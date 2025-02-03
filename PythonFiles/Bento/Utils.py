import pandas as pd
import pandasql as ps
import os
import sys
import re
from datetime import datetime


# This function returns input excel with path
def input_excel():
    if len(sys.argv) > 1:
        print("input excel with path is: "+sys.argv[1])
        return sys.argv[1]
    else:
        return "No input excel with path provided"

# This function returns output excel path
get_output_file_path = sys.argv[2]
print("output excel path is: "+get_output_file_path)

# This function gets the phs accession from testcase name and creates new path to the study
def get_tsv_files_path():

    base_path = sys.argv[3] #os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'InputFiles', 'CDS')
    # Regular expression to match "phs" followed by digits
    pattern = re.compile(r'phs\d+')
    # Check if testcaseName contains the pattern
    match = pattern.search(input_excel())
    
    if match:
        # Append the matched pattern to the base path
        print("PHS matched, path is: "+os.path.join(base_path, match.group()))
        return os.path.join(base_path, match.group())
    else:
        # Return the base path if no match
        print("PHS not matched, path is: "+base_path)
        return base_path


tsv_files_path = os.path.join(get_tsv_files_path())
print("TSV Files path inside Utils.py is: "+tsv_files_path)

# Loads a TSV file into a pandas DataFrame and sets a specified column as the index.
# Returns: DataFrame containing the data from the TSV file with the specified index.

def load_tsv_to_dataframe_with_index(file_path, index_column):
    try:
        df = pd.read_csv(file_path, delimiter='\t', index_col=index_column)
        df.columns = df.columns.str.strip()
        #df = df.map(lambda x: x.strip() if isinstance(x, str) else x) # Commented this for Jenkins issue
        df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)
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
            # Load each TSV file in the version folder and merge it with the existing DataFrame
            for node, index_column in index_columns.items():
                # Find files that end with the node name
                for file_name in os.listdir(version_path):
                    if file_name.endswith(f"{node}.tsv"):
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
    'program': 'program_id',
    'study': 'study_id',
    'study_subject': 'study_subject_id',
    'diagnosis': 'diagnosis_id',
    'stratification_factor': 'stratification_factor_id',
    'demographic_data': 'demographic_data_id',
    'sample': 'sample_id',
    'laboratory_procedure': 'laboratory_procedure_id',
    'file': 'file_id',
    'file_vcf': 'file_id',
    'file_bam': 'file_id'
}


# Path of input excel file
input_file_path = os.path.join(input_excel())

print(f'Reading input excel: {input_file_path}')

def get_value_from_excel(rowOrColName):
    """
    Reads an Excel file and returns the appropriate cell value based on the rowOrColName.
    Args: rowOrColName (str): The name of the row or column to search for.
    Returns: str: The value of the specified row or column.
    """

    # Read the input Excel file, always using the first sheet (index 0)
    df = pd.read_excel(input_file_path, sheet_name=0)
    
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

# Write data to excel
def write_to_excel(output_excel, sheet_name, result_df):
    """
    Writes the result DataFrame to an Excel file. If file exists, appends the sheet or replaces it if it already exists.
    If the file does not exist, it creates a new file and writes the sheet.
    :param output_excel: Path to the output Excel file.
    :param sheet_name: Name of the sheet to write in the Excel file.
    :param result_df: DataFrame to be written to the Excel file.
    """

    result_df = result_df.astype(str)
    if os.path.exists(output_excel):
        with pd.ExcelWriter(output_excel, engine='openpyxl', mode='a', if_sheet_exists='replace') as writer:
            result_df.to_excel(writer, sheet_name, index=False)
    else:
        with pd.ExcelWriter(output_excel, engine='xlsxwriter') as writer:
            result_df.to_excel(writer, sheet_name, index=False)





# Load and merge dataframe
dataframes = load_and_merge_versions(tsv_files_path, index_columns)

# Now each dataframe can be accessed from the dataframes dictionary
df_program = dataframes['program']
df_study = dataframes['study']
df_study_subject = dataframes['study_subject']
df_diagnosis = dataframes['diagnosis']
df_stratification_factor = dataframes['stratification_factor']
df_demographic_data = dataframes['demographic_data']
df_sample = dataframes['sample']
df_laboratory_procedure = dataframes['laboratory_procedure']
df_file = dataframes['file']
df_file_vcf = dataframes['file_vcf']
df_file_bam = dataframes['file_bam']


#Print each DataFrame
print("\nDataFrame: df_program")
print(df_program)
print("\nDataFrame: df_study")
print(df_study)
print("\nDataFrame: df_study_subject")
print(df_study_subject)
print("\nDataFrame: df_diagnosis")
print(df_diagnosis)
print("\nDataFrame: df_stratification_factor")
print(df_stratification_factor)
print("\nDataFrame: df_demographic_data")
print(df_demographic_data)
print("\nDataFrame: df_sample")
print(df_sample)
print("\nDataFrame: df_laboratory_procedure")
print(df_laboratory_procedure)
print("\nDataFrame: df_file")
print(df_file)
print("\nDataFrame: df_file_vcf")
print(df_file_vcf)
print("\nDataFrame: df_file_bam")
print(df_file_bam)

df_run_query = lambda q: ps.sqldf(q, globals())