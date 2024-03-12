
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import com.kms.katalon.core.testobject.TestObject

import java.lang.String

import org.openqa.selenium.WebElement

import org.openqa.selenium.WebDriver

import java.util.List

import org.apache.poi.ss.usermodel.Sheet

import org.apache.poi.hssf.usermodel.HSSFSheet

import org.apache.poi.xssf.usermodel.XSSFSheet

import org.apache.poi.ss.usermodel.Cell

import com.applitools.eyes.selenium.Eyes

import com.applitools.eyes.RectangleSize


 /**
	 * This function creates an instance of webdriver
	 * And navigates user to crdc application
	 */ 
def static "ctdc.utilities.Crdc.navigateToCrdc"() {
    (new ctdc.utilities.Crdc()).navigateToCrdc()
}

 /**
	 * The function is used to login to CRDC application.
	 */ 
def static "ctdc.utilities.Crdc.loginToCrdc"() {
    (new ctdc.utilities.Crdc()).loginToCrdc()
}

 /**
	 * This function enters text to any text box 
	 * @param eleObj
	 * @param fPath
	 * @param colNam
	 * @param rowNum
	 */ 
def static "ctdc.utilities.Crdc.setText"(
    	TestObject eleObj	
     , 	String fPath	
     , 	String colNam	
     , 	int rowNum	) {
    (new ctdc.utilities.Crdc()).setText(
        	eleObj
         , 	fPath
         , 	colNam
         , 	rowNum)
}

 /**
	 * This function validates CRDC Submission Request status bar
	 * @param Status to be validated i.e New, Submitted
	 */ 
def static "ctdc.utilities.Crdc.verifyStatusBar"(
    	String expStatus	) {
    (new ctdc.utilities.Crdc()).verifyStatusBar(
        	expStatus)
}

 /**
	 * This function enters Principal Investigator information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterPiInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int instAddRN	) {
    (new ctdc.utilities.Crdc()).enterPiInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	instAddRN)
}

 /**
	 *  This function enters Primary Contact information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterPrimaryContactInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int phoneRN	) {
    (new ctdc.utilities.Crdc()).enterPrimaryContactInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	phoneRN)
}

 /**
	 *  This function enters Additional Contact information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterAdditionalContactInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int phoneRN	) {
    (new ctdc.utilities.Crdc()).enterAdditionalContactInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	phoneRN)
}

 /**
	 * The function enters Program info into the submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterProgramInfo"(
    	String ddValue	) {
    (new ctdc.utilities.Crdc()).enterProgramInfo(
        	ddValue)
}

 /**
	 * This function enters study information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterStudyInfo"(
    	int stdyTitleRN	
     , 	int stdyAbbRN	
     , 	int stdyDesRN	) {
    (new ctdc.utilities.Crdc()).enterStudyInfo(
        	stdyTitleRN
         , 	stdyAbbRN
         , 	stdyDesRN)
}

 /**
	 * This function enters Funding Agency and DbGaP info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterFundingAgencyAndDbGaPInfo"(
    	int fundinAgency	
     , 	int grntRN	
     , 	int nciPgogOfcrRN	
     , 	int nciGenProgAdmnRN	
     , 	int dbgapPhsNumRN	) {
    (new ctdc.utilities.Crdc()).enterFundingAgencyAndDbGaPInfo(
        	fundinAgency
         , 	grntRN
         , 	nciPgogOfcrRN
         , 	nciGenProgAdmnRN
         , 	dbgapPhsNumRN)
}

 /**
	 * This function enters Publications info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterPublicationsInfo"(
    	int publiTitRN	
     , 	int pubmedIdRN	
     , 	int doiRN	
     , 	int plndPublTitleRN	) {
    (new ctdc.utilities.Crdc()).enterPublicationsInfo(
        	publiTitRN
         , 	pubmedIdRN
         , 	doiRN
         , 	plndPublTitleRN)
}

 /**
	 * This function enters Repository info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterRepositoryInfo"(
    	String dropDownVlue	
     , 	int repoNamRN	
     , 	int stdyIdRN	
     , 	int otherDataTypRN	) {
    (new ctdc.utilities.Crdc()).enterRepositoryInfo(
        	dropDownVlue
         , 	repoNamRN
         , 	stdyIdRN
         , 	otherDataTypRN)
}

 /**
	 * This function enters Data Access Types and Cancer Types into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.enterDataAccessAndDiseaseInfo"(
    	String cancerType	
     , 	String preCancerType	
     , 	String speciesOfSub	
     , 	int otherCancerTyRN	
     , 	int otherPreCancerTyRN	
     , 	int numOfSubjectRN	) {
    (new ctdc.utilities.Crdc()).enterDataAccessAndDiseaseInfo(
        	cancerType
         , 	preCancerType
         , 	speciesOfSub
         , 	otherCancerTyRN
         , 	otherPreCancerTyRN
         , 	numOfSubjectRN)
}

 /**
	 * This function selects Data Types on the submission request form
	 * @param buttonLable Toggle button label to be clicked (enter only one word per toggle button)
	 */ 
