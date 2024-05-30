import pandas as pd
import os
import sys

def read_tsv_files_and_write_to_excel(tsv_files, excel_path):
    try:
        with pd.ExcelWriter(excel_path, engine='xlsxwriter') as writer:
            for tsv_file in tsv_files:
                sheet_name = os.path.splitext(os.path.basename(tsv_file))[0]
                df = pd.read_csv(tsv_file, delimiter='\t')
                df.to_excel(writer, sheet_name=sheet_name, index=False)
        print(f"Data successfully saved to {excel_path}")
        return True
    except Exception as e:
        print(f"An error occurred: {e}")
        return False

if __name__ == "__main__":
    if len(sys.argv) < 9:
        print("Usage: python process_multiple_tsv.py <output_excel_path> <tsv_file1> <tsv_file2> ... <tsv_file7>")
        sys.exit(1)
    output_excel_path = sys.argv[1]
    tsv_files = sys.argv[2:]
    success = read_tsv_files_and_write_to_excel(tsv_files, output_excel_path)
    if success:
        print("Operation completed successfully.")
    else:
        print("Operation failed.")
