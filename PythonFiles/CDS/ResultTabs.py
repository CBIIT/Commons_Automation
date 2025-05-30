import pandas as pd
import pandasql as ps
import os
import Utils


result_tab = Utils.result_tab_name;


if result_tab in ("TsvDataParticipants"):
    # Get participants query from excel
    participants_query = Utils.get_value_from_excel('ParticipantsTab')
    print(f'This is Participants query fitched from input excel:\n{participants_query}')
    # Executing query with dataframe and storing result
    result_df_particp = Utils.df_run_query(participants_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataParticipants", result_df_particp)
    # Print output data
    print(f'Participants data successfully written to: {output_excel}')

elif result_tab in ("TsvDataSamples"):
    # Get participants query from excel
    samples_query = Utils.get_value_from_excel('SamplesTab')
    print(f'This is Samples query fitched from input excel:\n{samples_query}')
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
    # Get participants query from excel
    files_query = Utils.get_value_from_excel('FilesTab')
    print(f'This is Files query fitched from input excel:\n{files_query}')
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