def static "ctdc.utilities.Crdc.selectDataTypes"(
    	String[] buttonLable	) {
    (new ctdc.utilities.Crdc()).selectDataTypes(
        	buttonLable)
}

 /**
	 * This function selects File Types of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */ 
def static "ctdc.utilities.Crdc.selectFileTypes"(
    	int fileTypRN	
     , 	int fileExtRN	
     , 	int numOfFileRN	
     , 	int dataSizeRN	) {
    (new ctdc.utilities.Crdc()).selectFileTypes(
        	fileTypRN
         , 	fileExtRN
         , 	numOfFileRN
         , 	dataSizeRN)
}

 /**
	 * This function selects File Types of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */ 
def static "ctdc.utilities.Crdc.verifyPiInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int instAddRN	) {
    (new ctdc.utilities.Crdc()).verifyPiInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	instAddRN)
}

 /**
	 * This function verifies Primary contact info on Review Page
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyPrimaryContactInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int phoneRN	) {
    (new ctdc.utilities.Crdc()).verifyPrimaryContactInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	phoneRN)
}

 /**
	 * This function verifies additional contact info of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */ 
def static "ctdc.utilities.Crdc.verifyAdditionalContactInfo"(
    	int fNameRN	
     , 	int lNameRN	
     , 	int positnRN	
     , 	int emailRN	
     , 	int institRN	
     , 	int phoneRN	) {
    (new ctdc.utilities.Crdc()).verifyAdditionalContactInfo(
        	fNameRN
         , 	lNameRN
         , 	positnRN
         , 	emailRN
         , 	institRN
         , 	phoneRN)
}

 /**
	 * This function verifies program info on review page
	 * @param rowNumber
	 */ 
def static "ctdc.utilities.Crdc.verifyProgramInfo"(
    	int rowNumber	) {
    (new ctdc.utilities.Crdc()).verifyProgramInfo(
        	rowNumber)
}

 /**
	 * This function verifies study information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyStudyInfo"(
    	int stdyTitleRN	
     , 	int stdyAbbRN	
     , 	int stdyDesRN	) {
    (new ctdc.utilities.Crdc()).verifyStudyInfo(
        	stdyTitleRN
         , 	stdyAbbRN
         , 	stdyDesRN)
}

 /**
	 *  This function verifies Funding Agency and DbGaP information on the review page
	 * @param dataFileRowNum Data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyFundingAgencyAndDbGaPInfo"(
    	int fundinAgency	
     , 	int grntRN	
     , 	int nciPgogOfcrRN	
     , 	int nciGenProgAdmnRN	
     , 	int dbgapPhsNumRN	) {
    (new ctdc.utilities.Crdc()).verifyFundingAgencyAndDbGaPInfo(
        	fundinAgency
         , 	grntRN
         , 	nciPgogOfcrRN
         , 	nciGenProgAdmnRN
         , 	dbgapPhsNumRN)
}

 /**
	 *  This function verifies Publications information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyPublicationsInfo"(
    	int publiTitRN	
     , 	int pubmedIdRN	
     , 	int doiRN	
     , 	int plndPublTitleRN	) {
    (new ctdc.utilities.Crdc()).verifyPublicationsInfo(
        	publiTitRN
         , 	pubmedIdRN
         , 	doiRN
         , 	plndPublTitleRN)
}

 /**
	 *  This function verifies Repository information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyRepositoryInfo"(
    	String dropDownVlue	
     , 	int repoNamRN	
     , 	int stdyIdRN	
     , 	int otherDataTypRN	) {
    (new ctdc.utilities.Crdc()).verifyRepositoryInfo(
        	dropDownVlue
         , 	repoNamRN
         , 	stdyIdRN
         , 	otherDataTypRN)
}

 /**
	 * This function verifies Data Access Types and Cancer Types on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */ 
