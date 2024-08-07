import pandas as pd
import pandasql as ps
import os
import Utils

    

# Get participants query from excel
cases_query = Utils.get_value_from_excel('CasesTab')
print(f'This is Cases tab query fitched from input excel:\n{cases_query}')

# Executing query with dataframe and storing result
result_df_cases = Utils.df_run_query(cases_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataCases", result_df_cases)

# Print output data
print(f'Cases data successfully written to: {output_excel}')
 
