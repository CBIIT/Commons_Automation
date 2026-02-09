import pandas as pd
import pandasql as ps
import os
import Utils


result_tab = Utils.result_tab_name;


if result_tab in ("TsvDataStudies"):
    # Get participants query from excel
    studies_query = Utils.get_value_from_excel('StudiesTab')
    print(f'This is Files query fitched from input excel:\n{studies_query}')
    # Executing query with dataframe and storing result
    result_df_studies = Utils.df_run_query(studies_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataStudies", result_df_studies)
    # Print output data
    print(f'Files data successfully written to: {output_excel}')

elif result_tab in ("TsvDataParticipants"):
    # Get participants query from excel
    participants_query = Utils.get_value_from_excel('ParticipantsTab')
    print(f'This is Participants tab query fitched from input excel:\n{participants_query}')
    # Executing query with dataframe and storing result
    result_df_participants = Utils.df_run_query(participants_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataParticipants", result_df_participants)
    # Print output data
    print(f'Participants data successfully written to: {output_excel}')

elif result_tab in ("TsvDataDiagnosis"):
    # Get participants query from excel
    diagnosis_query = Utils.get_value_from_excel('DiagnosisTab')
    print(f'This is Diagnosis tab query fitched from input excel:\n{diagnosis_query}')
    # Executing query with dataframe and storing result
    result_df_diagnosis = Utils.df_run_query(diagnosis_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataDiagnosis", result_df_diagnosis)
    # Print output data
    print(f'Diagnosis data successfully written to: {output_excel}')
    
elif result_tab in ("TsvDataGeneticAnalysis"):
    # Get participants query from excel
    geneticanalysis_query = Utils.get_value_from_excel('GeneticAnalysisTab')
    print(f'This is Genetic Analysis tab query fitched from input excel:\n{geneticanalysis_query}')
    # Executing query with dataframe and storing result
    result_df_geneticanalysis = Utils.df_run_query(geneticanalysis_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataGeneticAnalysis", result_df_geneticanalysis)
    # Print output data
    print(f'Genetic Analysis data successfully written to: {output_excel}')
    

elif result_tab in ("TsvDataTreatment"):
    # Get participants query from excel
    treatment_query = Utils.get_value_from_excel('TreatmentTab')
    print(f'This is Treatment tab query fitched from input excel:\n{treatment_query}')
    # Executing query with dataframe and storing result
    result_df_treatment = Utils.df_run_query(treatment_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataTreatment", result_df_treatment)
    # Print output data
    print(f'Treatment data successfully written to: {output_excel}')

elif result_tab in ("TsvDataTreatmentResp"):
    # Get participants query from excel
    treatment_resp_query = Utils.get_value_from_excel('TreatmentRespTab')
    print(f'This is Treatment Response tab query fitched from input excel:\n{treatment_resp_query}')
    # Executing query with dataframe and storing result
    result_df_treatment_resp = Utils.df_run_query(treatment_resp_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataTreatmentResp", result_df_treatment_resp)
    # Print output data
    print(f'Treatment Response data successfully written to: {output_excel}')

elif result_tab in ("TsvDataSurvival"):
    # Get participants query from excel
    survival_query = Utils.get_value_from_excel('SurvivalTab')
    print(f'This is Survival tab query fitched from input excel:\n{survival_query}')
    # Executing query with dataframe and storing result
    result_df_survival = Utils.df_run_query(survival_query)   
    # Specify the output excel path
    output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), Utils.get_output_file_path, Utils.get_value_from_excel('TsvExcel'))
    # Write the result DataFrame to an Excel file
    Utils.write_to_excel(output_excel, "TsvDataSurvival", result_df_survival)
    # Print output data
    print(f'Survival data successfully written to: {output_excel}')

else:
    print(f'Check Result tab function. Result tab name in Python: {result_tab}')