def static "ctdc.utilities.Crdc.verifyDataAccessAndDiseaseInfo"(
    	String cancerType	
     , 	String preCancerType	
     , 	String speciesOfSub	
     , 	int otherCancerTyRN	
     , 	int otherPreCancerTyRN	
     , 	int numOfSubjectRN	) {
    (new ctdc.utilities.Crdc()).verifyDataAccessAndDiseaseInfo(
        	cancerType
         , 	preCancerType
         , 	speciesOfSub
         , 	otherCancerTyRN
         , 	otherPreCancerTyRN
         , 	numOfSubjectRN)
}

 /**
	 * This function verifies Data Types of the submission request form review page
	 * @param buttonLable Toggle button label to be clicked (enter only one word per toggle button)
	 */ 
def static "ctdc.utilities.Crdc.verifyDataTypes"(
    	String[] buttonLable	) {
    (new ctdc.utilities.Crdc()).verifyDataTypes(
        	buttonLable)
}

 /**
	 * This function verifies File Types on review page
	 * @param  Row Number of the data to be verified
	 */ 
def static "ctdc.utilities.Crdc.verifyFileTypes"(
    	int fileTypRN	
     , 	int fileExtRN	
     , 	int numOfFileRN	
     , 	int dataSizeRN	) {
    (new ctdc.utilities.Crdc()).verifyFileTypes(
        	fileTypRN
         , 	fileExtRN
         , 	numOfFileRN
         , 	dataSizeRN)
}

 /**
	 * This function clicks on submit and confirm submit buttons
	 */ 
def static "ctdc.utilities.Crdc.clickSubmitButton"() {
    (new ctdc.utilities.Crdc()).clickSubmitButton()
}


