import pandas as pd
import pandasql as ps
import os
import Utils



# Base directory path
base_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "InputFiles", "CDS", "phs002504")

# Paths to TSV files
program_path = os.path.join(base_dir, "program.tsv")
study_path = os.path.join(base_dir, "study.tsv")
participant_path = os.path.join(base_dir, "participant.tsv")
sample_path = os.path.join(base_dir, "sample.tsv")
file_path = os.path.join(base_dir, "file.tsv")

# Load TSV files to DataFrames
df_program = Utils.load_tsv_to_dataframe_with_index(program_path, 'program_acronym')
df_study = Utils.load_tsv_to_dataframe_with_index(study_path, 'phs_accession')
df_participant = Utils.load_tsv_to_dataframe_with_index(participant_path, 'study_participant_id')
df_sample = Utils.load_tsv_to_dataframe_with_index(sample_path, 'sample_id')
df_file = Utils.load_tsv_to_dataframe_with_index(file_path, 'file_id')


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

print('\nWriting Statbar data to excel...\n') 

# Print output data
print(f"Statbar data successfully written to: {output_excel}")
 
