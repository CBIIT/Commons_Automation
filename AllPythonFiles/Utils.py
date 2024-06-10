import pandas as pd
import pandasql as ps
import os


# Loads a TSV file into a pandas DataFrame and sets a specified column as the index.
# Returns: DataFrame containing the data from the TSV file with the specified index.
def load_tsv_to_dataframe_with_index(file_path, index_column):

    try:
        # Read the TSV file into a DataFrame and set the specified column as the index
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
    

# Path of input file
input_file_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'InputFiles', 'CDS', 'phs002504', 'TC01_phs002504_Gender_Male.xlsx')
print(f'Reading input excel: {input_file_path}')

def get_value_from_excel(rowOrColName):
    """
    Reads an Excel file and returns the appropriate cell value based on the rowOrColName.
    Args: rowOrColName (str): The name of the row or column to search for.
    Returns: str: The value of the specified row or column.
    """

    # Read the Excel file, always using the first sheet (index 0)
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
    if os.path.exists(output_excel):
        with pd.ExcelWriter(output_excel, engine='openpyxl', mode='a', if_sheet_exists='replace') as writer:
            result_df.to_excel(writer, sheet_name, index=False)
    else:
        with pd.ExcelWriter(output_excel, engine='xlsxwriter') as writer:
            result_df.to_excel(writer, sheet_name, index=False)