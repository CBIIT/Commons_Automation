import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
treatment_resp_query = Utils.get_value_from_excel('TreatmentRespTab')
print(f'This is Treatment Response tab query fitched from input excel:\n{treatment_resp_query}')

# Executing query with dataframe and storing result
result_df_treatment_resp = Utils.df_run_query(treatment_resp_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataTreatmentResp", result_df_treatment_resp)

# Print output data
print(f'Treatment Response data successfully written to: {output_excel}')
 
