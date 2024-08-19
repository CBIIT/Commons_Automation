import pandas as pd
import pandasql as ps
import os
import sys
import re
from datetime import datetime


# This returns input excel path
get_input_excel_path = sys.argv[1]

# This returns output excel path
get_output_file_path = sys.argv[2]

# This returns node/tsv/txt files path
get_node_files_path = sys.argv[3]

# Reads an Excel file and returns the appropriate cell value based on the rowOrColName.
def get_value_from_excel(rowOrColName):
    # Read the input Excel file, always using the first sheet (index 0)
    df = pd.read_excel(get_input_excel_path, sheet_name=0)
    # Iterate through the rows to find the specified rowOrColName entry
    for index, row in df.iterrows():
        if row['TabName'] == rowOrColName:
            return row['TabQuery']
        elif rowOrColName == 'StatQuery':
            return row['StatQuery']
        elif rowOrColName == 'TsvExcel':
            return row['TsvExcel']
        elif rowOrColName == 'WebExcel':
            return row['WebExcel']  
    return None


def get_tsv_files_path():
    base_path = get_node_files_path
    print(f"TSV Files Base Path inside Utils.py is: {base_path}")
    # Example test case name for demonstration purposes
    testcase_name = get_value_from_excel('TsvExcel')
    # Loop through all folders in the base path
    for folder_name in os.listdir(base_path):
        if folder_name in testcase_name:
            # Return the new path concatenated with the matching folder
            return os.path.join(base_path, folder_name)
    # Return the base path if no matching folder is found
    return base_path



def load_tsv_to_dataframe_with_index(file_path, index_column):
    try:
        df = pd.read_csv(file_path, delimiter='\t', index_col=index_column)
        return df
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        return None
    except pd.errors.EmptyDataError:
        print("No data: The file is empty.")
        return None
    except pd.errors.ParserError:
        print("Parsing error: The file could not be parsed.")
        return None
    except KeyError:
        print(f"Key error: The column '{index_column}' does not exist.")
        return None
    except Exception as e:
        print(f"An error occurred: {e}")
        return None

def load_and_merge_versions(base_path, index_columns):
    # Initialize empty dataframes for each type
    dataframes = {key: pd.DataFrame() for key in index_columns.keys()}
    
    # Get list of folders in base_path and filter out non-date directories
    all_folders = [folder for folder in os.listdir(base_path) if os.path.isdir(os.path.join(base_path, folder))]
    version_folders = [folder for folder in all_folders if is_date_format(folder, '%Y-%m-%d')]
    version_folders.sort(key=lambda x: datetime.strptime(x, '%Y-%m-%d'))
    
    # Check for folders that are not in the expected date format
    invalid_folders = [folder for folder in all_folders if folder not in version_folders]
    if invalid_folders:
        print(f"The following folders are not in the expected date format 'YYYY-MM-DD': {', '.join(invalid_folders)}")
    
    # Check if no valid version folders are found
    if not version_folders:
        print("No folders found in the expected date format 'YYYY-MM-DD'.")
        return dataframes
    
    # Iterate through each version folder
    for version in version_folders:
        version_path = os.path.join(base_path, version)
        if os.path.isdir(version_path):
            # Load each TSV or TXT file in the version folder and merge it with the existing DataFrame
            for node, index_column in index_columns.items():
                # Find files that contain the node name and have a .tsv or .txt extension
                for file_name in os.listdir(version_path):
                    if node in file_name and (file_name.endswith('.tsv') or file_name.endswith('.txt')):
                        file_path = os.path.join(version_path, file_name)
                        df_new = load_tsv_to_dataframe_with_index(file_path, index_column)
                        if df_new is not None:
                            if not dataframes[node].empty:
                                # Identify rows with duplicate indices
                                duplicated_indices = df_new.index.intersection(dataframes[node].index)
                                for idx in duplicated_indices:
                                    # Check for updated values in the new DataFrame
                                    if not df_new.loc[idx].equals(dataframes[node].loc[idx]):
                                        dataframes[node].loc[idx] = df_new.loc[idx]
                                # Append new rows
                                df_new = df_new[~df_new.index.isin(dataframes[node].index)]
                            # Concatenate non-duplicate rows
                            dataframes[node] = pd.concat([dataframes[node], df_new])
            print(f"Data successfully loaded for release: {version}")
    return dataframes



