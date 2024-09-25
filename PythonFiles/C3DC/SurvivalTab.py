import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
survival_query = Utils.get_value_from_excel('SurvivalTab')
print(f'This is Survival tab query fitched from input excel:\n{survival_query}')

# Executing query with dataframe and storing result
result_df_survival = Utils.df_run_query(survival_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataSurvival", result_df_survival)

# Print output data
print(f'Survival data successfully written to: {output_excel}')
 
