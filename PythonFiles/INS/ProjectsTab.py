import pandas as pd
import pandasql as ps
import os
import Utils



# Get projects query from excel
projects_query = Utils.get_value_from_excel('ProjectsTab')
print(f'This is projects query fitched from input excel:\n{projects_query}')

# Executing query with dataframe and storing result
result_df_projects = Utils.df_run_query(projects_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataProjects", result_df_projects)

# Print output data
print(f'Projects data successfully written to: {output_excel}')
 
