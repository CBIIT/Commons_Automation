import pandas as pd
import os
import sys
import pandasql as ps

def read_tsv_files_and_write_to_excel(tsv_files, excel_path, sql_query):
    try:
        with pd.ExcelWriter(excel_path, engine='xlsxwriter') as writer:
            for tsv_file in tsv_files:
                sheet_name = os.path.splitext(os.path.basename(tsv_file))[0]
                df = pd.read_csv(tsv_file, delimiter='\t')
                
                # Apply SQL query to POC-study DataFrame
                if sheet_name == 'POC-study':
                    df.to_sql('df_stdy', con='sqlite://', if_exists='replace')
                    df = ps.sqldf(sql_query, locals())
                
                df.to_excel(writer, sheet_name=sheet_name, index=False)
        
        print(f"Data successfully saved to {excel_path}")
        return True
    except Exception as e:
        print(f"An error occurred: {e}")
        return False

if __name__ == "__main__":
    if len(sys.argv) < 10:
        print("Usage: python process_multiple_tsv.py <output_excel_path> <sql_query> <tsv_file1> <tsv_file2> ... <tsv_file7>")
        sys.exit(1)
    
    output_excel_path = sys.argv[1]
    sql_query = sys.argv[2]
    tsv_files = sys.argv[3:]
    
    success = read_tsv_files_and_write_to_excel(tsv_files, output_excel_path, sql_query)
    if success:
        print("Operation completed successfully.")
    else:
        print("Operation failed.")
