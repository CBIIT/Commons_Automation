import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
casefiles_query = Utils.get_value_from_excel('CaseFilesTab')
print(f'This is Case Files query fitched from input excel:\n{casefiles_query}')

# Executing query with dataframe and storing result
result_df_casefiles = Utils.df_run_query(casefiles_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataCaseFiles", result_df_casefiles)

# Print output data
print(f'Case Files data successfully written to: {output_excel}')
 