def static "ctdc.utilities.runtestcaseforKatalon.Login"(
    	String signinButton	
     , 	String emailID	
     , 	String emailNxtBtn	
     , 	String Passwd	
     , 	String PasswdNxtBtn	) {
    (new ctdc.utilities.runtestcaseforKatalon()).Login(
        	signinButton
         , 	emailID
         , 	emailNxtBtn
         , 	Passwd
         , 	PasswdNxtBtn)
}

 /**
	 * This function reads the new excel file name from InputFiles
	 * @param input_file
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.RunKatalon"(
    	String input_file	) {
    (new ctdc.utilities.runtestcaseforKatalon()).RunKatalon(
        	input_file)
}

 /**for case detail level automation
	 * @return
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.getPageSwitch"() {
    (new ctdc.utilities.runtestcaseforKatalon()).getPageSwitch()
}

 /**
	 * This function reads the results table and writes the web and database data to excel
	 * This function also verifies the stat-bar counts and compares the web and database excels
	 * @param appName
	 * @param statVal
	 * @param tbl
	 * @param tblHdr
	 * @param nxtBtn
	 * @param webdataSheetName
	 * @param dbdataSheetName
	 * @param tabQuery
	 * @throws IOException
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.multiFunction"(
    	String appName	
     , 	String statVal	
     , 	String tbl	
     , 	String tblHdr	
     , 	String nxtBtn	
     , 	String webdataSheetName	
     , 	String dbdataSheetName	
     , 	String tabQuery	) {
    (new ctdc.utilities.runtestcaseforKatalon()).multiFunction(
        	appName
         , 	statVal
         , 	tbl
         , 	tblHdr
         , 	nxtBtn
         , 	webdataSheetName
         , 	dbdataSheetName
         , 	tabQuery)
}

 /**
	 * Gayathri will updata the details
	 * @param sTblbdy1
	 * @param sTblHdr1
	 * @param webSheetName
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readSelectedCols"(
    	String sTblbdy1	
     , 	String sTblHdr1	
     , 	String sNxtBtn	
     , 	String webSheetName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readSelectedCols(
        	sTblbdy1
         , 	sTblHdr1
         , 	sNxtBtn
         , 	webSheetName)
}


def static "ctdc.utilities.runtestcaseforKatalon.verifyCDSFacetExpansion"(
    	String CDSFacet	) {
    (new ctdc.utilities.runtestcaseforKatalon()).verifyCDSFacetExpansion(
        	CDSFacet)
}

 /**
	 * This function reads cases table
	 * @param statVal1
	 * @param tbl1
	 * @param hdr1
	 * @param nxtb1
	 * @param webSheetName
	 * @throws IOException
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.ReadCasesTableKatalon"(
    	String statVal1	
     , 	String tbl1	
     , 	String hdr1	
     , 	String nxtb1	
     , 	String webSheetName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).ReadCasesTableKatalon(
        	statVal1
         , 	tbl1
         , 	hdr1
         , 	nxtb1
         , 	webSheetName)
}

 /**
	 * This function reads Bento Statbar
	 * @param bProgs
	 * @param bArms
	 * @param bCases
	 * @param bSamples
	 * @param bAssays
	 * @param bFiles
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readStatBarBento"(
    	String bProgs	
     , 	String bArms	
     , 	String bCases	
     , 	String bSamples	
     , 	String bAssays	
     , 	String bFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readStatBarBento(
        	bProgs
         , 	bArms
         , 	bCases
         , 	bSamples
         , 	bAssays
         , 	bFiles)
}

 /**
	 * This function reads the count displayed near the cart icon in ICDC
	 * @param cmyCartCount
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readMyCartCount"(
    	String cmyCartCount	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readMyCartCount(
        	cmyCartCount)
}

 /**
	 * This function reads Canine Statbar
	 * @param cProgs
	 * @param cStuds
	 * @param cCases
	 * @param cSamples
	 * @param cFiles
	 * @param cStudyFiles
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readStatBarCanine"(
    	String cProgs	
     , 	String cStuds	
     , 	String cCases	
     , 	String cSamples	
     , 	String cFiles	
     , 	String cStudyFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readStatBarCanine(
        	cProgs
         , 	cStuds
         , 	cCases
         , 	cSamples
         , 	cFiles
         , 	cStudyFiles)
}

 /**
	 * This function reads CCDI Hub Statbar
	 * @param cStuds
	 * @param cParticip
	 * @param cSamples
	 * @param cFiles
	 //@param cDiag  - this will be used later when diag is available in stat bar
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readStatBarCCDIhub"(
    	String cStuds	
     , 	String cParticip	
     , 	String cSamples	
     , 	String cFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readStatBarCCDIhub(
        	cStuds
         , 	cParticip
         , 	cSamples
         , 	cFiles)
}

 /**
	 * This function reads CTDC Statbar
	 * @param tTrials
	 * @param tCases
	 * @param tFiles
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readTrialsStatBar"(
    	String tTrials	
     , 	String tCases	
     , 	String tFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readTrialsStatBar(
        	tTrials
         , 	tCases
         , 	tFiles)
}


def static "ctdc.utilities.runtestcaseforKatalon.readINSStatBar"(
    	String tProgs	
     , 	String tProjs	
     , 	String tGrants	
     , 	String tPubs	
     , 	String tDsets	
     , 	String tClinTrials	
     , 	String tPatents	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readINSStatBar(
        	tProgs
         , 	tProjs
         , 	tGrants
         , 	tPubs
         , 	tDsets
         , 	tClinTrials
         , 	tPatents)
}

 /**
	 * This function reads GMB Statbar
	 * @param gTrials
	 * @param gSubjects
	 * @param gFiles
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readGMBStatBar"(
    	String gTrials	
     , 	String gSubjects	
     , 	String gFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readGMBStatBar(
        	gTrials
         , 	gSubjects
         , 	gFiles)
}

 /**
	 * This function reads CDS Statbar
	 * @param cdsStuds
	 * @param cdsDisesSite
	 * @param cdsParticipants
	 * @param cdsSamples
	 * @param cdsFiles
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.readStatBarCDS"(
    	String cdsStuds	
     , 	String cdsParticipants	
     , 	String cdsSamples	
     , 	String cdsFiles	) {
    (new ctdc.utilities.runtestcaseforKatalon()).readStatBarCDS(
        	cdsStuds
         , 	cdsParticipants
         , 	cdsSamples
         , 	cdsFiles)
}


def static "ctdc.utilities.runtestcaseforKatalon.givexpath"(
    	String objname	) {
    (new ctdc.utilities.runtestcaseforKatalon()).givexpath(
        	objname)
}

 /**
	 * This function is used for bento local find functionality
	 */ 
def static "ctdc.utilities.runtestcaseforKatalon.BentoLocalFindDdn"() {
    (new ctdc.utilities.runtestcaseforKatalon()).BentoLocalFindDdn()
}


def static "ctdc.utilities.runtestcaseforKatalon.BentoLocalFindFileUpld"(
    	String filetype	) {
    (new ctdc.utilities.runtestcaseforKatalon()).BentoLocalFindFileUpld(
        	filetype)
}


def static "ctdc.utilities.runtestcaseforKatalon.CTDCLocalFindDdn"() {
    (new ctdc.utilities.runtestcaseforKatalon()).CTDCLocalFindDdn()
}


def static "ctdc.utilities.runtestcaseforKatalon.canineUIValidation"() {
    (new ctdc.utilities.runtestcaseforKatalon()).canineUIValidation()
}


