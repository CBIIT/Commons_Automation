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
print('Data successfully loaded to dataframe')

# Get participants query from excel
samples_query = Utils.get_value_from_excel('SamplesTab')
print(f'This is Samples query fitched from input excel:\n{samples_query}')

# Executing query with dataframe and storing result
result_df_samples = df_run_query(samples_query)   
 
# Specify the output path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'OutputFiles', Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataSamples", result_df_samples)

print('Writing Samples data to excel...') 

# Print output data
print(f'Samples data successfully written to: {output_excel}')
 
