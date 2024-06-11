# Among Us

This release includes the experimental results that are used for the ASE artifact analysis. All the results are described in our paper. All required artifacts are attached to this release.

# Information Extraction
The project is used to retrieve all relevant information from a specific version of AOSP to 2 CSV files. The maiximize length for a single cell is 32767.
Input: path to a specific version of AOSP.
Output:
A CSV file which contains all retrieved methods information. Primary key is signature.
Another CSV file which contains all retrieved fields information. Primary key is variable name.

# RQ1
LLMs experiment results (AST, API body, API comment) on our 407 groundtruth dataset. Version 4-33 retrieved AOSP method lists can be found in the shared link.
method_list.txt: The list of the current API (Method) from version 4 to version 33. (SDK version 11 to 13 are omitted, because Android 3 is non-free software and Google does not provide public available Android 3 source code.)
groundtruth_407.csv: 407 Groud Truth. The API with Manual check.

# RQ2

# RQ3

# Item description

- Item 1 (1_method_list): The list of the current API (Method) from version 4 to version 33. (SDK version 11 to 13 are omitted, because Android 3 is non-free software and Google does not provide public available Android 3 source code.)
- Item 2 (2_field_list): The list of the current API (Field) from version 4 to version 33. (SDK version 11 to 13 are omitted, because Android 3 is non-free software and Google does not provide public available Android 3 source code.)
- Item 3 (3_groundtruth_307): 307 Groud Truth. The API with Manual check.
- Item 4 (4_signature_incompatible_API_list): The list of the signature incompatible API.
- Item 5 (5_pred_results): The list of the semantic incompatible API.

--------------------------------------------------------------------------------------

# Item 4 and 5 data's indexes description

- Number: Serial number
- Signature (a string): The signature of the current API (Method).
- Early_Version: The earlier version of the API
- Late_Version: The later version of the API

- Early_Signature: the signature of the API in the earlier version
- Late_Signature: the signature of the API in the later version
- Diff_Signature (0/1): 0 for no difference, 1 otherwise.

- Early_Implementation: the implementation (body) of the API in the earlier version
- Late_Implementation: the implementation (body) of the API in the later version 
- Diff_Implementation (0/1): 0 for no difference, 1 otherwise. 

- Early_Comment: the comment of the API in the earlier version
- Late_Comment: the comment of the API in the later version 
- Diff_Comment (0/1): 0 for no difference, 1 otherwise. 

- Early_Annotation: the annotation of the API in the earlier version
- Late_Annotation: the annotation of the API in the later version 
- Diff_Annotation (0/1): 0 for no difference, 1 otherwise.

- Early_Callback: 1 for it is a callback, 0 for it is not a callback, -1 for the method does not exist
- Late_Callback: 1 for it is a callback, 0 for it is not a callback, -1 for the method does not exist
- Diff_Callback (0/1): 0 for no difference, 1 otherwise. -1 for Not sure

 -------- 
GT_Change_Type: Ground truth of the change type between two versions. Numbers stand for:


Note: for changes that fall into multiple categories, it will be separated by ',', e.g., 1,2,3,4,5
- 0: no Change
- -1: Not Sure
- 1: Return value changed
- 2: Exception handling changed
- 3: Control dependency changed (only including conditions (e.g., if) and content in the branches changes)
- 4: Statement changed
- 5: Dependent API changed (e.g., API1 -> API2, and API2 changed)

 -------- 
GT_CI_Type: Ground truth of the root reason type of (In)Compatibility Issue. Numbers stand for:

Note: for CIs that fall into multiple categories, it will be separated by ',', e.g., 1,2,3,4,5
- 0: no CI
- -1: Not Sure
- 1: different return values
- 2: different exceptions

 -------- 
pred_change: Predicted results from GPT-4 of the change type between two versions. Numbers stand for:

Note: for changes that fall into multiple categories, it will be separated by ',', e.g., 1,2,3,4,5
- 0: no Change
- 1: Return value changed
- 2: Exception handling changed
- 3: Control dependency changed (only including conditions (e.g., if) and content in the branches changes)
- 4: Statement changed
- 5: Dependent API changed (e.g., API1 -> API2, and API2 changed)

 -------- 
pred_CI: Predicted results from GPT-4 of the root reason type of (In)Compatibility Issue. Numbers stand for:

Note: for CIs that fall into multiple categories, it will be separated by ',', e.g., 1,2,3,4,5
- 0: no CI
- 1: different return values
- 2: different exceptions

--------
- change_description: The reason why GPT-4 predicted such the change type between two versions.
- CI_description: The reason why GPT-4 predicted such the root reason type of (In)Compatibility Issue. 
