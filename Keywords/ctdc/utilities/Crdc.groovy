package ctdc.utilities
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.Iterator;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.openqa.selenium.Keys as Keys

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Cookie as Cookie


class Crdc extends runtestcaseforKatalon implements Comparator<List<XSSFCell>>{
	public int compare( List<XSSFCell> l1, List<XSSFCell> l2 ){
		return l1.get(0).getStringCellValue().compareTo( l2.get(0).getStringCellValue() )
	}

	public static WebDriver driver

	//*************** Input functions start here ****************
	/**
	 * This function creates an instance of webdriver
	 * And navigates user to crdc application
	 */
	@Keyword
	public static void navigateToCrdc() {
		driver = CustomBrowserDriver.createWebDriver();
		driver.get(GlobalVariable.G_Urlname);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebUI.waitForPageLoad(10)
		WebUI.verifyMatch(GlobalVariable.G_Urlname, WebUI.getUrl(), false)
	}

	/**
	 * The function is used to login to CRDC application.
	 */
	@Keyword
	public static void loginToCrdc() {

		Thread.sleep(3000);
		WebUI.waitForPageLoad(10)
		WebUI.waitForElementPresent(findTestObject('CRDC/NavBar/WarningBanner_Continue-Btn'), 5)
		WebUI.click(findTestObject('CRDC/NavBar/WarningBanner_Continue-Btn'))

		WebUI.waitForElementPresent(findTestObject('CRDC/Login/LoginPageLogin-Btn'), 5)
		WebUI.click(findTestObject('CRDC/Login/LoginPageLogin-Btn'))

		WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov-Btn'), 5)
		WebUI.click(findTestObject('CRDC/Login/Login.gov-Btn'))

		WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_UserEmail-TxtBx'), 5)
		WebUI.setText(findTestObject('CRDC/Login/Login.gov_UserEmail-TxtBx'), GlobalVariable.userEmail)
		Thread.sleep(1000);

		WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_UserPass-TxtBx'), 5)
		WebUI.setText(findTestObject('CRDC/Login/Login.gov_UserPass-TxtBx'), GlobalVariable.userPassword)
		Thread.sleep(1000);

		WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_SignIn-Btn'), 5)
		WebUI.click(findTestObject('CRDC/Login/Login.gov_SignIn-Btn'))

		for (int i=1; i<=3; i++) {

			WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_BackupSecurityCode-TxtBx'), 5)

			WebUI.setText(findTestObject('CRDC/Login/Login.gov_BackupSecurityCode-TxtBx'), findTestData('CRDC/Login/LoginData').getValue('sec-backup-codes', i))
			System.out.println("Entering backup code: " + findTestData('CRDC/Login/LoginData').getValue('sec-backup-codes', i));



			WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_OneTimeCode_Submit-Btn'), 5)
			WebUI.click(findTestObject('CRDC/Login/Login.gov_OneTimeCode_Submit-Btn'))

			Thread.sleep(3000);
			String url = WebUI.getUrl();

			if (url.contains("idp.int.identity")) {

				System.out.println("Current URL is: "+ url);
				System.out.println("Old code detected, Trying new code...");

			} else {
				System.out.println("Valid Code, Continuing with Consent");
				if(i==3) {
					System.out.println("Last Code Used! Please update the input file for next run");
				}
				break;
			}

		}


		WebUI.waitForElementPresent(findTestObject('CRDC/Login/Login.gov_ConsentGrant-Btn'), 5)
		WebUI.click(findTestObject('CRDC/Login/Login.gov_ConsentGrant-Btn'))

//		if(WebUI.getUrl().contains("hub")) {
//			WebUI.waitForElementPresent(findTestObject('CRDC/Login/UserProfile-Dd'), 5)
//			WebUI.verifyElementPresent(findTestObject('CRDC/Login/UserProfile-Dd'), 5)
//			String userName = WebUI.getText(findTestObject('CRDC/Login/UserProfile-Dd'));
//
//			if(userName.contains("KATALON"))
//				System.out.println("User '" + userName + "' sucessfully logged in");
//			System.out.println("Current URL is: "+ WebUI.getUrl());
//			WebUI.verifyMatch(GlobalVariable.G_Urlname+"submissions", WebUI.getUrl(), false)
//
//		}else {
//			KeywordUtil.markFailed("Landed on the wrong page!")
//		}

	}


	/**
	 * This function gets the system's current date
	 * @param format i.e MM/DD/YYYY
	 * @return formated date
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date currentDate = new Date();
		String formattedDate = dateFormat.format(currentDate);
		return formattedDate;
	}

	/**
	 * This function enters text to any text box 
	 * @param eleObj
	 * @param fPath
	 * @param colNam
	 * @param rowNum
	 */
	@Keyword
	static void setText(TestObject eleObj, String fPath, String colNam, int rowNum) {
		TestData testData = TestDataFactory.findTestData(fPath)
		String data = testData.getValue(colNam, rowNum)
		WebUI.setText(eleObj, data)
		//WebUI.setText(findTestObject(ePath+'EstimDtaSiz-RowOne-Txtbx'), findTestData(fPath).getValue('estimtd-data-size', dataSizeRN));
		//setText(findTestObject(ePath+'AdditionalComnt-TxtBx'), fPath, 'addit-comnt',1)
	}


	/**
	 * This function validates CRDC Submission Request status bar
	 * @param Status to be validated i.e New, Submitted
	 */
	@Keyword
	public static void verifyStatusBar(String expStatus) {
		expStatus = expStatus.toUpperCase();
		String actualStatus = WebUI.getText(findTestObject('CRDC/SubmissionRequest/Status-Bar/StatusBar-Stutus'))
		String actualDate = WebUI.getText(findTestObject('CRDC/SubmissionRequest/Status-Bar/LastUpdated-Date'))
		System.out.println("Actual Status is: " + actualStatus +"\nExpected Status is: "+expStatus);
		System.out.println("Actual Date is: " + actualDate +"\nExpected Date is: "+getCurrentDate("M/d/yyyy"));
		WebUI.verifyMatch(actualStatus, expStatus, false)
		WebUI.verifyMatch(actualDate, getCurrentDate("M/d/yyyy"), false)
	}


