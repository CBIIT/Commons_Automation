import pandas as pd
import pandasql as ps
import os
import Utils


result_tab = Utils.result_tab_name;


if result_tab in ("TsvDataCases"):
    # Get participants query from excel
    cases_query = Utils.get_value_from_excel('CasesTab')
    print(f'This is Cases tab query fitched from input excel:\n{cases_query}')
    # Executing query with dataframe and storing result
    result_df_cases = Utils.df_run_query(cases_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataCases", result_df_cases)
    # Print output data
    print(f'Cases data successfully written to: {output_excel}')

elif result_tab in ("TsvDataSamples"):
    # Get participants query from excel
    samples_query = Utils.get_value_from_excel('SamplesTab')
    print(f'This is Samples query fitched from input excel:\n{samples_query}')
    # Executing query with dataframe and storing result
    result_df_samples = Utils.df_run_query(samples_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataSamples", result_df_samples)
    # Print output data
    print(f'Samples data successfully written to: {output_excel}')

elif result_tab in ("TsvDataCaseFiles"):
    # Get participants query from excel
    casefiles_query = Utils.get_value_from_excel('CaseFilesTab')
    print(f'This is Case Files query fitched from input excel:\n{casefiles_query}')
    # Executing query with dataframe and storing result
    result_df_casefiles = Utils.df_run_query(casefiles_query)    
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataCaseFiles", result_df_casefiles)
    # Print output data
    print(f'Case Files data successfully written to: {output_excel}')

elif result_tab in ("TsvDataStudyFiles"):
    # Get participants query from excel
    studyfiles_query = Utils.get_value_from_excel('StudyFilesTab')
    print(f'This is Study Files query fitched from input excel:\n{studyfiles_query}')
    # Executing query with dataframe and storing result
    result_df_studyfiles = Utils.df_run_query(studyfiles_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataStudyFiles", result_df_studyfiles)
    # Print output data
    print(f'Study Files data successfully written to: {output_excel}')

else:
    print(f'Check Result tab function. Result tab name in Python: {result_tab}')