def static "ctdc.utilities.runtestcaseforKatalon.trialsUIValidation"() {
    (new ctdc.utilities.runtestcaseforKatalon()).trialsUIValidation()
}


def static "ctdc.utilities.runtestcaseforKatalon.footerVal"() {
    (new ctdc.utilities.runtestcaseforKatalon()).footerVal()
}


def static "ctdc.utilities.runtestcaseforKatalon.headerVal"() {
    (new ctdc.utilities.runtestcaseforKatalon()).headerVal()
}


def static "ctdc.utilities.runtestcaseforKatalon.compareLists"(
    	String webSheetName	
     , 	String neoSheetName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).compareLists(
        	webSheetName
         , 	neoSheetName)
}


def static "ctdc.utilities.runtestcaseforKatalon.compareManifestLists"(
    	String webCartSheetName	
     , 	String manifestSheetName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).compareManifestLists(
        	webCartSheetName
         , 	manifestSheetName)
}


def static "ctdc.utilities.runtestcaseforKatalon.validateStatBar"(
    	Object getAppName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).validateStatBar(
        	getAppName)
}


def static "ctdc.utilities.runtestcaseforKatalon.validateCanineDetailStat"() {
    (new ctdc.utilities.runtestcaseforKatalon()).validateCanineDetailStat()
}


def static "ctdc.utilities.runtestcaseforKatalon.validateTrialsDetailStat"() {
    (new ctdc.utilities.runtestcaseforKatalon()).validateTrialsDetailStat()
}


def static "ctdc.utilities.runtestcaseforKatalon.validateTrialsStatBar"() {
    (new ctdc.utilities.runtestcaseforKatalon()).validateTrialsStatBar()
}


def static "ctdc.utilities.runtestcaseforKatalon.clickTab"(
    	String TabName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickTab(
        	TabName)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat"(
    	String TbName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickTabCanineStat(
        	TbName)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickTabINSStat"(
    	String TbName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickTabINSStat(
        	TbName)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickTabGMBStat"(
    	String TbName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickTabGMBStat(
        	TbName)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickTabCDSStat"(
    	String TbName	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickTabCDSStat(
        	TbName)
}


def static "ctdc.utilities.runtestcaseforKatalon.scrolltoViewjs"(
    	WebElement elem	) {
    (new ctdc.utilities.runtestcaseforKatalon()).scrolltoViewjs(
        	elem)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickElement"(
    	WebElement el	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickElement(
        	el)
}


def static "ctdc.utilities.runtestcaseforKatalon.Select_case_checkbox"(
    	String caseID	
     , 	String count	) {
    (new ctdc.utilities.runtestcaseforKatalon()).Select_case_checkbox(
        	caseID
         , 	count)
}


def static "ctdc.utilities.runtestcaseforKatalon.isDriverOpen"() {
    (new ctdc.utilities.runtestcaseforKatalon()).isDriverOpen()
}


def static "ctdc.utilities.runtestcaseforKatalon.JsFunc"() {
    (new ctdc.utilities.runtestcaseforKatalon()).JsFunc()
}


def static "ctdc.utilities.runtestcaseforKatalon.File_details"(
    	String tbl1	
     , 	String hdr1	
     , 	String nxtb1	) {
    (new ctdc.utilities.runtestcaseforKatalon()).File_details(
        	tbl1
         , 	hdr1
         , 	nxtb1)
}


def static "ctdc.utilities.runtestcaseforKatalon.clickcase"(
    	String lCases	) {
    (new ctdc.utilities.runtestcaseforKatalon()).clickcase(
        	lCases)
}


def static "ctdc.utilities.runtestcaseforKatalon.casedetailsQueryBuilder"(
    	String lCases	) {
    (new ctdc.utilities.runtestcaseforKatalon()).casedetailsQueryBuilder(
        	lCases)
}


def static "ctdc.utilities.RunTestcase.Run"(
    	String InputExcelname	
     , 	String pwd_file	) {
    (new ctdc.utilities.RunTestcase()).Run(
        	InputExcelname
         , 	pwd_file)
}


def static "ctdc.utilities.RunTestcase.ReadCasesTable"(
    	WebDriver driver	) {
    (new ctdc.utilities.RunTestcase()).ReadCasesTable(
        	driver)
}


def static "ctdc.utilities.RunTestcase.compareLists"() {
    (new ctdc.utilities.RunTestcase()).compareLists()
}


