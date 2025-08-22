package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p>Profile CCDC_QA : This variable holds the location of the input excel file which has the locators to be read from</p>
     */
    public static Object G_InputExcelFileName
     
    /**
     * <p>Profile CCDC_QA : This is the path where the browserdriver is stored - for Chromedriver, Geckodrive, IEdriver etc</p>
     */
    public static Object G_BrowserDriverPath
     
    /**
     * <p></p>
     */
    public static Object G_Browser
     
    /**
     * <p></p>
     */
    public static Object baseUrl
     
    /**
     * <p></p>
     */
    public static Object ResourceKey
     
    /**
     * <p></p>
     */
    public static Object DtstSummaryKey
     
    /**
     * <p></p>
     */
    public static Object ResFilter
     
    /**
     * <p></p>
     */
    public static Object fullUrl
     
    /**
     * <p></p>
     */
    public static Object initSummCnt
     
    /**
     * <p></p>
     */
    public static Object manifestPath
     
    /**
     * <p></p>
     */
    public static Object execBrowser
     
    /**
     * <p></p>
     */
    public static Object ManifestFlag
     
    /**
     * <p></p>
     */
    public static Object G_currentTCName
     
    /**
     * <p></p>
     */
    public static Object G_GrantInfo
     
    /**
     * <p></p>
     */
    public static Object G_dbgapID
     
    /**
     * <p></p>
     */
    public static Object G_caseEthn
     
    /**
     * <p></p>
     */
    public static Object G_caseRace
     
    /**
     * <p></p>
     */
    public static Object G_caseAge
     
    /**
     * <p></p>
     */
    public static Object G_caseSex
     
    /**
     * <p></p>
     */
    public static Object G_DtstName
     
    /**
     * <p></p>
     */
    public static Object G_DtstDesc
     
    /**
     * <p></p>
     */
    public static Object G_resCode
     
    /**
     * <p></p>
     */
    public static Object G_casesCnt
     
    /**
     * <p></p>
     */
    public static Object G_Grant
     
    /**
     * <p></p>
     */
    public static Object G_DtstScope
     
    /**
     * <p></p>
     */
    public static Object G_POC
     
    /**
     * <p></p>
     */
    public static Object G_POCemail
     
    /**
     * <p></p>
     */
    public static Object G_PubIn
     
    /**
     * <p></p>
     */
    public static Object G_samplesCnt
     
    /**
     * <p></p>
     */
    public static Object G_caseDisDiag
     
    /**
     * <p></p>
     */
    public static Object G_caseGender
     
    /**
     * <p></p>
     */
    public static Object G_cTumorSite
     
    /**
     * <p></p>
     */
    public static Object G_caseTrtmtAdmn
     
    /**
     * <p></p>
     */
    public static Object G_caseTrtmtOutcm
     
    /**
     * <p></p>
     */
    public static Object G_sampleAssMeth
     
    /**
     * <p></p>
     */
    public static Object G_sampleAnalType
     
    /**
     * <p></p>
     */
    public static Object G_sampleAnatSite
     
    /**
     * <p></p>
     */
    public static Object G_sampleCompType
     
    /**
     * <p></p>
     */
    public static Object G_sampleIsNml
     
    /**
     * <p></p>
     */
    public static Object G_sampleIsXeno
     
    /**
     * <p></p>
     */
    public static Object G_RsrcName
     
    /**
     * <p></p>
     */
    public static Object G_DtstSummCnt
     
    /**
     * <p></p>
     */
    public static Object G_FilterType
     
    /**
     * <p></p>
     */
    public static Object G_Splzation
     
    /**
     * <p></p>
     */
    public static Object G_dUpdateDate
     
    /**
     * <p></p>
     */
    public static Object G_VisTools
     
    /**
     * <p></p>
     */
    public static Object G_AnalTools
     
    /**
     * <p></p>
     */
    public static Object G_dContType
     
    /**
     * <p></p>
     */
    public static Object G_ResrceType
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
            selectedVariables += TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters(), selectedVariables)
    
            G_InputExcelFileName = selectedVariables['G_InputExcelFileName']
            G_BrowserDriverPath = selectedVariables['G_BrowserDriverPath']
            G_Browser = selectedVariables['G_Browser']
            baseUrl = selectedVariables['baseUrl']
            ResourceKey = selectedVariables['ResourceKey']
            DtstSummaryKey = selectedVariables['DtstSummaryKey']
            ResFilter = selectedVariables['ResFilter']
            fullUrl = selectedVariables['fullUrl']
            initSummCnt = selectedVariables['initSummCnt']
            manifestPath = selectedVariables['manifestPath']
            execBrowser = selectedVariables['execBrowser']
            ManifestFlag = selectedVariables['ManifestFlag']
            G_currentTCName = selectedVariables['G_currentTCName']
            G_GrantInfo = selectedVariables['G_GrantInfo']
            G_dbgapID = selectedVariables['G_dbgapID']
            G_caseEthn = selectedVariables['G_caseEthn']
            G_caseRace = selectedVariables['G_caseRace']
            G_caseAge = selectedVariables['G_caseAge']
            G_caseSex = selectedVariables['G_caseSex']
            G_DtstName = selectedVariables['G_DtstName']
            G_DtstDesc = selectedVariables['G_DtstDesc']
            G_resCode = selectedVariables['G_resCode']
            G_casesCnt = selectedVariables['G_casesCnt']
            G_Grant = selectedVariables['G_Grant']
            G_DtstScope = selectedVariables['G_DtstScope']
            G_POC = selectedVariables['G_POC']
            G_POCemail = selectedVariables['G_POCemail']
            G_PubIn = selectedVariables['G_PubIn']
            G_samplesCnt = selectedVariables['G_samplesCnt']
            G_caseDisDiag = selectedVariables['G_caseDisDiag']
            G_caseGender = selectedVariables['G_caseGender']
            G_cTumorSite = selectedVariables['G_cTumorSite']
            G_caseTrtmtAdmn = selectedVariables['G_caseTrtmtAdmn']
            G_caseTrtmtOutcm = selectedVariables['G_caseTrtmtOutcm']
            G_sampleAssMeth = selectedVariables['G_sampleAssMeth']
            G_sampleAnalType = selectedVariables['G_sampleAnalType']
            G_sampleAnatSite = selectedVariables['G_sampleAnatSite']
            G_sampleCompType = selectedVariables['G_sampleCompType']
            G_sampleIsNml = selectedVariables['G_sampleIsNml']
            G_sampleIsXeno = selectedVariables['G_sampleIsXeno']
            G_RsrcName = selectedVariables['G_RsrcName']
            G_DtstSummCnt = selectedVariables['G_DtstSummCnt']
            G_FilterType = selectedVariables['G_FilterType']
            G_Splzation = selectedVariables['G_Splzation']
            G_dUpdateDate = selectedVariables['G_dUpdateDate']
            G_VisTools = selectedVariables['G_VisTools']
            G_AnalTools = selectedVariables['G_AnalTools']
            G_dContType = selectedVariables['G_dContType']
            G_ResrceType = selectedVariables['G_ResrceType']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
