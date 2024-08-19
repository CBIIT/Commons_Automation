import pandas as pd
import pandasql as ps
import os
import Utils

    

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
 
