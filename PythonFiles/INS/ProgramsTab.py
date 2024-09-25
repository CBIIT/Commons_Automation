import pandas as pd
import pandasql as ps
import os
import Utils

    

# Get programs query from excel
programs_query = Utils.get_value_from_excel('ProgramsTab')
print(f'This is Programs tab query fitched from input excel:\n{programs_query}')

# Executing query with dataframe and storing result
result_df_programs = Utils.df_run_query(programs_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataPrograms", result_df_programs)

# Print output data
print(f'Programs data successfully written to: {output_excel}')
 