	/**
	 * This function clears text from a text field
	 * @return String
	 */
	public static String clearText() {
		return Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
	}

	/** Add the path to the web element**/
	public static String ePath=null;

	/** Add the path to the data file**/
	public static String fPath=null;


	/**
	 * This function enters Principal Investigator information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterPiInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int instAddRN){
		Thread.sleep(1000)
		ePath = "CRDC/SubmissionRequest/Section-A/";
		fPath = "CRDC/SubmissionRequest/Section-A/principal-investigator";
		WebUI.setText(findTestObject(ePath+'PI_FirstName-Txtbx'), clearText() + findTestData(fPath).getValue('pi-first-name', fNameRN));
		WebUI.setText(findTestObject(ePath+'PI_LastName-Txtbx'), clearText() + findTestData(fPath).getValue('pi-last-name', lNameRN));
		WebUI.setText(findTestObject(ePath+'PI_Position-Txtbx'), clearText() + findTestData(fPath).getValue('position', positnRN));
		WebUI.setText(findTestObject(ePath+'PI_Email-Txtbx'), clearText() + findTestData(fPath).getValue('pi-email', emailRN));
		WebUI.setText(findTestObject(ePath+'PI_Institution-Dd'), clearText() + findTestData(fPath).getValue('pi-institution', institRN));
		WebUI.setText(findTestObject(ePath+'PI_InstitAddress-Txtbx'), clearText() + findTestData(fPath).getValue('pi-instit-address', instAddRN));
		System.out.println("Successfully entered PI information");
	}

	/**
	 *  This function enters Primary Contact information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterPrimaryContactInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int phoneRN){
		fPath = "CRDC/SubmissionRequest/Section-A/primary-contact";
		WebUI.setText(findTestObject(ePath+'PC_FirstName-Txtbx'), findTestData(fPath).getValue('pc-first-name', fNameRN));
		WebUI.setText(findTestObject(ePath+'PC_LastName-Txtbx'), findTestData(fPath).getValue('pc-last-name', lNameRN));
		WebUI.setText(findTestObject(ePath+'PC_Position-Txtbx'), findTestData(fPath).getValue('pc-position', positnRN));
		WebUI.setText(findTestObject(ePath+'PC_Email-Txtbx'), findTestData(fPath).getValue('pc-email', emailRN));
		WebUI.setText(findTestObject(ePath+'PC_Institution-Dd'), findTestData(fPath).getValue('pc-institution', institRN));
		WebUI.setText(findTestObject(ePath+'PC_Phone-Txtbx'), findTestData(fPath).getValue('pc-phone', phoneRN));
		System.out.println("Successfully entered primary contact information");
	}

	/**
	 *  This function enters Additional Contact information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterAdditionalContactInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int phoneRN) {
		fPath = "CRDC/SubmissionRequest/Section-A/additional-contact";
		Thread.sleep(1000);
		WebUI.click(findTestObject(ePath+'AddContact-Btn'))
		WebUI.setText(findTestObject(ePath+'AC_FirstName-Txtbx'), findTestData(fPath).getValue('ac-first-name', fNameRN));
		WebUI.setText(findTestObject(ePath+'AC_LastName-Txtbx'), findTestData(fPath).getValue('ac-last-name', lNameRN));
		WebUI.setText(findTestObject(ePath+'AC_Position-Txtbx'), findTestData(fPath).getValue('ac-position', positnRN));
		WebUI.setText(findTestObject(ePath+'AC_Email-Txtbx'), findTestData(fPath).getValue('ac-email', emailRN));
		WebUI.setText(findTestObject(ePath+'AC_Institution-Dd'), findTestData(fPath).getValue('ac-institution', institRN));
		WebUI.setText(findTestObject(ePath+'AC_Phone-Txtbx'), findTestData(fPath).getValue('ac-phone', phoneRN));
		System.out.println("Successfully entered additional contact information");
	}

	/**
	 * This function validates program fields on Program and Study page (section B)
	 * It is intended to be used in the class. Don't directly call it in the test-case
	 * @param rowNumber
	 */
	public static void validateProgramFields(int rowNumber) {

		ePath = "CRDC/SubmissionRequest/Section-B/";
		fPath = "CRDC/SubmissionRequest/Section-B/program-study";
		String actual=null;
		String expctd=null;
		Thread.sleep(1000)
		actual = WebUI.getAttribute(findTestObject(ePath+'ProgramTitle-TxtBx'), 'value')
		expctd = findTestData(fPath).getValue('program-title', rowNumber);
		System.out.println("Actual Program Title is: " + actual +"\nExpected Program Title is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getAttribute(findTestObject(ePath+'ProgAbbre-Txtbx'), 'value')
		expctd = findTestData(fPath).getValue('prog-abbreviation', rowNumber);
		System.out.println("Actual Program Abbreviation is: " + actual +"\nExpected Program Abbreviation is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getAttribute(findTestObject(ePath+'ProgDescrptn-Txtbx'), 'value')
		expctd = findTestData(fPath).getValue('prog-description', rowNumber);
		System.out.println("Actual Program Description is: " + actual +"\nExpected Program Description is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
	}

	/**
	 * The function enters Program info into the submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterProgramInfo(String ddValue) {

		ePath = "CRDC/SubmissionRequest/Section-B/";
		fPath = "CRDC/SubmissionRequest/Section-B/program-study";
		Thread.sleep(1000);
		WebUI.click(findTestObject(ePath+'Program-Dd'))
		Thread.sleep(500);
		GlobalVariable.CrdcUiElement=ddValue;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))

		if(ddValue.contains("Other")) {
			WebUI.setText(findTestObject(ePath+'ProgramTitle-TxtBx'), findTestData(fPath).getValue('program-title', 6)+getCurrentDate("M-d-yyyy-HH-mm"));
			WebUI.setText(findTestObject(ePath+'ProgAbbre-Txtbx'), findTestData(fPath).getValue('prog-abbreviation', 6)+getCurrentDate("M-d-yyyy-HH-mm"));
			WebUI.setText(findTestObject(ePath+'ProgDescrptn-Txtbx'), findTestData(fPath).getValue('prog-description', 6));

		}else if(ddValue.contains("CCDI")) {

			validateProgramFields(2);

		}else if(ddValue.contains("CPTAC")) {

			validateProgramFields(3);

		}else if(ddValue.contains("DCCPS")) {

			validateProgramFields(4);

		}else if(ddValue.contains("HTAN")) {

			validateProgramFields(5);

		}else {
			KeywordUtil.markFailed("Invalid Drop-down Value! Check enterProgramInfo function")
		}

		System.out.println("Successfully entered program information");
	}

	/**These variables hold date value and reused in verification functions**/
	public static String studyTitle;
	public static String studyAbrre;

	/**
	 * This function enters study information into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterStudyInfo(int stdyTitleRN, int stdyAbbRN, int stdyDesRN) {

		studyTitle=getCurrentDate("M-d-yyyy-HH-mm");
		studyAbrre=getCurrentDate("M-d-yyyy-HH-mm")
		fPath = "CRDC/SubmissionRequest/Section-B/program-study";
		Thread.sleep(1000);
		WebUI.setText(findTestObject(ePath+'StudyTitle-Txtbx'), findTestData(fPath).getValue('study-title', stdyTitleRN)+studyTitle);
		WebUI.setText(findTestObject(ePath+'StudyAbbre-Txtbx'), findTestData(fPath).getValue('study-abbreviation', stdyAbbRN)+studyAbrre);
		WebUI.setText(findTestObject(ePath+'StudyDescription-Txtbx'), findTestData(fPath).getValue('study-description', stdyDesRN));
		System.out.println("Successfully entered Study information");
	}

	/**
	 * This function enters Funding Agency and DbGaP info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterFundingAgencyAndDbGaPInfo(int fundinAgency, int grntRN, int nciPgogOfcrRN, int nciGenProgAdmnRN, int dbgapPhsNumRN) {

		Thread.sleep(1000);
		fPath = "CRDC/SubmissionRequest/Section-B/funding-agency-dbGaP";
		WebUI.setText(findTestObject(ePath+'FundingAgency-Dd'), findTestData(fPath).getValue('funding-agency', fundinAgency));
		WebUI.setText(findTestObject(ePath+'GrantContractNumber-Txtbx'), findTestData(fPath).getValue('grant-number', grntRN));
		WebUI.setText(findTestObject(ePath+'NCIProgramOfficer-Txtbx'), findTestData(fPath).getValue('nci-prog-officer', nciPgogOfcrRN));
		WebUI.setText(findTestObject(ePath+'NciGenProgAdministrator-Txtbx'), findTestData(fPath).getValue('nci-genomic-prog-admin', nciGenProgAdmnRN));

		String isRegistered = WebUI.getAttribute(findTestObject(ePath+'yesNoParentTag-TogleBtn'), 'class')

		if(!isRegistered.contains("checked")) {
			WebUI.click(findTestObject(ePath+'dbGapRegistered-TogleBtn'))
			WebUI.setText(findTestObject(ePath+'dbGapPHSNumber-Txtbx'), findTestData(fPath).getValue('dbgap-phs-num', dbgapPhsNumRN));
		}else {
			WebUI.setText(findTestObject(ePath+'dbGapPHSNumber-Txtbx'), findTestData(fPath).getValue('dbgap-phs-num', dbgapPhsNumRN));
		}

		System.out.println("Successfully entered Funding Agency and dbGaP information");
	}


	/**
	 * This function enters Publications info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterPublicationsInfo(int publiTitRN, int pubmedIdRN, int doiRN, int plndPublTitleRN) {

		fPath = "CRDC/SubmissionRequest/Section-B/publication-repository";
		WebUI.click(findTestObject(ePath+"AddExistPublication-Btn"))
		Thread.sleep(500);
		WebUI.setText(findTestObject(ePath+'PublicationTitle-Txtbx'), findTestData(fPath).getValue('publication-title', publiTitRN));
		WebUI.setText(findTestObject(ePath+'PubMedID-Txtbx'), findTestData(fPath).getValue('pubmed-id', pubmedIdRN));
		WebUI.setText(findTestObject(ePath+'DOI-Txtbx'), findTestData(fPath).getValue('doi', doiRN));

		WebUI.click(findTestObject(ePath+"AddPlannedPublication-Btn"))
		Thread.sleep(500);
		WebUI.scrollToElement(findTestObject(ePath+'PlannedPublicationTitle-Txtbx'), 3)
		WebUI.setText(findTestObject(ePath+'PlannedPublicationTitle-Txtbx'), findTestData(fPath).getValue('pland-publictn-title', plndPublTitleRN));
		WebUI.setText(findTestObject(ePath+'ExpectedPubDate-Clndr'), clearText() + getCurrentDate("MM/dd/yyyy"));
		System.out.println("Successfully entered Publications information");
	}

	/**
	 * This function enters Repository info into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterRepositoryInfo(String dropDownVlue, int repoNamRN, int stdyIdRN, int otherDataTypRN) {

		fPath = "CRDC/SubmissionRequest/Section-B/publication-repository";
		WebUI.click(findTestObject(ePath+"AddRepository-Btn"))
		Thread.sleep(500);
		WebUI.setText(findTestObject(ePath+'RepositoryName-Txtbx'), findTestData(fPath).getValue('repository-name', repoNamRN));
		WebUI.setText(findTestObject(ePath+'StudyID-Txtbx'), findTestData(fPath).getValue('study-id', stdyIdRN));
		WebUI.click(findTestObject(ePath+'DataTypesSubmitd-Dd'));
		Thread.sleep(500);
		GlobalVariable.CrdcUiElement=dropDownVlue;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'));
		GlobalVariable.CrdcUiElement="Proteomics";
		WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))
		WebUI.setText(findTestObject(ePath+'OtherDataTypes-Txtbx'), findTestData(fPath).getValue('other-data-types', otherDataTypRN));
		System.out.println("Successfully entered Repository information");
	}


	/**
	 * This function enters Data Access Types and Cancer Types into submission request form
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void enterDataAccessAndDiseaseInfo(String cancerType, String preCancerType, String speciesOfSub, int otherCancerTyRN, int otherPreCancerTyRN, int numOfSubjectRN) {

		ePath = "CRDC/SubmissionRequest/Section-C/";
		fPath = "CRDC/SubmissionRequest/Section-C/data-access-disease";
		Thread.sleep(1000)
		WebUI.click(findTestObject(ePath+"OpenAccess-Chkbx"))
		WebUI.click(findTestObject(ePath+"CancerTypes-Dd"))
		GlobalVariable.CrdcUiElement=cancerType;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))

		GlobalVariable.CrdcUiElement="Bone";
		WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))
		WebUI.setText(findTestObject(ePath+'OtherCancerTypes-Txtbx'), findTestData(fPath).getValue('other-cancer-type', otherCancerTyRN));

		Thread.sleep(500);
		WebUI.click(findTestObject(ePath+"PreCancerTypes-Dd"))
		GlobalVariable.CrdcUiElement=preCancerType;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))
		GlobalVariable.CrdcUiElement="Lung";
		WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))
		WebUI.setText(findTestObject(ePath+'OtherPreCancerTypes-Txtbx'), findTestData(fPath).getValue('other-pre-cancer-type', otherPreCancerTyRN));

		Thread.sleep(500);
		WebUI.click(findTestObject(ePath+"SpeciesOfSubjects-Dd"))
		GlobalVariable.CrdcUiElement=speciesOfSub;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))
		GlobalVariable.CrdcUiElement="Rattus";
		WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))
		WebUI.setText(findTestObject(ePath+'NumOfSubjectsIncludInSub-Txtbx'), findTestData(fPath).getValue('num-of-subjects-included', numOfSubjectRN));
		WebUI.click(findTestObject(ePath+"CellLines-Chkbx"))
		WebUI.click(findTestObject(ePath+"ConfirmDataSubAreDeIdenified-Yes-RdoBtn"))
		System.out.println("Successfully entered Data Access Types and Cancer Types information");
	}

	/**
	 * This function selects Data Types on the submission request form
	 * @param buttonLable Toggle button label to be clicked (enter only one word per toggle button)
	 */
	@Keyword
	public static void selectDataTypes(String... buttonLable) {

		ePath = "CRDC/SubmissionRequest/Section-D/";
		fPath = "CRDC/SubmissionRequest/Section-D/data-types";
		Thread.sleep(1000)
		WebUI.setText(findTestObject(ePath+'TragetSubmDelivryDate-Clndr'), clearText() + getCurrentDate("MM/dd/yyyy"));
		WebUI.setText(findTestObject(ePath+'ExpctdPubliDate-Clndr'), clearText() + getCurrentDate("MM/dd/yyyy"));

		//Verify default is 'No' for all data types
		List elements = WebUI.findWebElements(findTestObject(ePath+'AllSlider-Btns'), 20)
		for (WebElement element : elements) {
			String value = element.getAttribute('class')
			WebUI.verifyMatch(value, 'textChecked', false)
		}

		//Select data type based on the user provided value
		for (String label : buttonLable) {
			label = label.toLowerCase();
			GlobalVariable.CrdcUiElement=label;

			if(label.contains("other")) {
				WebUI.setText(findTestObject(ePath+'OthrDtaTyp-Txtbx'), findTestData(fPath).getValue('other', 1));

			}else if(label.equals("other-clinical")){
				WebUI.setText(findTestObject(ePath+'OthrClinclDtaTyp-Txtbx'), findTestData(fPath).getValue('othr-clinicl', 1));

			}else if(label.contains("imaging")) {
				WebUI.click(findTestObject('CRDC/SubmissionRequest/Toggle-Btn'))
				WebUI.click(findTestObject(ePath+'ConfirmDataIdentified_yes-RdoBtn'))

			}else {
				WebUI.click(findTestObject('CRDC/SubmissionRequest/Toggle-Btn'))
			}

			System.out.println("Successfully selected '"+label+"' data type");
		}
	}



	/**
	 * This function selects File Types of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */
	@Keyword
	public static void selectFileTypes(int fileTypRN, int fileExtRN, int numOfFileRN, int dataSizeRN) {

		fPath = "CRDC/SubmissionRequest/Section-D/file-types";
		WebUI.scrollToElement(findTestObject(ePath+'FileType-RowOne-Dd'), 20)
		WebUI.click(findTestObject(ePath+'FileType-RowOne-Dd'))
		String fileType = findTestData(fPath).getValue('file-type', fileTypRN)
		GlobalVariable.CrdcUiElement = fileType;
		Thread.sleep(500);
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))

		WebUI.click(findTestObject(ePath+'FileExtension-RowOne-Dd'))
		String fileExten = findTestData(fPath).getValue('file-extension', fileExtRN);
		GlobalVariable.CrdcUiElement = fileExten;
		WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))

