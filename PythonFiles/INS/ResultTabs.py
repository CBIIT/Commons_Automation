import pandas as pd
import pandasql as ps
import os
import Utils


result_tab = Utils.result_tab_name;

if result_tab in ("TsvDataPrograms"):
    # Get programs query from excel
    programs_query = Utils.get_value_from_excel('ProgramsTab')
    print(f'This is Programs tab query fitched from input excel:\n{programs_query}')
    # Executing query with dataframe and storing result
    result_df_programs = Utils.df_run_query(programs_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataPrograms", result_df_programs)
    # Print output data
    print(f'Programs data successfully written to: {output_excel}')

elif result_tab in ("TsvDataProjects"):
    # Get Grants query from excel
    grants_query = Utils.get_value_from_excel('GrantsTab')
    print(f'This is Grants query fitched from input excel:\n{grants_query}')
    # Executing query with dataframe and storing result
    result_df_grants = Utils.df_run_query(grants_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataGrants", result_df_grants)
    # Print output data
    print(f'Grants data successfully written to: {output_excel}')

elif result_tab in ("TsvDataGrants"):
    # Get Grants query from excel
    grants_query = Utils.get_value_from_excel('GrantsTab')
    print(f'This is Grants query fitched from input excel:\n{grants_query}')
    # Executing query with dataframe and storing result
    result_df_grants = Utils.df_run_query(grants_query)    
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataGrants", result_df_grants)
    # Print output data
    print(f'Grants data successfully written to: {output_excel}')   

elif result_tab in ("TsvDataPublications"):
    # Get publications query from excel
    publications_query = Utils.get_value_from_excel('PublicationsTab')
    print(f'This is publications query fitched from input excel:\n{publications_query}')
    # Executing query with dataframe and storing result
    result_df_studyfiles = Utils.df_run_query(publications_query)    
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataPublications", result_df_studyfiles)
    # Print output data
    print(f'Publications data successfully written to: {output_excel}')

else:
    print(f'Check Result tab function. Result tab name in Python: {result_tab}')