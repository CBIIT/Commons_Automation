import pandas as pd
import pandasql as ps
import os
import Utils


result_tab = Utils.result_tab_name;


if result_tab in ("TsvDataCases"):
    # Get Cases query from excel
    cases_query = Utils.get_value_from_excel('CasesTab')
    print(f'This is Cases query fetched from input excel:\n{cases_query}')
    # Executing query with dataframe and storing result
    result_df_cases = Utils.df_run_query(cases_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    print(f'This is output_excel path: '+output_excel)
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataCases", result_df_cases)
    # Print output data
    print(f'Cases data successfully written to: {output_excel}')

elif result_tab in ("TsvDataSamples"):
    # Get Samples query from excel
    samples_query = Utils.get_value_from_excel('SamplesTab')
    print(f'This is Samples query fetched from input excel:\n{samples_query}')
    # Executing query with dataframe and storing result
    result_df_samples = Utils.df_run_query(samples_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    #output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'OutputFiles', Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataSamples", result_df_samples)
    # Print output data
    print(f'Samples data successfully written to: {output_excel}')

elif result_tab in ("TsvDataFiles"):
    # Get Files query from excel
    files_query = Utils.get_value_from_excel('FilesTab')
    print(f'This is Files query fetched from input excel:\n{files_query}')
    # Executing query with dataframe and storing result
    result_df_files = Utils.df_run_query(files_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataFiles", result_df_files)
    # Print output data
    print(f'Files data successfully written to: {output_excel}')

else:
    print(f'Check Result tab function. Result tab name in Python: {result_tab}')