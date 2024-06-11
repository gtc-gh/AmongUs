package edu.monash.mevol;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFileFieldExtractor {

    private static int row = 0; // record the current row number

    private static Map<String, List<String>> fieldsInformation = new HashMap<>();

    public static Workbook getFields(Workbook workbook, String p1) {
        // Get the similar files. p1, p2 means the input path

        Sheet sheet2 = workbook.createSheet("Fields");

        Row row3 = sheet2.createRow(row);

        // the defination of Java fields:
        // [access_modifier] [static] [final] type name [= initial value] ;

        String[] firstRow= {"Variable Name", "Public", "Protected", "Private", "Static", "Final", "Type",
                "Initial Value", "Annotation", "Comment", "Java File Path"};
        for (int i = 0; i < firstRow.length; i ++)
            WriteToCSV.writeToWorkbook(row3, i, firstRow[i]);

        // move to next row, the first row is column names and the data is stored from the second row
        row++;

        // input path 1
        for (String javaFilePath : GTCClient.files) {
            // Get the fields from these similar files.
            try {
                FileInputStream in = new FileInputStream(p1 + javaFilePath);
                CompilationUnit cu = JavaParser.parse(in);

                String packageName = cu.getPackageDeclaration().map(PackageDeclaration::getNameAsString).orElse("");
                VoidVisitor<Void> classVisitor = new ClassVisitor(packageName);
                classVisitor.visit(cu, null);

                // Store them to CSV file.
                // print the call chain
                for (Map.Entry<String, List<String>> entry : fieldsInformation.entrySet()) {
                    Row ithRow = sheet2.createRow(row);
                    WriteToCSV.writeToWorkbook(ithRow, 0, entry.getKey());
                    int col = 1;
                    for (String value : entry.getValue()) {
                        WriteToCSV.writeToWorkbook(ithRow, col, value);
                        col ++;
                    }
                    WriteToCSV.writeToWorkbook(ithRow, col, String.valueOf(javaFilePath));
                    row ++;
                }
                // Clear the map for current file.
                fieldsInformation.clear();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return workbook;
    }

    private static class ClassVisitor extends VoidVisitorAdapter<Void> {

        private final String packageName;

        public ClassVisitor(String packageName) {
            this.packageName = packageName;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            // Process the class information as needed
            String className = n.getNameAsString();
            VoidVisitor<Void> fieldVisitor = new FieldVisitor(packageName + "." + className);
            fieldVisitor.visit(n, null);

            // Continue visiting nested nodes
            super.visit(n, arg);
        }
    }

    private static class FieldVisitor extends VoidVisitorAdapter<Void> {

        private final String prefixName;

        public FieldVisitor(String name) {
            this.prefixName = name;
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            // Retrieve relevant information and store them.
            List<String> currentFieldInformation = new ArrayList<>();
            currentFieldInformation.add(String.valueOf(n.isPublic()));
            currentFieldInformation.add(String.valueOf(n.isProtected()));
            currentFieldInformation.add(String.valueOf(n.isPrivate()));
            currentFieldInformation.add(String.valueOf(n.isStatic()));
            currentFieldInformation.add(String.valueOf(n.isFinal()));
            currentFieldInformation.add(n.getElementType().asString());
            currentFieldInformation.add(n.getVariables().get(0).getInitializer().map(Object::toString).orElse(""));
            currentFieldInformation.add(n.getAnnotations().stream().findFirst().map(Object::toString).orElse(""));
            currentFieldInformation.add(n.getComment().map(Object::toString).orElse(""));

            fieldsInformation.put(prefixName + "." + n.getVariables().get(0).getNameAsString(), currentFieldInformation);

            // Continue visiting other nodes
            super.visit(n, arg);
        }
    }

}
