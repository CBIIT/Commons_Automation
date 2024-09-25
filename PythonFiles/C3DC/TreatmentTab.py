import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
treatment_query = Utils.get_value_from_excel('TreatmentTab')
print(f'This is Treatment tab query fitched from input excel:\n{treatment_query}')

# Executing query with dataframe and storing result
result_df_treatment = Utils.df_run_query(treatment_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataTreatment", result_df_treatment)

# Print output data
print(f'Treatment data successfully written to: {output_excel}')
 
