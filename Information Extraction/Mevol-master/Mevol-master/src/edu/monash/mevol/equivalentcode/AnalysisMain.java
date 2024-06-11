package edu.monash.mevol.equivalentcode;

import com.github.javaparser.ParseException;
import edu.monash.mevol.GTCClient;

import java.io.IOException;


/**
 * Analysis and extract the all methods and fields from AOSP.
 * https://github.com/gtc-gh/AOSPMethodsAndFieldsRetrivial
 */
public class AnalysisMain {
    public static void main(String[] args) throws IOException, ParseException {
        // GTC Clint to retrieve all signatures and fields from AOSP.
        // Configuration: input path to a specific version's AOSP
        // output path: methods and fields
        GTCClient.main(new String[] {args[0]});

        String outputPath1 = "C:\\Users\\gtc\\Desktop\\AOSPRetrievedCSVFilesComparison\\AOSPRetrievedCSVFilesComparison\\33outputMethods.csv";
        String outputPath2 = "C:\\Users\\gtc\\Desktop\\AOSPRetrievedCSVFilesComparison\\AOSPRetrievedCSVFilesComparison\\33outputFields.csv";

        FunctionallyEquivalentCodeAnalysis fecAnalysis = new FunctionallyEquivalentCodeAnalysis();
        fecAnalysis.analyse(GTCClient.allMethods, GTCClient.methodsRepo, args[0], outputPath1, outputPath2);

    }
}
