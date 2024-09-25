import pandas as pd
import pandasql as ps
import os
import Utils


# Get publications query from excel
publications_query = Utils.get_value_from_excel('PublicationsTab')
print(f'This is publications query fitched from input excel:\n{publications_query}')

# Executing query with dataframe and storing result
result_df_studyfiles = Utils.df_run_query(publications_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataPublications", result_df_studyfiles)

# Print output data
print(f'Publications data successfully written to: {output_excel}')
 