def static "ctdc.utilities.RunTestcase.browserDriver"(
    	String browserName	) {
    (new ctdc.utilities.RunTestcase()).browserDriver(
        	browserName)
}

 /**
	 * This function reads input excels and assigns global variables to each query...
	 * @param sheetData
	 * @param dr
	 */ 
def static "ctdc.utilities.ICDCcaseDetails.readInput"(
    	String input_file	) {
    (new ctdc.utilities.ICDCcaseDetails()).readInput(
        	input_file)
}


def static "ctdc.utilities.ICDCcaseDetails.excelparsing"(
    	java.util.List<java.util.List<org.apache.poi.xssf.usermodel.XSSFCell>> sheetData	
     , 	WebDriver dr	) {
    (new ctdc.utilities.ICDCcaseDetails()).excelparsing(
        	sheetData
         , 	dr)
}


def static "ctdc.utilities.ICDCcaseDetails.readStatBarICDC"(
    	String cProgs	
     , 	String cStuds	
     , 	String cCases	
     , 	String cSamples	
     , 	String cFiles	
     , 	String cStudyFiles	) {
    (new ctdc.utilities.ICDCcaseDetails()).readStatBarICDC(
        	cProgs
         , 	cStuds
         , 	cCases
         , 	cSamples
         , 	cFiles
         , 	cStudyFiles)
}


def static "ctdc.utilities.ICDCcaseDetails.multiFunctionCD"(
    	String appName	
     , 	String tbl	
     , 	String tblHdr	
     , 	String nxtBtn	
     , 	String webdataSheetName	
     , 	String dbdataSheetName	
     , 	String tabQuery	) {
    (new ctdc.utilities.ICDCcaseDetails()).multiFunctionCD(
        	appName
         , 	tbl
         , 	tblHdr
         , 	nxtBtn
         , 	webdataSheetName
         , 	dbdataSheetName
         , 	tabQuery)
}


def static "ctdc.utilities.ICDCcaseDetails.readInfoPanel"(
    	String infoType	
     , 	String webdataSheetName	
     , 	String dbdataSheetName	
     , 	String tabquery	) {
    (new ctdc.utilities.ICDCcaseDetails()).readInfoPanel(
        	infoType
         , 	webdataSheetName
         , 	dbdataSheetName
         , 	tabquery)
}


def static "ctdc.utilities.ICDCcaseDetails.readCDInfo"(
    	String infoType	
     , 	String webdataSheetName	) {
    (new ctdc.utilities.ICDCcaseDetails()).readCDInfo(
        	infoType
         , 	webdataSheetName)
}


def static "ctdc.utilities.ICDCcaseDetails.readTable"(
    	String tbl1	
     , 	String hdr1	
     , 	String nxtb1	
     , 	String webSheetName	) {
    (new ctdc.utilities.ICDCcaseDetails()).readTable(
        	tbl1
         , 	hdr1
         , 	nxtb1
         , 	webSheetName)
}


def static "ctdc.utilities.CustomBrowserDriver.createWebDriver"() {
    (new ctdc.utilities.CustomBrowserDriver()).createWebDriver()
}


def static "ctdc.utilities.CustomBrowserDriver.chromeHeadless"() {
    (new ctdc.utilities.CustomBrowserDriver()).chromeHeadless()
}


def static "ctdc.utilities.CustomBrowserDriver.firefoxHeadless"() {
    (new ctdc.utilities.CustomBrowserDriver()).firefoxHeadless()
}


def static "ctdc.utilities.ExtraFunctions.compareLists_1D"() {
    (new ctdc.utilities.ExtraFunctions()).compareLists_1D()
}


def static "ctdc.utilities.ReadExcel.Test"(
    	String filename	) {
    (new ctdc.utilities.ReadExcel()).Test(
        	filename)
}


def static "ctdc.utilities.ReadExcel.readExceltoWeblist"(
    	String filename	
     , 	String sheetName	) {
    (new ctdc.utilities.ReadExcel()).readExceltoWeblist(
        	filename
         , 	sheetName)
}


def static "ctdc.utilities.ReadExcel.Neo4j"(
    	String dbSheetName	
     , 	String tbQuery	) {
    (new ctdc.utilities.ReadExcel()).Neo4j(
        	dbSheetName
         , 	tbQuery)
}


def static "ctdc.utilities.ReadExcel.initialLoad"() {
    (new ctdc.utilities.ReadExcel()).initialLoad()
}


def static "ctdc.utilities.ReadExcel.PrintG"() {
    (new ctdc.utilities.ReadExcel()).PrintG()
}