		WebUI.setText(findTestObject(ePath+'NumOfFiles-RowOne-Txtbx'), findTestData(fPath).getValue('num-of-file', numOfFileRN));
		WebUI.setText(findTestObject(ePath+'EstimDtaSiz-RowOne-Txtbx'), findTestData(fPath).getValue('estimtd-data-size', dataSizeRN));
		WebUI.setText(findTestObject(ePath+'AdditionalComnt-TxtBx'), findTestData(fPath).getValue('addit-comnt', 1));
		System.out.println("Successfully selected file type '"+fileType+ "' with extension '"+fileExten+"'");
	}



	//*************** Verification functions start here ****************

	static String actual=null;
	static String expctd=null;

	/**
	 * This function selects File Types of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */
	@Keyword
	public static void verifyPiInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int instAddRN){
		Thread.sleep(1000)
		ePath = "CRDC/SubmissionRequest/ReviewSubmit/";
		fPath = "CRDC/SubmissionRequest/Section-A/principal-investigator";

		actual = WebUI.getText(findTestObject(ePath+'PI_Name-Txt'))
		expctd = findTestData(fPath).getValue('pi-last-name', lNameRN)+", " +findTestData(fPath).getValue('pi-first-name', fNameRN)
		System.out.println("Actual PI Name is: " + actual +"\nExpected PI Name is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PI_Position-Txt'))
		expctd = findTestData(fPath).getValue('position', positnRN)
		System.out.println("Actual PI Position is: " + actual +"\nExpected PI Position is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PI_Email-Txt'))
		expctd = findTestData(fPath).getValue('pi-email', emailRN)
		System.out.println("Actual PI email is: " + actual +"\nExpected PI email is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PI_InstitName-Txt'))
		expctd = findTestData(fPath).getValue('pi-institution', institRN)
		System.out.println("Actual PI institution is: " + actual +"\nExpected PI institution is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PI_InstitAddress-Txt'))
		expctd = findTestData(fPath).getValue('pi-instit-address', instAddRN)
		System.out.println("Actual PI institution address is: " + actual +"\nExpected PI institution address is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
	}

	/**
	 * This function verifies Primary contact info on Review Page
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void verifyPrimaryContactInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int phoneRN){

		fPath = "CRDC/SubmissionRequest/Section-A/primary-contact";

		actual = WebUI.getText(findTestObject(ePath+'PC_Name-Txt'))
		expctd = findTestData(fPath).getValue('pc-last-name', lNameRN)+", " +findTestData(fPath).getValue('pc-first-name', fNameRN)
		System.out.println("Actual Primary Contact Name is: " + actual +"\nExpected Primary Contact Name is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PC_Position-Txt'))
		expctd = findTestData(fPath).getValue('pc-position', positnRN)
		System.out.println("Actual Primary Contact Position is: " + actual +"\nExpected Primary Contact Position is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PC_Email-Txt'))
		expctd = findTestData(fPath).getValue('pc-email', emailRN)
		System.out.println("Actual Primary Contact email is: " + actual +"\nExpected Primary Contact email is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PC_InstitName-Txt'))
		expctd = findTestData(fPath).getValue('pc-institution', institRN)
		System.out.println("Actual Primary Contact institution is: " + actual +"\nExpected Primary Contact institution is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PC_InstitName-Txt'))
		expctd = findTestData(fPath).getValue('pc-institution', institRN)
		System.out.println("Actual Primary Contact phone is: " + actual +"\nExpected Primary Contact phone is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

	}

	/**
	 * This function verifies additional contact info of the submission request form
	 * @param  Row Number of the data to be selected/entered
	 */
	@Keyword
	public static void verifyAdditionalContactInfo(int fNameRN, int lNameRN, int positnRN, int emailRN, int institRN, int phoneRN) {
		fPath = "CRDC/SubmissionRequest/Section-A/additional-contact";

		actual = WebUI.getText(findTestObject(ePath+'AC_Name-Txt'))
		expctd = findTestData(fPath).getValue('ac-last-name', lNameRN)+", " +findTestData(fPath).getValue('ac-first-name', fNameRN)
		System.out.println("Actual Aditional Contact Name is: " + actual +"\nExpected Aditional Contact Name is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'AC_Position-Txt'))
		expctd = findTestData(fPath).getValue('ac-position', positnRN)
		System.out.println("Actual Aditional Contact Position is: " + actual +"\nExpected Aditional Contact Position is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'AC_Email-Txt'))
		expctd = findTestData(fPath).getValue('ac-email', emailRN)
		System.out.println("Actual Aditional Contact email is: " + actual +"\nExpected Aditional Contact email is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'AC_InstitName-Txt'))
		expctd = findTestData(fPath).getValue('ac-institution', institRN)
		System.out.println("Actual Aditional Contact institution is: " + actual +"\nExpected Aditional Contact institution is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		//Write script to convert the phone number format
		//actual = WebUI.getText(findTestObject(ePath+'AC_Phone-Txt'))
		//expctd = findTestData(fPath).getValue('ac-phone', institRN)
		//System.out.println("Actual Aditional Contact phone is: " + actual +"\nExpected Aditional Contact phone is: "+expctd);
		//WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified additional contact information");
	}

	/**
	 * This function verifies program info on review page
	 * @param rowNumber
	 */
	@Keyword
	public static void verifyProgramInfo(int rowNumber) {

		ePath = "CRDC/SubmissionRequest/ReviewSubmit/";
		fPath = "CRDC/SubmissionRequest/Section-B/program-study";

		actual = WebUI.getText(findTestObject(ePath+'ProgramTitle-Txt'))
		expctd = findTestData(fPath).getValue('program-title', rowNumber);
		System.out.println("Actual Program Title is: " + actual +"\nExpected Program Title is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'ProgAbbre-Txt'))
		expctd = findTestData(fPath).getValue('prog-abbreviation', rowNumber);
		System.out.println("Actual Program Abbreviation is: " + actual +"\nExpected Program Abbreviation is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'ProgDescrptn-Txt'))
		expctd = findTestData(fPath).getValue('prog-description', rowNumber);
		System.out.println("Actual Program Description is: " + actual +"\nExpected Program Description is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
	}

	/**
	 * This function verifies study information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void verifyStudyInfo(int stdyTitleRN, int stdyAbbRN, int stdyDesRN) {

		fPath = "CRDC/SubmissionRequest/Section-B/program-study";

		actual = WebUI.getText(findTestObject(ePath+'StudyTitle-Txt'))
		expctd = findTestData(fPath).getValue('study-title', stdyTitleRN)+ studyTitle
		System.out.println("Actual study title is: " + actual +"\nExpected study title is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'StudyAbbre-Txt'))
		expctd = findTestData(fPath).getValue('study-abbreviation', stdyAbbRN)+studyAbrre
		System.out.println("Actual study abbreviation is: " + actual +"\nExpected study abbreviation is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'StudyDscrptin-Txt'))
		expctd = findTestData(fPath).getValue('study-description', stdyDesRN)
		System.out.println("Actual study description is: " + actual +"\nExpected study description is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified Study information");
	}

	/**
	 *  This function verifies Funding Agency and DbGaP information on the review page
	 * @param dataFileRowNum Data file row number to be read
	 */
	@Keyword
	public static void verifyFundingAgencyAndDbGaPInfo(int fundinAgency, int grntRN, int nciPgogOfcrRN, int nciGenProgAdmnRN, int dbgapPhsNumRN) {

		fPath = "CRDC/SubmissionRequest/Section-B/funding-agency-dbGaP";

		actual = WebUI.getText(findTestObject(ePath+'FndngAgncy-Txt'))
		expctd = findTestData(fPath).getValue('funding-agency', fundinAgency)
		System.out.println("Actual Funding agency is: " + actual +"\nExpected Funding agency is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'GrantCntractNum-Txt'))
		expctd = findTestData(fPath).getValue('grant-number', grntRN)
		System.out.println("Actual Grant number is: " + actual +"\nExpected Grant number is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'NCIProgOficer-Txt'))
		expctd = findTestData(fPath).getValue('nci-prog-officer', nciPgogOfcrRN)
		System.out.println("Actual NCI program officer is: " + actual +"\nExpected NCI program officer is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'NciGenProgAdmstr-Txt'))
		expctd = findTestData(fPath).getValue('nci-genomic-prog-admin', nciGenProgAdmnRN)
		System.out.println("Actual NCI program Administrator is: " + actual +"\nExpected NCI program Administrator is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		//Add 'HAS YOUR STUDY BEEN REGISTERED IN dbGaP? Yes No verification here
		actual = WebUI.getText(findTestObject(ePath+'dbGapPHSNumber-Txt'))
		expctd = findTestData(fPath).getValue('dbgap-phs-num', dbgapPhsNumRN)
		System.out.println("Actual dbGaP PHS number is: " + actual +"\nExpected dbGaP PHS number is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified Funding Agency and dbGaP information");
	}

	/**
	 *  This function verifies Publications information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void verifyPublicationsInfo(int publiTitRN, int pubmedIdRN, int doiRN, int plndPublTitleRN) {

		fPath = "CRDC/SubmissionRequest/Section-B/publication-repository";

		//Need to add a method to verify if publication is added, then verify, otherwise don't verify
		actual = WebUI.getText(findTestObject(ePath+'PublictnTitle-Txt'))
		expctd = findTestData(fPath).getValue('publication-title', publiTitRN)
		System.out.println("Actual Publications title is: " + actual +"\nExpected Publications title is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PubMedID-Txt'))
		expctd = findTestData(fPath).getValue('pubmed-id', pubmedIdRN)
		System.out.println("Actual PubMed ID is: " + actual +"\nExpected PubMed ID is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'DOI-Txt'))
		expctd =  findTestData(fPath).getValue('doi', doiRN)
		System.out.println("Actual PubMed ID is: " + actual +"\nExpected PubMed ID is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		//Need to add a method to verify if planned publication is added, then verify, otherwise don't verify

		actual = WebUI.getText(findTestObject(ePath+'PlnedPublTitle-Txt'))
		expctd =  findTestData(fPath).getValue('pland-publictn-title', plndPublTitleRN)
		System.out.println("Actual Planned publication title is: " + actual +"\nExpected Planned publication title is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'ExpctdPubDate-Txt'))
		expctd =  getCurrentDate("MM/dd/yyyy")
		System.out.println("Actual Publication date is: " + actual +"\nExpected Publication date is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified Publications information");
	}

	/**
	 *  This function verifies Repository information on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void verifyRepositoryInfo(String dropDownVlue, int repoNamRN, int stdyIdRN, int otherDataTypRN) {

		ePath = "CRDC/SubmissionRequest/ReviewSubmit/";
		fPath = "CRDC/SubmissionRequest/Section-B/publication-repository";

		actual = WebUI.getText(findTestObject(ePath+'RepoName-Txt'))
		expctd = findTestData(fPath).getValue('repository-name', repoNamRN)
		System.out.println("Actual Repository name is: " + actual +"\nExpected Repository name is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'StudyID-Txt'))
		expctd = findTestData(fPath).getValue('study-id', stdyIdRN)
		System.out.println("Actual Study ID is: " + actual +"\nExpected Study ID is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)


		//Add code to verify DATA TYPE(S) SUBMITTED. Add if condition. i.e. if is clinical then do.... by checking current ui value
		//See below code to come up with a logic
		//WebUI.click(findTestObject(ePath+'DataTypesSubmitd-Dd'));
		//Thread.sleep(500);
		//GlobalVariable.CrdcUiElement=dropDownVlue;
		//WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'));
		//GlobalVariable.CrdcUiElement="Proteomics";
		//WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))

		actual = WebUI.getText(findTestObject(ePath+'RepositoryOtherDataTypes-Txt'))
		expctd = findTestData(fPath).getValue('other-data-types', otherDataTypRN)
		System.out.println("Actual Other data type is: " + actual +"\nExpected Other data type is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified Repository information");
	}

	/**
	 * This function verifies Data Access Types and Cancer Types on the review page
	 * @param dataFileRowNum Please add data file row number to be read
	 */
	@Keyword
	public static void verifyDataAccessAndDiseaseInfo(String cancerType, String preCancerType, String speciesOfSub, int otherCancerTyRN, int otherPreCancerTyRN, int numOfSubjectRN) {

		fPath = "CRDC/SubmissionRequest/Section-C/data-access-disease";
		WebUI.scrollToElement(findTestObject(ePath+'OtherCancerTypes-Txt'), 3)

		actual = WebUI.getText(findTestObject(ePath+'AccessTypes-Txt'))
		expctd = findTestData(fPath).getValue('access-types', 1)
		System.out.println("Actual Access type is: " + actual +"\nExpected Access type is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'CancerTypes-Txt'))

		//Based on the user input, pull the corresponding value from excel for cancer type
		switch (cancerType.toLowerCase()) {

			case 'adrenocortical':
				expctd = findTestData(fPath).getValue('cancer-type', 1)
				break;
			case 'pheochromocytoma':
				expctd = findTestData(fPath).getValue('cancer-type', 2)
				break;
			case 'cholangiocarcinoma':
				expctd = findTestData(fPath).getValue('cancer-type', 3)
				break;
			case 'bladder':
				expctd = findTestData(fPath).getValue('cancer-type', 4)
				break;
			case 'lymphocytic':
				expctd = findTestData(fPath).getValue('cancer-type', 5)
				break;
			case 'myeloid':
				expctd = findTestData(fPath).getValue('cancer-type', 6)
				break;
			case 'sarcoma':
				expctd = findTestData(fPath).getValue('cancer-type', 7)
				break;
			default:
				KeywordUtil.markFailed("Invalid Cancer Type Drop-Down Value: "+cancerType)
		}

		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'OtherCancerTypes-Txt'))
		expctd = findTestData(fPath).getValue('other-cancer-type', otherCancerTyRN)
		System.out.println("Actual Other cancer type is: " + actual +"\nExpected Other cancer type is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'PreCancerTypes-Txt'))

		//Based on the user input, pull the corresponding value from excel for pre-cancer type
		switch (preCancerType.toLowerCase()) {

			case 'lung':
				expctd = findTestData(fPath).getValue('pre-cancer-type', 1)
				break;
			case 'breast':
				expctd = findTestData(fPath).getValue('pre-cancer-type', 2)
				break;
			default:
				KeywordUtil.markFailed("Invalid Pre-Cancer Type Drop-Down Value: "+preCancerType)
		}

		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'OtherPreCancerTypes-Txt'))
		expctd = findTestData(fPath).getValue('other-pre-cancer-type', otherPreCancerTyRN)
		System.out.println("Actual Other pre-cancer type is: " + actual +"\nExpected Other pre-cancer type is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		//Add code for Species Of Subject
		//WebUI.click(findTestObject(ePath+"SpeciesOfSubjects-Dd"))
		//GlobalVariable.CrdcUiElement=speciesOfSub;
		//WebUI.click(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'))
		//GlobalVariable.CrdcUiElement="Rattus";
		//WebUI.sendKeys(findTestObject('CRDC/SubmissionRequest/CrdcDdValue'), Keys.chord(Keys.TAB))

		actual = WebUI.getText(findTestObject(ePath+'SpeciesOfSubject-Txt'))

		//Based on the user input, pulling the corresponding value from excel
		switch (speciesOfSub.toLowerCase()) {

			case 'homo':
				expctd = findTestData(fPath).getValue('species-of-subject', 1)
				break;
			case 'mus':
				expctd = findTestData(fPath).getValue('species-of-subject', 2)
				break;
			case 'canis':
				expctd = findTestData(fPath).getValue('species-of-subject', 3)
				break;
			case 'rattus':
				expctd = findTestData(fPath).getValue('species-of-subject', 4)
				break;
			default:
				KeywordUtil.markFailed("Invalid Species of Subject Drop-Down Value: "+cancerType)
		}

		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'NumOfSubjInclud-Txt'))
		expctd = findTestData(fPath).getValue('num-of-subjects-included', numOfSubjectRN)
		System.out.println("Actual number of subject included in sub is: " + actual +"\nExpected number of subject included in sub is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'CellLines-Txt'))
		expctd = findTestData(fPath).getValue('cell-lines', 1)
		System.out.println("Actual Cell lines is: " + actual +"\nExpected Cell lines is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'DataDeIdentified-Txt'))
		expctd = findTestData(fPath).getValue('data-de-identified', 1)
		System.out.println("Actual Data De-Identified is: " + actual +"\nExpected Data De-Identified is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		System.out.println("Successfully verified Data Access Types and Cancer Types");
	}

	/**
	 * This function verifies Data Types of the submission request form review page
	 * @param buttonLable Toggle button label to be clicked (enter only one word per toggle button)
	 */
	@Keyword
	public static void verifyDataTypes(String... buttonLable) {

		fPath = "CRDC/SubmissionRequest/Section-D/data-types";

		actual = WebUI.getText(findTestObject(ePath+'TragetSubmDelivryDate-Txt'))
		expctd = getCurrentDate("MM/dd/yyyy")
		System.out.println("Actual target data submission delivery date is: " + actual +"\nExpected target data submission delivery date is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'ExpctdPubliDateSec-D-RevPage-Txt'))
		expctd =  getCurrentDate("MM/dd/yyyy")
		System.out.println("Actual Publication date is: " + actual +"\nExpected Publication date is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified Data Types delivery dates");

		//Verifying data type based on the user provided value
		for (String label : buttonLable) {
			label = label.toLowerCase();
			GlobalVariable.CrdcUiElement=label;

			switch (label) {
				case 'clinical':
					actual = WebUI.getText(findTestObject(ePath+'ClinicalTrial-Txt'))
					expctd = findTestData(fPath).getValue('clinical-trial', 2)
					break;

				case 'immunology':
					actual = WebUI.getText(findTestObject(ePath+'Imunology-Txt'))
					expctd = findTestData(fPath).getValue('immunology', 2)
					break;

				case 'genomics':
					actual = WebUI.getText(findTestObject(ePath+'Genomics-Txt'))
					expctd = findTestData(fPath).getValue('genomics', 2)
					break;

				case 'proteomics':
					actual = WebUI.getText(findTestObject(ePath+'Proteomics-Txt'))
					expctd = findTestData(fPath).getValue('proteomics', 2)
					break;

				case 'imaging':
					actual = WebUI.getText(findTestObject(ePath+'Imaging-Txt'))
					expctd = findTestData(fPath).getValue('imaging', 2)
					break;

				case 'epidemiologic':
					actual = WebUI.getText(findTestObject(ePath+'EpidemilogicCohort-Txt'))
					expctd = findTestData(fPath).getValue('epide-cohort', 2)
					break;

				case 'other':
					actual = WebUI.getText(findTestObject(ePath+'OtherDataTypes-Txt'))
					expctd = findTestData(fPath).getValue('other', 1)
					break;

				case 'demographic':
					actual = WebUI.getText(findTestObject(ePath+'Demographic-Txt'))
					expctd = findTestData(fPath).getValue('demog-dta', 2)
					break;

				case 'relapse':
					actual = WebUI.getText(findTestObject(ePath+'Relapse-Txt'))
					expctd = findTestData(fPath).getValue('relapse-dta', 2)
					break;

				case 'diagnosis':
					actual = WebUI.getText(findTestObject(ePath+'Diagnosis-Txt'))
					expctd = findTestData(fPath).getValue('diagno-dta', 2)
					break;

				case 'outcome':
					actual = WebUI.getText(findTestObject(ePath+'Outcome-Txt'))
					expctd = findTestData(fPath).getValue('outcome', 2)
					break;

				case 'treatment':
					actual = WebUI.getText(findTestObject(ePath+'Treatment-Txt'))
					expctd = findTestData(fPath).getValue('teatmnt-dta', 2)
					break;

				case 'other-clinical':
					actual = WebUI.getText(findTestObject(ePath+'OthrClinclDtaTyp-Txt'))
					expctd = findTestData(fPath).getValue('othr-clinicl', 1)
					break;

				default:
					WebUI.click(findTestObject('CRDC/SubmissionRequest/Toggle-Btn'))

			}

			System.out.println("Actual "+label+" data type is: " + actual +"\nExpected "+label+" data type is: "+expctd);
			WebUI.verifyMatch(actual, expctd, false)
			System.out.println("Successfully verified '"+label+"' data type");
		}
	}


	/**
	 * This function verifies File Types on review page
	 * @param  Row Number of the data to be verified
	 */
	@Keyword
	public static void verifyFileTypes(int fileTypRN, int fileExtRN, int numOfFileRN, int dataSizeRN) {

		fPath = "CRDC/SubmissionRequest/Section-D/file-types";

		actual = WebUI.getText(findTestObject(ePath+'FileType-RowOne-Txt'))
		expctd = findTestData(fPath).getValue('file-type', fileTypRN)
		System.out.println("Actual file type is: " + actual +"\nExpected file type is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'FileExtension-Txt'))
		expctd = findTestData(fPath).getValue('file-extension', fileExtRN)
		System.out.println("Actual file extension is: " + actual +"\nExpected file extension is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'NumOfFiles-RowOne-Txt'))
		expctd = findTestData(fPath).getValue('num-of-file', numOfFileRN)
		System.out.println("Actual number of files: " + actual +"\nExpected number of files: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'EstimDtaSiz-RowOne-Txt'))
		expctd =  findTestData(fPath).getValue('estimtd-data-size', dataSizeRN)
		System.out.println("Actual data size is: " + actual +"\nExpected data size is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)

		actual = WebUI.getText(findTestObject(ePath+'AdditionalComnt-Txt'))
		expctd = findTestData(fPath).getValue('addit-comnt', 1)
		System.out.println("Actual comment is: " + actual +"\nExpected comment is: "+expctd);
		WebUI.verifyMatch(actual, expctd, false)
		System.out.println("Successfully verified File Types");
	}


	/**
	 * This function clicks on submit and confirm submit buttons
	 */
	@Keyword
	public static void clickSubmitButton() {

		WebUI.click(findTestObject('CRDC/SubmissionRequest/Submit-Btn'))
		Thread.sleep(500)
		WebUI.click(findTestObject('CRDC/SubmissionRequest/PopUpConfirmToSubmit-Btn'))
		Thread.sleep(500)
		WebUI.verifyElementPresent(findTestObject('CRDC/NavBar/Start_a_SubmissionRequest-Btn'), 10)
	}



}//class ends