import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
files_query = Utils.get_value_from_excel('FilesTab')
print(f'This is Files query fitched from input excel:\n{files_query}')

# Executing query with dataframe and storing result
result_df_files = Utils.df_run_query(files_query)   
 
# Specify the output path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'OutputFiles', Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataFiles", result_df_files)

# Print output data
print(f'Files data successfully written to: {output_excel}')
 
