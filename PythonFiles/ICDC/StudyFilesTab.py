import pandas as pd
import pandasql as ps
import os
import Utils


# Get participants query from excel
studyfiles_query = Utils.get_value_from_excel('StudyFilesTab')
print(f'This is Study Files query fitched from input excel:\n{studyfiles_query}')

# Executing query with dataframe and storing result
result_df_studyfiles = Utils.df_run_query(studyfiles_query)   
 
# Specify the output excel path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataStudyFiles", result_df_studyfiles)

# Print output data
print(f'Study Files data successfully written to: {output_excel}')
 