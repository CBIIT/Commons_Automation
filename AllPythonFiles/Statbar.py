import pandas as pd
import pandasql as ps
import os
import Utils



# Base directory path
base_path = Utils.tsv_files_path

dataframes = Utils.load_and_merge_versions(base_path, Utils.index_columns)

# Now each dataframe can be accessed from the dataframes dictionary
df_program = dataframes['program']
df_study = dataframes['study']
df_participant = dataframes['participant']
df_sample = dataframes['sample']
df_file = dataframes['file']
df_diagnosis = dataframes['diagnosis']
df_genomic_info = dataframes['genomic_info']


# Lambda function that makes it easier to run SQL queries on pandas DataFrames.
df_run_query = lambda q: ps.sqldf(q, globals())

# Get participants query from excel
statbar_query = Utils.get_value_from_excel("StatQuery")
print(f'This is Statbar query fitched from input excel: {statbar_query}')

# Passing query to dataframe
result_df_statbar = df_run_query(statbar_query)   
 
# Specify the output path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "OutputFiles", Utils.get_value_from_excel("TsvExcel"))

 
# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "StatOutput", result_df_statbar)

print('Writing Statbar data to excel...') 

# Print output data
print(f"Statbar data successfully written to: {output_excel}")
 
