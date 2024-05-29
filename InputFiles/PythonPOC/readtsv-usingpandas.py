import pandas as pd
import os

print("Python script started")

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
relative_tsv_file_path = "POC-study.tsv"
output_directory = "OutputFiles"
output_filename = "outputxl-usingpandas.xlsx"

# Desired directory to stop at
stop_at_dir = "Commons_Automation"

# Initialize the project_dir with base_dir
project_dir = base_dir

# Loop to go up until the desired directory is reached
while os.path.basename(project_dir) != stop_at_dir:
    project_dir = os.path.dirname(project_dir)
    
print("Your desired path within the project directory:", project_dir)
    
 
# Construct absolute paths
tsv_file_path = os.path.join(base_dir, relative_tsv_file_path)
print("Your tsv_file_path is:", tsv_file_path)

output_directory_path = os.path.join(project_dir, output_directory)
print("Your output_directory_path is:", output_directory_path)

output_file_path = os.path.join(output_directory_path, output_filename)
print("Your output_file_path is :", output_file_path)

# Call the function with relative paths
success = load_tsv_and_save_to_excel(tsv_file_path, output_file_path)

if success:
    print("Operation completed successfully.")
else:
    print("Operation failed.")
