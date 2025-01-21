import pandas as pd
import pandasql as ps
import os
import Utils

# Get Statbar query from excel
statbar_query = Utils.get_value_from_excel("StatQuery")
print(f'This is Statbar query fetched from input excel:\n {statbar_query}')

# Passing query to dataframe
result_df_statbar = Utils.df_run_query(statbar_query)   

# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "StatOuput", result_df_statbar)

# Print output data
print(f"Statbar data successfully written to: {output_excel}")
 