def static "ctdc.utilities.ReadExcel.ExcelToArray"(
    	String filename	) {
    (new ctdc.utilities.ReadExcel()).ExcelToArray(
        	filename)
}


def static "ctdc.utilities.DataValidation.initDriver"() {
    (new ctdc.utilities.DataValidation()).initDriver()
}


def static "ctdc.utilities.DataValidation.passDriver"() {
    (new ctdc.utilities.DataValidation()).passDriver()
}


def static "ctdc.utilities.DataValidation.passDriver"(
    	WebDriver dr	) {
    (new ctdc.utilities.DataValidation()).passDriver(
        	dr)
}


def static "ctdc.utilities.DataValidation.countRows"(
    	String tblbdy	) {
    (new ctdc.utilities.DataValidation()).countRows(
        	tblbdy)
}


def static "ctdc.utilities.DataValidation.CountRowsfromPagination"(
    	String pgntn	) {
    (new ctdc.utilities.DataValidation()).CountRowsfromPagination(
        	pgntn)
}


def static "ctdc.utilities.DataValidation.isObjPresent"(
    	String objID	) {
    (new ctdc.utilities.DataValidation()).isObjPresent(
        	objID)
}


def static "ctdc.utilities.DataValidation.isObjClickablet"(
    	String objID	) {
    (new ctdc.utilities.DataValidation()).isObjClickablet(
        	objID)
}


def static "ctdc.utilities.DataValidation.CCDCreadInfo"(
    	WebDriver driver	
     , 	String webElem	
     , 	String ipElem	
     , 	String globalV	
     , 	String ElemLabel	) {
    (new ctdc.utilities.DataValidation()).CCDCreadInfo(
        	driver
         , 	webElem
         , 	ipElem
         , 	globalV
         , 	ElemLabel)
}


def static "ctdc.utilities.FileOperations.assignMfstFilenames"() {
    (new ctdc.utilities.FileOperations()).assignMfstFilenames()
}


def static "ctdc.utilities.FileOperations.manifestFileOps"(
    	String csvfilename1	
     , 	String xlsfilename1	
     , 	String xlsxfilename1	
     , 	String mfstSelectedColsSheetNm	
     , 	String mfstBkupSheetNm	) {
    (new ctdc.utilities.FileOperations()).manifestFileOps(
        	csvfilename1
         , 	xlsfilename1
         , 	xlsxfilename1
         , 	mfstSelectedColsSheetNm
         , 	mfstBkupSheetNm)
}


def static "ctdc.utilities.FileOperations.pickLatestFileFromDownloads"() {
    (new ctdc.utilities.FileOperations()).pickLatestFileFromDownloads()
}


def static "ctdc.utilities.FileOperations.fileRename"() {
    (new ctdc.utilities.FileOperations()).fileRename()
}


def static "ctdc.utilities.FileOperations.csvToEXCEL"(
    	String csvFileName	
     , 	String excelFileName	) {
    (new ctdc.utilities.FileOperations()).csvToEXCEL(
        	csvFileName
         , 	excelFileName)
}


def static "ctdc.utilities.FileOperations.generateXLSfromCSV"(
    	String csvFilePath	
     , 	String xlsFilePath	
     , 	String xlsSheetnm	) {
    (new ctdc.utilities.FileOperations()).generateXLSfromCSV(
        	csvFilePath
         , 	xlsFilePath
         , 	xlsSheetnm)
}


def static "ctdc.utilities.FileOperations.xlsTOxlsx"(
    	String inputxlsname	
     , 	String outputxlsxname	) {
    (new ctdc.utilities.FileOperations()).xlsTOxlsx(
        	inputxlsname
         , 	outputxlsxname)
}


def static "ctdc.utilities.FileOperations.copySheetXLS"(
    	String fileNm	
     , 	String SheetNm	) {
    (new ctdc.utilities.FileOperations()).copySheetXLS(
        	fileNm
         , 	SheetNm)
}


def static "ctdc.utilities.FileOperations.copySheetXLSX"(
    	String fileNm	
     , 	String SheetNm	) {
    (new ctdc.utilities.FileOperations()).copySheetXLSX(
        	fileNm
         , 	SheetNm)
}


def static "ctdc.utilities.FileOperations.deleteCol"(
    	String filenm	) {
    (new ctdc.utilities.FileOperations()).deleteCol(
        	filenm)
}


def static "ctdc.utilities.FileOperations.deleteColumn"(
    	Sheet sheet	
     , 	int columnToDelete	) {
    (new ctdc.utilities.FileOperations()).deleteColumn(
        	sheet
         , 	columnToDelete)
}


