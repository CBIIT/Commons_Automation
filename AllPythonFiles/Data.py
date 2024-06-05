import pandas as pd
import pandasql as ps
import sys

def load_tsv_to_dataframe_with_index(file_path, index_column):
    """
    Loads a TSV file into a pandas DataFrame and sets a specified column as the index.
    
    Parameters:
    file_path (str): The path to the TSV file.
    index_column (str): The name of the column to set as the index.
    
    Returns:
    pd.DataFrame: DataFrame containing the data from the TSV file with the specified index.
    """
    try:
        # Read the TSV file into a DataFrame and set the specified column as the index
        df = pd.read_csv(file_path, delimiter='\t', index_col=index_column)
        return df
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        return None
    except pd.errors.EmptyDataError:
        print("No data: The file is empty.")
        return None
    except pd.errors.ParserError:
        print("Parsing error: The file could not be parsed.")
        return None
    except KeyError:
        print(f"Key error: The column '{index_column}' does not exist.")
        return None
    except Exception as e:
        print(f"An error occurred: {e}")
        return None

# Example usage:
#This is the program node  #all the tsv paths to be made global
csv_prm_path = r"/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC/POC-program.tsv"
df_prm = load_tsv_to_dataframe_with_index(csv_prm_path, 'program_acronym')
print(df_prm)

#This is the study node   (parent: program)
csv_std_path = r"/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC/POC-study.tsv"
df_stdy =  load_tsv_to_dataframe_with_index(csv_std_path, 'phs_accession')
print(df_stdy)

#This is the participant node   (parent: study)
csv_prtpnt_path = r"/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC/POC-participant.tsv"
df_prtpnt = load_tsv_to_dataframe_with_index(csv_prtpnt_path, 'study_participant_id')
print(df_prtpnt)

#This is the sample node   (parent: participant)
csv_smpl_path = r"/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC/POC-sample.tsv"
df_smpl = load_tsv_to_dataframe_with_index(csv_smpl_path, 'participant_study_participant_id')
print(df_smpl)



pysqldf = lambda q: ps.sqldf(q, globals())

 
 
#==========================================================================
# Define the SQL query for Participants tab   call a function to read this query from an excel
queryParticipants = """
        SELECT
        sp.participant_id, 
        s.study_name,
        s.phs_accession,
        sp.gender,
        GROUP_CONCAT(DISTINCT smp.sample_id) as sample_ids
    FROM 
        df_prtpnt sp
    JOIN 
        df_stdy s
    ON 
        sp.study_phs_accession = s.phs_accession
    JOIN 
        df_smpl smp
    ON 
        smp.participant_study_participant_id = sp.study_participant_id
    JOIN 
        df_prm p
    ON
        p.program_acronym = s.program_program_acronym
    WHERE 
        s.phs_accession='phs002504'
    GROUP BY
        sp.study_participant_id, 
        s.study_name,
        s.phs_accession,
        sp.gender
"""
#====================================================================================
 
 
#==============================================================================
# Execute the query
 
result_df_particp = pysqldf(queryParticipants)   #integrate this part to read the above query from the excel, without hardcoding here
 
#result_df_particp = result_df_particp.groupby(['study_participant_id', 'study_name', 'phs_accession', 'gender'])['sample_id'].apply(lambda x: ','.join(sorted(set(x)))).reset_index()

# Specify the output path
 #output excel name and sheetname should be read from Sohil's input query excel
output_path_particp = r"/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/OutputFiles/ParticipantsTabOutput.xlsx"
 

# Write the result DataFrame to an Excel file
 
result_df_particp.to_excel(output_path_particp, index=False)
 

 
print(f"Output written to {output_path_particp}")
 