def is_date_format(date_str, date_format):
    try:
        datetime.strptime(date_str, date_format)
        return True
    except ValueError:
        return False

# Index columns for each TSV file
index_columns = {
    'studies': 'study_id',
    'participant': 'participant_id',
    'diagnosis': 'diagnosis_id',
    'reference_file': 'reference_file_id',
    'survivals': 'survival_id'
}


# Write data to excel
def write_to_excel(output_excel, sheet_name, result_df):
    """
    Writes the result DataFrame to an Excel file. If file exists, appends the sheet or replaces it if it already exists.
    If the file does not exist, it creates a new file and writes the sheet.
    :param output_excel: Path to the output Excel file.
    :param sheet_name: Name of the sheet to write in the Excel file.
    :param result_df: DataFrame to be written to the Excel file.
    """
    result_df = result_df.fillna('')
    result_df = result_df.astype(str)
    if os.path.exists(output_excel):
        with pd.ExcelWriter(output_excel, engine='openpyxl', mode='a', if_sheet_exists='replace') as writer:
            result_df.to_excel(writer, sheet_name, index=False)
    else:
        with pd.ExcelWriter(output_excel, engine='xlsxwriter') as writer:
            result_df.to_excel(writer, sheet_name, index=False)



# Load and merge dataframe
dataframes = load_and_merge_versions(get_tsv_files_path(), index_columns)

# Now each dataframe can be accessed from the dataframes dictionary
df_study = dataframes['studies']
df_participant = dataframes['participant']
df_sample = dataframes['sample']
df_diagnosis = dataframes['diagnosis']
df_survival = dataframes['survivals']


# df_enrollment = dataframes['enrollment']

# df_study_file = dataframes['study_files']
# df_cohort = dataframes['cohort']

# df_program = pd.DataFrame({'program_acronym': ['A1', 'A2'], 'data': ['data1', 'data2']}).set_index('program_acronym')
# df_study = pd.DataFrame({'clinical_study_designation': ['S1', 'S2'], 'data': ['data3', 'data4']}).set_index('clinical_study_designation')
# df_case = pd.DataFrame({'case_id': [1, 2], 'data': ['data5', 'data6']}).set_index('case_id')
# df_demographic = pd.DataFrame({'demographic_id': [1, 2], 'data': ['data7', 'data8']}).set_index('demographic_id')
# df_sample = pd.DataFrame({'sample_id': [1, 2], 'data': ['data9', 'data10']}).set_index('sample_id')
# df_diagnosis = pd.DataFrame({'diagnosis_id': [1, 2], 'data': ['data11', 'data12']}).set_index('diagnosis_id')
# df_file = pd.DataFrame({'file_name': ['file1', 'file2'], 'data': ['data13', 'data14']}).set_index('file_name')
# df_enrollment = pd.DataFrame({'enrollment_id': [1, 2], 'data': ['data15', 'data16']}).set_index('enrollment_id')
# df_publication = pd.DataFrame({'pubmed_id': [1, 2], 'data': ['data17', 'data18']}).set_index('pubmed_id')

#Print each DataFrame
print("\nDataFrame: df_study")
print(df_study)
print("\nDataFrame: df_participant")
print(df_participant)
print("\nDataFrame: df_diagnosis")
print(df_diagnosis)
print("\nDataFrame: df_survival")
print(df_survival)


df_run_query = lambda q: ps.sqldf(q, globals())

