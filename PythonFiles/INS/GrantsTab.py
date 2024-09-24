import pandas as pd
import pandasql as ps
import os
import Utils


# Get Grants query from excel
grants_query = Utils.get_value_from_excel('GrantsTab')
print(f'This is Grants query fitched from input excel:\n{grants_query}')

# Executing query with dataframe and storing result
result_df_grants = Utils.df_run_query(grants_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataGrants", result_df_grants)

# Print output data
print(f'Grants data successfully written to: {output_excel}')
 
