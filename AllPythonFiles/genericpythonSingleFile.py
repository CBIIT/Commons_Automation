import pandas as pd
import os
import sys

 

def get_project_directory(levels_up):
    """
    Returns the project directory by going up a specified number of levels from the current working directory.
    
    :param levels_up: The number of levels to go up.
    :return: The resulting project directory path.
    """
    project_dir = os.getcwd()  # Get the current working directory
    for _ in range(levels_up):
        project_dir = os.path.dirname(project_dir)
    return project_dir

print("about to start reading the python function")

def load_tsv_and_save_to_excel(file_path, excel_path):
    """
    Reads a TSV file into a pandas DataFrame and saves it to an Excel file.
    
    Parameters:
    file_path (str): The path to the TSV file.
    excel_path (str): The path to save the Excel file.
    
    Returns:
    bool: True if the operation was successful, False otherwise.
    """
    
    print("inside the Python script ")
    try:
        # Read the TSV file into a DataFrame
        df = pd.read_csv(file_path, delimiter='\t')
        
        # Save the DataFrame to an Excel file
        df.to_excel(excel_path, index=False)
        
        print(f"Data successfully saved to {excel_path}")
        return True
    
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        return False
    except pd.errors.EmptyDataError:
        print("No data: The file is empty.")
        return False
    except pd.errors.ParserError:
        print("Parsing error: The file could not be parsed.")
        return False
    except Exception as e:
        print(f"An error occurred: {e}")
        return False

# Define base directory
base_dir = os.path.dirname(os.path.realpath(__file__))  # Assuming this script is in the base directory

# Define relative paths from the base directory
relative_tsv_file_path = "InputFiles/PythonPOC/POC-study.tsv"
output_directory = "OutputFiles"
output_filename = "outputxl-usingpandas.xlsx"

# Get the project directory by going up 2 levels (or as needed)
project_dir = get_project_directory(0)  #When this is 0, the path given is /Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation

print("Your desired path within the project directory:", project_dir)
    
# Construct absolute paths
tsv_file_path = os.path.join(project_dir, relative_tsv_file_path)
print("Your tsv_file_path is:", tsv_file_path)

output_path = os.path.join(project_dir, output_directory)
print("Your output_path is:", output_path)

output_full_path = os.path.join(output_path, output_filename)
print("Your output_full_path is :", output_full_path)

# Call the function with constructed paths
success = load_tsv_and_save_to_excel(tsv_file_path, output_full_path)

if success:
    print("Operation completed successfully.")
else:
    print("Operation failed.")
