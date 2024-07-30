import pandas as pd
import pandasql as ps
import os
import Utils



# Get participants query from excel
samples_query = Utils.get_value_from_excel('SamplesTab')
print(f'This is Samples query fitched from input excel:\n{samples_query}')

# Executing query with dataframe and storing result
result_df_samples = Utils.df_run_query(samples_query)   
 
# Specify the output path
output_excel = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'OutputFiles', Utils.get_value_from_excel('TsvExcel'))

# Write the result DataFrame to an Excel file
Utils.write_to_excel(output_excel, "TsvDataSamples", result_df_samples)

# Print output data
print(f'Samples data successfully written to: {output_excel}')
 