def static "ctdc.utilities.FileOperations.printXLS"(
    	HSSFSheet sheet	) {
    (new ctdc.utilities.FileOperations()).printXLS(
        	sheet)
}


def static "ctdc.utilities.FileOperations.printXLSX"(
    	XSSFSheet sheet	) {
    (new ctdc.utilities.FileOperations()).printXLSX(
        	sheet)
}


def static "ctdc.utilities.FileOperations.cloneCell"(
    	Cell cNew	
     , 	Cell cOld	) {
    (new ctdc.utilities.FileOperations()).cloneCell(
        	cNew
         , 	cOld)
}


def static "ctdc.utilities.FileOperations.selectCols"(
    	String filenm	) {
    (new ctdc.utilities.FileOperations()).selectCols(
        	filenm)
}


def static "ctdc.utilities.FileOperations.deleteFiles"() {
    (new ctdc.utilities.FileOperations()).deleteFiles()
}

 /**
	 * This function reads the results table and writes the web and database data to excel
	 * This function also verifies the stat-bar counts and compares the web and database excels
	 * @param appName
	 * @param statVal
	 * @param tbl
	 * @param tblHdr
	 * @param nxtBtn
	 * @param webdataSheetName
	 * @param dbdataSheetName
	 * @param tabQuery
	 * @throws IOException
	 */ 
def static "ctdc.utilities.functions.multiFunction"(
    	String appName	
     , 	String statVal	
     , 	String tbl	
     , 	String tblHdr	
     , 	String nxtBtn	
     , 	String webdataSheetName	
     , 	String dbdataSheetName	
     , 	String tabQuery	) {
    (new ctdc.utilities.functions()).multiFunction(
        	appName
         , 	statVal
         , 	tbl
         , 	tblHdr
         , 	nxtBtn
         , 	webdataSheetName
         , 	dbdataSheetName
         , 	tabQuery)
}

 /**
	 * This function reads cases table
	 * @param statVal1
	 * @param tbl1
	 * @param hdr1
	 * @param nxtb1
	 * @param webSheetName
	 * @throws IOException
	 */ 
def static "ctdc.utilities.functions.ReadTabKatalon"(
    	String statVal1	
     , 	String tbl1	
     , 	String hdr1	
     , 	String nxtb1	
     , 	String webSheetName	) {
    (new ctdc.utilities.functions()).ReadTabKatalon(
        	statVal1
         , 	tbl1
         , 	hdr1
         , 	nxtb1
         , 	webSheetName)
}


def static "ctdc.utilities.sandbox.CaseData"() {
    (new ctdc.utilities.sandbox()).CaseData()
}


def static "ctdc.utilities.sandbox.getall"() {
    (new ctdc.utilities.sandbox()).getall()
}


def static "ctdc.utilities.sandbox.getElementID"(
    	WebElement Tab	
     , 	String caseid	) {
    (new ctdc.utilities.sandbox()).getElementID(
        	Tab
         , 	caseid)
}


def static "ctdc.utilities.sandbox.clicking"() {
    (new ctdc.utilities.sandbox()).clicking()
}


def static "ctdc.utilities.sandbox.tablesize"() {
    (new ctdc.utilities.sandbox()).tablesize()
}


def static "com.kms.katalon.keyword.applitools.BasicKeywords.checkElement"(
    	Eyes eyes	
     , 	WebElement element	) {
    (new com.kms.katalon.keyword.applitools.BasicKeywords()).checkElement(
        	eyes
         , 	element)
}


def static "com.kms.katalon.keyword.applitools.BasicKeywords.checkTestObject"(
    	TestObject testObject	
     , 	String testName	) {
    (new com.kms.katalon.keyword.applitools.BasicKeywords()).checkTestObject(
        	testObject
         , 	testName)
}


def static "com.kms.katalon.keyword.applitools.BasicKeywords.checkWindow"(
    	String testName	) {
    (new com.kms.katalon.keyword.applitools.BasicKeywords()).checkWindow(
        	testName)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesOpenWithBaseline"(
    	String baselineName	
     , 	String testName	
     , 	RectangleSize viewportSize	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesOpenWithBaseline(
        	baselineName
         , 	testName
         , 	viewportSize)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesOpen"(
    	String testName	
     , 	RectangleSize viewportSize	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesOpen(
        	testName
         , 	viewportSize)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesClose"(
    	Eyes eyes	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesClose(
        	eyes)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesInit"() {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesInit()
}
