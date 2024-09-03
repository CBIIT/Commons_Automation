package utilities

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.print.DocFlavor.STRING

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable



public class TestRunner implements Comparator<List<XSSFCell>>{
	public int compare( List<XSSFCell> l1, List<XSSFCell> l2 ){
		return l1.get(0).getStringCellValue().compareTo( l2.get(0).getStringCellValue() )
	}


	public static WebDriver driver
	public static WebElement nxtBtn
	public static String appKey = GlobalVariable.AppKey;


	@Keyword
	public void Login (String signinButton, String emailID, String emailNxtBtn, String Passwd, String PasswdNxtBtn){
		String xsigninButton = givexpath(signinButton)
		String xemailNxtBtn = givexpath(emailNxtBtn)
		String xPasswd = givexpath(Passwd)
		String xPasswdNxtBtn = givexpath(PasswdNxtBtn)

		JavascriptExecutor js = (JavascriptExecutor)driver;

		Set<String> allHandlesb4signin = driver.getWindowHandles();
		System.out.println("Count of windows BEFORE sign in with google :"+allHandlesb4signin.size());
		System.out.println(allHandlesb4signin);
		String currentWindowHandleB4 = allHandlesb4signin.iterator().next();
		System.out.println("currentWindow Handle - default handle before signin : "+currentWindowHandleB4);

		//removed the if loop


		System.out.println("Waiting to log in")

		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(xsigninButton)));
		Set<String> allHandlesAftersignin = driver.getWindowHandles();
		System.out.println("Count of windows AFTER sign in with google :"+allHandlesAftersignin.size());
		System.out.println(allHandlesAftersignin);
		String parent=driver.getWindowHandle();
		for(String curWindow : allHandlesAftersignin){
			System.out.println ("This is the id of the curr window :"+curWindow)
			driver.switchTo().window(curWindow);   //switching to the child window
		}
		String currentWindowHandleAFTER = allHandlesAftersignin.iterator().next();
		System.out.println("currentWindow Handle -default after signin : "+currentWindowHandleAFTER);
		//store parent window & child window

		//switch to child window
		WebUI.switchToWindowIndex(1)
		String FirstWndUrl = driver.getCurrentUrl();
		System.out.println("First Popup window's url: " + FirstWndUrl)
		driver.manage().window().maximize();


		//Entering the email id or username
		Thread.sleep(2000)
		driver.findElement(By.xpath(xemailID)).sendKeys(GlobalVariable.G_AppUserName);
		System.out.println("Reading the text typed in email field: "+driver.findElement(By.xpath(xemailID)).getAttribute("value"));
		Thread.sleep(2000)


		//Clicking the next button after email id
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(xemailNxtBtn)));
		Thread.sleep(10000);

		//Entering the password
		driver.findElement(By.xpath(xPasswd)).sendKeys(GlobalVariable.G_AppPassword);
		System.out.println("Getting password: "+driver.findElement(By.xpath(xPasswd)).getAttribute("value"));
		Thread.sleep(2000)

		//Clicking the next button after password
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(xPasswdNxtBtn)));
		Thread.sleep(3000)

		System.out.println("Typed password and clicked the next button in password window. Moving back to the parent window handle")
		driver.switchTo().window(parent);
		driver.manage().window().maximize();
		Thread.sleep(3000)

		System.out.println("This is the current url post login & moving back to parent window handle : "+ driver.getCurrentUrl() );

		Set<String> allHandlesAfterLogin = driver.getWindowHandles();
		System.out.println(allHandlesAfterLogin);
		System.out.println("Count of windows after successful login :"+allHandlesAfterLogin.size());
		String curWindowHandlePostLogin = allHandlesAfterLogin.iterator().next();
		System.out.println("currentWindow Handle -default after successful login : "+curWindowHandlePostLogin);

		System.out.println("After successful login, the landing page's url: " + driver.getCurrentUrl())
	}//login function ends here




	/**
	 * This function reads the new excel file name from InputFiles
	 * @param input_file
	 */
	@Keyword
	public  void RunKatalon(String input_file) {

		String url = GlobalVariable.G_Urlname;
		String usrDir = System.getProperty("user.dir");
		String inputFiles = "InputFiles";
		Path filePath;


		if(appKey.equals("Bento")) {
			filePath = Paths.get(usrDir, inputFiles, "Bento", input_file);
		}else if(appKey.equals("ICDC")) {
			filePath = Paths.get(usrDir, inputFiles, "ICDC", input_file);
		}else if(appKey.equals("CCDI")) {
			filePath = Paths.get(usrDir, inputFiles, "CCDI", input_file);
		}else if(appKey.equals("C3DC")) {
			filePath = Paths.get(usrDir, inputFiles, "C3DC", input_file);
		}else if(appKey.equals("INS")) {
			filePath = Paths.get(usrDir, inputFiles, "INS", input_file);
		}else if(appKey.equals("CDS")) {
			filePath = Paths.get(usrDir, inputFiles, "CDS", input_file);
		}else if(appKey.equals("CTDC")) {
			filePath = Paths.get(usrDir, inputFiles, "CTDC", input_file);
		}else if(appKey.equals("MTP")) {
			filePath = Paths.get(usrDir, inputFiles, "MTP", input_file);
		}else if(appKey.equals("CCDC")) {
			filePath = Paths.get(usrDir, inputFiles, "CCDC", input_file);
		}else if(appKey.equals("CRDC")) {
			filePath = Paths.get(usrDir, inputFiles, "CRDC", input_file);
		}else {
			KeywordUtil.markFailed("Invalid App Key: Check Profile or RunKatalon function")
		}

		if (filePath !=null) {
			KeywordUtil.markPassed("This is the full file path: "+filePath.toString())
			GlobalVariable.InputExcel=filePath.toString();
		}else{
			KeywordUtil.markFailed("Password File is not found")
		}


		KeywordUtil.logInfo("Global variable set for password file is: " + GlobalVariable.InputExcel )
		Thread.sleep(2000)
		List<List<XSSFCell>> sheetData_K = new ArrayList<>();
		FileInputStream fis = new FileInputStream(GlobalVariable.InputExcel);
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // Create an excel workbook from the file system.
		int numberOfSheets = workbook.getNumberOfSheets();// Get the  sheets on the workbook
		int countrow = 0
		int countcol= 0
		Thread.sleep(2000)
		XSSFSheet sheet = workbook.getSheetAt(0);  //reading input query
		countrow = sheet.lastRowNum- sheet.firstRowNum;
		System.out.println ("Row count of input excel is: " + countrow);
		countcol = sheet.getRow(0).getLastCellNum();
		System.out.println("Colm count of input excel is: " + countcol);

		//This loops through the rows of the table till there is next row
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			List<XSSFCell> data = new ArrayList<>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				data.add(cell);
			}
			sheetData_K.add(data);
		}

		KeywordUtil.markPassed("Data loaded from input file for the test case." )
		driver = CustomBrowserDriver.createWebDriver();
		System.out.println("This is the driver from inside the runkatalon method : "+driver)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		excelparsingKatalon(sheetData_K, driver);
		//System.out.println("Entire input excel data is: " + sheetData_K)
	}


	/**
	 * This function converts any given string value into integer
	 * @param stringVal Add the value to be converted to Int
	 * @return
	 */
	public static int convStringtoInt (String stringVal)	{
		int i =0;
		try {
			System.out.println("string value is = " + stringVal);
			i = Integer.parseInt(stringVal.trim());
			System.out.println("integer value is = " + i);
		}
		catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException: " + nfe.getMessage());
		}
		return i;
	}



	/**
	 * Gayathri will update this later..
	 */
	public static void manifestDownloadRobot(){
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}


	/**
	 * This function reads input excels and assigns global variables to each query...
	 * @param sheetData
	 * @param dr
	 */
	private static void excelparsingKatalon(List<List<XSSFCell>> sheetData, WebDriver dr) {
		System.out.println("This is the value of browser driver from exelparsingkatalon: "+dr)

		System.out.println("This is urlname: "+GlobalVariable.G_Urlname)
		driver.get(GlobalVariable.G_Urlname)
		driver.manage().window().maximize()
		System.out.println("The window is maximized")
		Thread.sleep(3000)
		int countrow = 0
		countrow = sheetData.size();
		System.out.println ( "row count from initializing fnc: " + countrow )
		System.out.println ( "sheet  data size: " + sheetData.get(0).size())

		//Loop through rows
		for (int i = 1; i < countrow; i++){
			List<XSSFCell> datarow = sheetData.get(i);
			System.out.println ("Columns size from initializing fnc:  " + datarow.size())
			String str = "";
			//loop through columns
			for (int j = 0; j < datarow.size(); j++){
				//				System.out.println ("Value of  i: "  + i + "  Value of j  : " + j )
				XSSFCell cell = datarow.get(j);
				//Look for specific column names to perform action
				switch(sheetData.get(0).get(j).getStringCellValue().trim() ) {
					case("TabName"):
						GlobalVariable.G_inputTabName = sheetData.get(i).get(j).getStringCellValue()
						System.out.println("This is the tabname from input excel : "+GlobalVariable.G_inputTabName)
						break;
					case("query"):
						if(GlobalVariable.G_inputTabName=="CasesTab"){
							GlobalVariable.G_QueryCasesTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of cases tab query from switch case : "+GlobalVariable.G_QueryCasesTab)
						}else if(GlobalVariable.G_inputTabName=="SamplesTab"){
							GlobalVariable.G_QuerySamplesTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of samples tab query from switch case : "+GlobalVariable.G_QuerySamplesTab)
						}else if(GlobalVariable.G_inputTabName=="FilesTab"){
							GlobalVariable.G_QueryFilesTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of files tab query from switch case : "+GlobalVariable.G_QueryFilesTab)
						}else if(GlobalVariable.G_inputTabName=="ProgramsTab"){
							GlobalVariable.G_QueryProgramsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of files tab query from switch case : "+GlobalVariable.G_QueryProgramsTab)
						}else if(GlobalVariable.G_inputTabName=="ParticipantsTab"){
							GlobalVariable.G_QueryParticipantsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Participants tab query from switch case : "+GlobalVariable.G_QueryParticipantsTab)
						}else if(GlobalVariable.G_inputTabName=="StudyFilesTab"){
							GlobalVariable.G_QueryStudyFilesTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Study Files tab query from switch case : "+GlobalVariable.G_QueryStudyFilesTab)
						}else if(GlobalVariable.G_inputTabName=="SubjectsTab"){
							GlobalVariable.G_GQuerySubjectsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Study Files tab query from switch case : "+GlobalVariable.G_GQuerySubjectsTab)
						}else if(GlobalVariable.G_inputTabName=="GrantsTab"){
							GlobalVariable.G_QueryGrantsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Projects tab query from switch case : "+GlobalVariable.G_QueryGrantsTab)
						}else if(GlobalVariable.G_inputTabName=="PublicationsTab"){
							GlobalVariable.G_QueryPublicationsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Publications tab query from switch case : "+GlobalVariable.G_QueryPublicationsTab)
						}else if(GlobalVariable.G_inputTabName=="DatasetsTab"){
							System.out.println("This is the value of Datasets tab query from switch case : "+GlobalVariable.G_QueryDatasetsTab)
						}else if(GlobalVariable.G_inputTabName=="ClinicalTrialsTab"){
							GlobalVariable.G_QueryClinTrialsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Clinical Trials tab query from switch case : "+GlobalVariable.G_QueryClinTrialsTab)
						}else if(GlobalVariable.G_inputTabName=="DiagnosisTab"){
							GlobalVariable.G_QueryDiagnosisTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Diagnosis tab query from switch case : "+GlobalVariable.G_QueryDiagnosisTab)
						}else if(GlobalVariable.G_inputTabName=="StudiesTab"){
							GlobalVariable.G_QueryStudiesTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Studies tab query from switch case : "+GlobalVariable.G_QueryStudiesTab)
						}else if(GlobalVariable.G_inputTabName=="PatentsTab"){
							GlobalVariable.G_QueryPatentsTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Patents tab query from switch case : "+GlobalVariable.G_QueryPatentsTab)
						}else if(GlobalVariable.G_inputTabName=="SurvivalTab"){
							GlobalVariable.G_QuerySurvivalTab = sheetData.get(i).get(j).getStringCellValue()
							System.out.println("This is the value of Survival tab query from switch case : "+GlobalVariable.G_QuerySurvivalTab)
						}
						break;

					case ("StatQuery"):  //query for stat bar only
						GlobalVariable.G_StatQuery= sheetData.get(i).get(j).getStringCellValue()
						break;
					case ("cartQuery"):  //query for My cart table only
						GlobalVariable.G_cartQuery= sheetData.get(i).get(j).getStringCellValue()
						System.out.println("This is the value of cart query from switch case : "+GlobalVariable.G_cartQuery)
						break;
					case("WebExcel"):
						GlobalVariable.G_WebExcel = sheetData.get(i).get(j).getStringCellValue()
						GlobalVariable.G_OutputFileName = GlobalVariable.G_WebExcel
						System.out.println("This is the value of gwebexcel before appending with directory :"+GlobalVariable.G_WebExcel)
						System.out.println("This is the value of output filename stored in a global var :"+GlobalVariable.G_OutputFileName)

						Path outputDir = Paths.get(System.getProperty("user.dir"), "OutputFiles")
						GlobalVariable.G_OutputDir =outputDir.toString()
						System.out.println("This is the path till the output directory : "+GlobalVariable.G_OutputDir)

						Path filepath = Paths.get(System.getProperty("user.dir"), "OutputFiles", GlobalVariable.G_WebExcel)
						GlobalVariable.G_WebExcel=filepath.toString()
						System.out.println("This is the full path stored in global variable gwebexcel: "+GlobalVariable.G_WebExcel)
						break;
					case("dbExcel"):
						GlobalVariable.G_dbexcel = sheetData.get(i).get(j).getStringCellValue()
						Path dbfilepath = Paths.get(System.getProperty("user.dir"), "OutputFiles", GlobalVariable.G_dbexcel)
						GlobalVariable.G_ResultPath=dbfilepath.toString()
						break;
					case("TsvExcel"):
						GlobalVariable.G_dbexcel = sheetData.get(i).get(j).getStringCellValue()
						Path dbfilepath = Paths.get(System.getProperty("user.dir"), "OutputFiles", GlobalVariable.G_dbexcel)
						GlobalVariable.G_ResultPath=dbfilepath.toString()
						break;
					default :
						System.out.println("Error in initializing")
						break;
				}// Switch case ends here
				str =str+ cell.getStringCellValue() + "||"
			}//for loop j ends (column read)
		}//for loop i ends (row read)
	} //excelparsingKatalon function ends here


	/**for case detail level automation
	 * @return
	 */
	@Keyword
	public static String getPageSwitch() {
		System.out.println("Inside pageswitch function")
		String switchStr;
		String pgUrl = driver.getCurrentUrl()

		//if(((driver.getCurrentUrl()).contains("ccdi"))) {
		if(appKey.equals("CCDI")) {
			System.out.println("This is CCDI. the url does not contain #")
			switchStr = "/explore"
		}else if(appKey.equals("C3DC")){
			System.out.println("This is C3DC. the url does not contain #")
			switchStr = "/explore"
		}else {
			String[] arrOfStr = pgUrl.split("#", 2);
			System.out.println ("This is the value of the array of strings after splitting url : "+arrOfStr)
			switchStr=getSwitchStr(arrOfStr[1])
		}

		return switchStr
	}

	/**
	 * This function returns a string for a particular application"s page for further processing
	 * and is called above in getPageSwitch function
	 * @param mainStr
	 * @return
	 */
	public static String getSwitchStr(String mainStr) {
		String retnSwStr
		if (mainStr.contains("/cases")){
			retnSwStr = "/cases"
		}else if(mainStr.contains("/case/")){
			retnSwStr = "/case/"
		}else if(mainStr.contains("/study/")){
			retnSwStr = "/study/"
		}else if(mainStr.contains("/explore")){
			retnSwStr = "/explore"
		}else if(mainStr.contains("/subjects")){
			retnSwStr = "/subjects"
		}else if(mainStr.contains("/data")){
			retnSwStr = "/data"
		}else if(mainStr.contains("/fileCentricCart")){
			retnSwStr = "/fileCentricCart"
		}else if(mainStr.contains("/projects")){
			retnSwStr = "/projects"
		}else if(mainStr.contains("/studies")){
			retnSwStr = "/studies"
		}else if(mainStr.contains("/programs")){
			retnSwStr = "/programs"
		}
		System.out.println("This is the value returned for switch case: "+retnSwStr)
		return retnSwStr
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
	@Keyword
	public static void multiFunction(String appName, String statVal, String tbl, String tblHdr, String nxtBtn, String webdataSheetName, String dbdataSheetName, String tabQuery) throws IOException {

		System.out.println("This is the value of stat (string) obtained from multifunction: " + statVal);

		int statValue = convStringtoInt(statVal);

		System.out.println("This is the value of stat (integer) obtained from multifunction: " + statValue);

		if (statValue !=0) {
			ReadCasesTableKatalon(statVal, tbl,tblHdr, nxtBtn, webdataSheetName)

			//ReadExcel.Neo4j(dbdataSheetName,tabQuery)
			if(appKey.equals("CDS")) {

				if(dbdataSheetName.equals("TsvDataParticipants")){
					PythonReader.readFile('ParticipantsTab.py')
				}else if(dbdataSheetName.equals("TsvDataSamples")){
					PythonReader.readFile('SamplesTab.py')
				}else if(dbdataSheetName.equals("TsvDataFiles")){
					PythonReader.readFile('FilesTab.py')
				}else {
					System.out.println("Invalid TSV Sheet name: " + dbdataSheetName)
				}
				PythonReader.readFile('Statbar.py')
			}else if(appKey.equals("ICDC")) {
				if(dbdataSheetName.equals("TsvDataCases")){
					PythonReader.readFile('CasesTab.py')
				}else if(dbdataSheetName.equals("TsvDataSamples")){
					PythonReader.readFile('SamplesTab.py')
				}else if(dbdataSheetName.equals("TsvDataCaseFiles")){
					PythonReader.readFile('CaseFilesTab.py')
				}else if(dbdataSheetName.equals("TsvDataStudyFiles")){
					PythonReader.readFile('StudyFilesTab.py')
				}else {
					System.out.println("Invalid TSV Sheet name: " + dbdataSheetName)
				}
				PythonReader.readFile('Statbar.py')
			}else if(appKey.equals("CCDI")) {
				if(dbdataSheetName.equals("TsvDataParticipants")){
					PythonReader.readFile('ParticipantsTab.py')
				}else if(dbdataSheetName.equals("TsvDataDiagnosis")){
					PythonReader.readFile('DiagnosisTab.py')
				}else if(dbdataSheetName.equals("TsvDataStudies")){
					PythonReader.readFile('StudiesTab.py')
				}else if(dbdataSheetName.equals("TsvDataSamples")){
					PythonReader.readFile('SamplesTab.py')
				}else if(dbdataSheetName.equals("TsvDataFiles")){
					PythonReader.readFile('FilesTab.py')
				}
				PythonReader.readFile('Statbar.py')
			}else if(appKey.equals("C3DC")) {
				if(dbdataSheetName.equals("TsvDataStudies")){
					PythonReader.readFile('StudiesTab.py')
				}else if(dbdataSheetName.equals("TsvDataParticipants")){
					PythonReader.readFile('ParticipantsTab.py')
				}else if(dbdataSheetName.equals("TsvDataDiagnosis")){
					PythonReader.readFile('DiagnosisTab.py')
				}else if(dbdataSheetName.equals("TsvDataTreatment")){
					PythonReader.readFile('TreatmentTab.py')
				}else if(dbdataSheetName.equals("TsvDataTreatmentResp")){
					PythonReader.readFile('TreatmentRespTab.py')
				}else if(dbdataSheetName.equals("TsvDataSurvival")){
					PythonReader.readFile('SurvivalTab.py')
				}
				PythonReader.readFile('Statbar.py')
			}else {
				KeywordUtil.markFailed("Invalid App Key: Check multiFunction method")
			}

			compareSheets(webdataSheetName, dbdataSheetName)

			System.out.println("Control is before validate stat bar function from multifunction")
			validateStatBar(appName)
		}else {
			System.out.println("Skipping data collection from neo4j and compare lists of web and db as the stat value is 0")
		}
	}


	/**
	 * This function reads the ui table in MyCart ICDC
	 * @param appName1
	 * @param totalRecCountMyCart1
	 * @param tblMyCart1
	 * @param hdrMyCart1
	 * @param nxtbMyCart1
	 * @param myCartWebSheetName1
	 * @param myCartdbSheetName1
	 * @param cartQuery1
	 * @throws IOException
	 */
	public static void readMyCartTable(String appName1, String totalRecCountMyCart1, String tblMyCart1, String hdrMyCart1, String nxtbMyCart1, String myCartWebSheetName1, String myCartdbSheetName1, String cartQuery1) throws IOException {
		System.out.println("This is the value of my cart db query : "+ cartQuery1)
		System.out.println("This is the value of cart count  : "+ totalRecCountMyCart1)
		System.out.println("This is the value of my cart db query stored in global variable : "+ GlobalVariable.G_cartQuery)
		System.out.println("This is the value of cart count stored in global variable : "+ GlobalVariable.G_myCartTotal)
		ReadCasesTableKatalon(totalRecCountMyCart1, tblMyCart1, hdrMyCart1, nxtbMyCart1, myCartWebSheetName1)
		System.out.println("Control is before readexcel neo4j function")
		ReadExcel.Neo4j(myCartdbSheetName1,cartQuery1)
		System.out.println("Control is before compare lists function in readcarttable")
		compareSheets(myCartWebSheetName1, myCartdbSheetName1)
	}

	/**
	 * Gayathri will updata the details
	 * @param sTblbdy1
	 * @param sTblHdr1
	 * @param webSheetName
	 */
	@Keyword
	public static void readSelectedCols(String sTblbdy1, String sTblHdr1, String sNxtBtn, String webSheetName) {
		List<String> sTableHdrData = new ArrayList<String>();
		List<String> sTableBodyData = new ArrayList<String>();

		String tableHdr= givexpath(sTblHdr1)
		String tableBdy= givexpath(sTblbdy1)
		String nextButton = givexpath(sNxtBtn)
		GlobalVariable.G_customTblHdr=tableHdr
		GlobalVariable.G_customTblBdy=tableBdy
		System.out.println("This is the value of custom table header fm global var : "+GlobalVariable.G_customTblHdr)
		System.out.println("This is the value of custom table body fm global var : "+GlobalVariable.G_customTblBdy)

		driver.manage().window().maximize();
		scrolltoViewjs(driver.findElement(By.xpath(tableHdr)))
		System.out.println("Scrolled into view of custom table header")

		WebElement wbTableHdr = driver.findElement(By.xpath(tableHdr))
		List<WebElement> col_Headers = wbTableHdr.findElements(By.tagName("th"));
		WebElement wbTableBdy = driver.findElement(By.xpath(tableBdy))
		List<WebElement> rows_table;

		System.out.println("This is the value stored in column header list: "+col_Headers)
		int columns_count=col_Headers.size()-1;
		System.out.println("This is the num of cols in the table: "+columns_count);

		rows_table = wbTableBdy.findElements(By.tagName("tr"))
		System.out.println("This is the value of list containing weblements of rows from the table :"+rows_table);
		int rows_count = rows_table.size()
		System.out.println("This is the num of rows in the table in the current page: "+rows_count);

		//*******************************CUSTOM COLUMN HEADER DATA COLLECTION****************************************************
		String hdrdata = ""
		for(int c=1;c<=columns_count;c++){
			if((col_Headers.get(c).getAttribute("innerText"))!="Access"){
				//if ( ((col_Headers.get(c).getAttribute("innerText")) == 'File Name')||((col_Headers.get(c).getAttribute("innerText")) == 'Study Code')||((col_Headers.get(c).getAttribute("innerText")) == 'Case ID') ) {
				hdrdata = hdrdata + (col_Headers.get(c).getAttribute("innerText")) + "||"
				System.out.println("column "+ (c) +"added from header")
			}
		}

		sTableHdrData.add(hdrdata);
		System.out.println("Number of columns in the current result tab is: "+ columns_count)
		System.out.println("Complete list of column headers in current tab: "+ sTableHdrData)

		for(int index = 0; index < sTableHdrData.size(); index++) {
			System.out.println("Header data of the table is :" + sTableHdrData.get(index))
		}
		//*********************************CUSTOM ROW  DATA COLLECTION FOR THE CHOSEN HEADERS******************************************
		int counter = 1;
		scrolltoViewjs(driver.findElement(By.xpath(GlobalVariable.G_customTblBdy)));
		// add code to check exception - if the value of rows_count=1, ie if the table has only header and no data, skip collecting the webdata.

		int i;

		for(i = 1; i <= rows_count; i++) {
			//loop through each row in current page
			String data = ""
			System.out.println("Inside filecentric cart case of ICDC - for 10 cols after excluding Access and Remove"+ "--  row number: "+i);
			for(int j=2;j<=columns_count+1;j++){

				//*[@id='table_selected_files']//thead/tr/th[2]
				String colNameChk = ((driver.findElement(By.xpath(tableHdr +"/tr/th" + "[" + j + "]")).getAttribute("innerText")))
				//System.out.println ("Column header name before if condition : "+colNameChk)
				//	if((colNameChk!="Access"){
				//if((colNameChk == 'File Name')||(colNameChk == 'Study Code')||(colNameChk == 'Case ID')) {

				System.out.println("Value of i is: "+i)  //this tells the row index
				System.out.println("Value of j is: "+j) //this tells the column index
				//*[@id='table_selected_files']//tbody/tr[1]/td[2]/div[2]   last div index is always 2
				data = data + ((driver.findElement(By.xpath(tableBdy +"/tr" + "[" + i + "]/td[" + j + "]/div[2]")).getAttribute("innerText")) +"||")
				System.out.println("This is the value of data : "+data+" from column name : "+colNameChk)
				//} //if loop
			} //inner for loop

			sTableBodyData.add(data)
			//System.out.println("Size of table body list in current result tab is: "+sTableBodyData.size())
			for(int index = 0; index < sTableBodyData.size(); index++) {
				System.out.println("Table body data from current page is: " + sTableBodyData.get(index))
			}

			System.out.println("============================ Verification of the data: =========================")
			GlobalVariable.G_CaseData= sTableHdrData + sTableBodyData;   //GlobalVariable.G_CustomTblData
			System.out.println("This is the contents of globalvar G_casedata : " +GlobalVariable.G_CaseData)

			//********************* CLICKING THE NEXT BUTTON IN RESULTS FOR NEXT PAGE *******************************
			// add a counter for 10 inside this for limitting 100 records

			scrolltoViewjs(nextButton)   //added to address the unable to scroll into view issue/ another element obscures next button issue
			System.out.println("past the scrollintoview block")

			if (nextButton.contains("disabled")){
				break;
			} else {
				System.out.println("COLLECTED DATA FROM PAGE - " +counter);
				clickElement(nextButton); //uses jsexecutor to click
				counter++;
			}
		} //outer for loop



		writeToExcel(webSheetName);
		System.out.println("Custom webdata written to excel successfully")
	}// readSelectedCols function ends


	@Keyword
	public static void verifyCDSFacetExpansion (String CDSFacet) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String facetxpath = givexpath(CDSFacet)
		System.out.println("This is the value of xpath of the element: "+facetxpath);
		WebElement cdsfacet = driver.findElement(By.xpath(facetxpath));
		// Get the value of the aria-expanded attribute
		js.executeScript("arguments[0].scrollIntoView(true);", cdsfacet);
		String ariaExpandedValue = cdsfacet.getAttribute("aria-expanded")
		System.out.println ("This is the value of the aria expanded attribute of the facet : "+ariaExpandedValue);
		// Check if aria-expanded is "false"
		if (ariaExpandedValue != null && ariaExpandedValue.equalsIgnoreCase("false")) {
			// Click the element
			js.executeScript("arguments[0].click();", cdsfacet);
			println("Clicked on the facet as it was not expanded previously.")
		} else {
			println("aria-expanded is true for the facet. No action needed. Facet is already expanded")
		}
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
	@Keyword
	public static void ReadCasesTableKatalon(String statVal1, String tbl1, String hdr1, String nxtb1, String webSheetName) throws IOException {
		String switchCanine
		String switchTrials
		String switchBento
		String switchGMB
		String switchCDS
		String switchCCDI
		String switchC3DC
		String switchINS
		String switchString
		WebElement nextButton
		WebElement nxtBtn
		WebElement resultTab
		String trim

		WebDriverWait wait = new WebDriverWait(driver,30);
		System.out.println("This is the stat value of cases/total (in case of cart) before converting to int: "+statVal1)
		int statValue = convStringtoInt(statVal1);
		System.out.println("This is the passed value of stat for this run: "+statValue)


		//List<String> webData = new ArrayList<String>();  //this is not used
		List<String> wTableHdrData = new ArrayList<String>(); //to capture the table header data
		List<String> wTableBodyData = new ArrayList<String>(); //to capture the table body data
		String tbl_bdy;
		String tbl_main= givexpath(tbl1)
		System.out.println("This is the value of tbl main : "+tbl_main)

		tbl_bdy= tbl_main+"//tbody"
		GlobalVariable.G_cannine_caseTblBdy=tbl_bdy  //correct his variables name typo and also rename it to G_commons_casetblbdy
		System.out.println("This is the value of table body: "+GlobalVariable.G_cannine_caseTblBdy)

		//	driver.manage().window().maximize()  commenting to check the error in INS
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tbl_bdy)));
		scrolltoViewjs(driver.findElement(By.xpath(tbl_bdy)))
		System.out.println("Scrolled into view and ready to click again")
		clickElement(driver.findElement(By.xpath(tbl_bdy)));
		System.out.println("using jscriptexec, clicked again")

		WebElement TableBdy =driver.findElement(By.xpath(GlobalVariable.G_cannine_caseTblBdy))
		List<WebElement> rows_table = TableBdy.findElements(By.tagName("tr"))
		System.out.println("This is the value of weblement rows table: "+rows_table);

		int rows_count = rows_table.size()
		System.out.println("This is the size of the rows in the results table in first page: "+(rows_count))
		String nxt_str=     givexpath(nxtb1)
		System.out.println("This is the value of the xpath of nextbtn : "+nxt_str)
		nextButton = driver.findElement(By.xpath(nxt_str));
		System.out.println("This is the value of the webelem next button from readcasestablekatalon method : "+nextButton)
		System.out.println("This is the value of the hdr object: "+hdr1)
		String hdr_str= givexpath(hdr1)
		System.out.println("This is the value of the hdr string - xpath : "+hdr_str)
		WebElement tableHdr = driver.findElement(By.xpath(hdr_str))

		List<WebElement> colHeader = tableHdr.findElements(By.tagName("th"));

		int columns_count
		String hdrdata = ""

		String crntUrl=driver.getCurrentUrl();

		//Read ICDC table header from result table for a specific tab
		if(appKey.equals("ICDC") && ((driver.getCurrentUrl()).contains("/case/"))){
			switchCanine = getPageSwitch();
			switchString = "Canine";
			System.out.println ("This is the value of CANINE switch string returned by getcurrentpage function: "+switchCanine)
			nxtBtn =  driver.findElement(By.xpath(givexpath('Object Repository/Canine/Canine_File_NextBtn'))); //remove these references of nxtbtn from all 4 ifs
			columns_count = (colHeader.size())
			for(int c=0;c<columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}
		}else if (appKey.equals("ICDC")){
			switchCanine = getPageSwitch();
			switchString = "Canine";
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("This is the value of next button from canine cases switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No records in the table as stat value is 0")
			}else{
				columns_count = (colHeader.size())-1
				for(int c=1;c<=columns_count;c++){
					if((colHeader.get(c).getAttribute("innerText"))!="Access"){
						//if column header = 'Access' ignore adding it to the hdrdata string
						System.out.println ("This is the value of col header index : "+c)
						hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
					}
				}
			}
		}else if (appKey.equals("ICDC") && ((driver.getCurrentUrl()).contains("/study"))){
			switchCanine = getPageSwitch();
			switchString = "Canine";
			System.out.println ("This is the value of CANINE switch string: " + switchCanine)
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("This is the value of next button from canine cases switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No records in the table as stat value is 0")
			}else{
				columns_count = (colHeader.size())-1
				for(int c=1;c<=columns_count;c++){
					if((colHeader.get(c).getAttribute("innerText"))!="Access"){
						//if column header = 'Access' ignore adding it to the hdrdata string
						System.out.println ("This is the value of col header index : "+c)
						hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
					}
				}
			}
		}
		else if (appKey.equals("ICDC") && ((driver.getCurrentUrl()).contains("/fileCentricCart"))){
			switchCanine = getPageSwitch();
			switchString = "Canine";
			System.out.println ("This is the value of CANINE switch string: " + switchCanine)
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("This is the value of next button from canine mycart switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No files in the cart")
			}else{
				columns_count = (colHeader.size())-1
				for(int c=1;c<=columns_count;c++){
					hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
				}
			}
			//******** INS function starts here ********
		}else if (appKey.equals("INS") && ((driver.getCurrentUrl()).contains("/explore"))){
			switchINS = getPageSwitch();
			switchString = "INS";
			System.out.println ("This is the value of INS switch string: " + switchINS)
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("This is the value of next button from INS projects switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No records in the table as stat value is 0")
			}else{
				columns_count = colHeader.size()
				for(int c=0;c<columns_count;c++){
					System.out.println ("This is the value of col header index : "+c)
					hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
				}
			}
			//******** CDS function starts here ********
		}else if(appKey.equals("CDS")){
			switchCDS = getPageSwitch();
			switchString = "CDS";
			columns_count = (colHeader.size())
			columns_count=columns_count-1;
			System.out.println("Inside CDS switch case for header data::  " +columns_count)
			for(int c=1;c<=columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}

			//******** CCDI function starts here ********
		}else if(appKey.equals("CCDI")){
			System.out.println ("Control is about to go to case switch ")
			switchCCDI = getPageSwitch();
			System.out.println ("Control is about to go to the switch for CCDI ")
			switchString = "CCDI";
			System.out.println ("This is the value of CCDI switch string returned by getcurrentpage function: "+switchCCDI)

			columns_count = (colHeader.size())
			columns_count=columns_count-1;
			System.out.println("Inside CCDI switch case for header data::  " +columns_count)
			for(int c=1;c<=columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}

			//******** C3DC function starts below ********
		}else if(appKey.equals("C3DC")){
			switchC3DC = getPageSwitch();
			switchString = "C3DC";
			System.out.println ("This is the value of C3DC switch string: "+switchC3DC)
			columns_count = (colHeader.size())
			columns_count = columns_count;
			System.out.println("Inside C3DC switch case for header data:  " + columns_count)
			for(int c=0;c<columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}

			//******** CTDC function starts below ********
		}else if(appKey.equals("Trials")){
			switchTrials = getPageSwitch();
			switchString = "Trials";
			System.out.println ("Value of TRIALS switch string: "+switchTrials)
			nxtBtn =  driver.findElement(By.xpath(givexpath('Object Repository/Trials/Trials_File_NextBtn')));
			columns_count = (colHeader.size())
			for(int c=0;c<columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}
		}else if(appKey.equals("CTDC")){
			switchTrials = getPageSwitch();
			switchString = "Trials";
			System.out.println ("Value of TRIALS switch string: "+switchTrials)
			nxtBtn =  driver.findElement(By.xpath(givexpath('Object Repository/Trials/Cases_page/Trials_CasesTabNextBtn')));
			columns_count = (colHeader.size())
			System.out.println ("Total number of columns in CTDC result tab in explore page: "+ columns_count)
			for(int c=0;c<columns_count;c++){
				hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
			}
			//********** Bento Function Start here *********
		}else if(appKey.equals("Bento")){
			switchBento = getPageSwitch();
			switchString = "Bento";
			System.out.println ("Value of Bento switch string: " + switchBento)
			columns_count = (colHeader.size())-1
			hdrdata = ""
			for(int c=1;c<=columns_count;c++){
				if((colHeader.get(c).getAttribute("innerText"))!="Access"){
					System.out.println ("This is the value of col header index: "+c)
					hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
					System.out.println ("This is the value of header data from the else condition: "+hdrdata)
				}
			}
		}else if (appKey.equals("Bento") && (crntUrl.contains("/fileCentricCart"))){
			switchBento = getPageSwitch();
			switchString = "Bento";
			System.out.println("Value of BENTO switch string returned by getcurrentpage function: "+switchBento)
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("This is the value of next button from Bento mycart switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No files in the cart")
			}else{
				columns_count = (colHeader.size())-1
				for(int c=0;c<columns_count;c++){
					hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
				}
			}
		}

		else if (appKey.equals("Bento") && (crntUrl.contains("/programs"))){
			switchBento = getPageSwitch();
			switchString = "Bento";
			System.out.println ("Value of BENTO switch string: " + switchBento)
			nxtBtn =  driver.findElement(By.xpath(givexpath(nxtb1)));
			System.out.println("Value of next button from Bento pgm page switch: "+nxtBtn)
			if(statValue==0){
				System.out.println ("No records for pgms")
			}else{
				columns_count = (colHeader.size())
				for(int c=0;c<columns_count;c++){
					hdrdata = hdrdata + (colHeader.get(c).getAttribute("innerText")) + "||"
				}
			}
		}
		// Write header data
		wTableHdrData.add(hdrdata);
		System.out.println("Number of columns in the current result tab is:  "+ columns_count)
		System.out.println("Complete list of column headers in current tab:  "+ wTableHdrData)
		System.out.println("Total number of rows to read:  "+statValue);



		//@@@@@@@@@@@@@@@@@@  COLLECTING THE TABLE BODY DATA @@@@@@@@@@@@@@@
		int counter=1;
		if (statValue !=0) {

			while(counter <= 10) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GlobalVariable.G_cannine_caseTblBdy)));   //the name is misleading but it is only a placeholder for all the applications
				scrolltoViewjs(driver.findElement(By.xpath(GlobalVariable.G_cannine_caseTblBdy)))
				TableBdy =driver.findElement(By.xpath(GlobalVariable.G_cannine_caseTblBdy))
				Thread.sleep(2000) //Check first and then delete
				rows_table = TableBdy.findElements(By.tagName("tr"))
				rows_count = rows_table.size()
				System.out.println("Number  of rows per  page is:  "+(rows_count))

				int i;

				for(i = 1; i <= rows_count; i++) {

					String data = ""

					//@@@@@@@@@@@@@@@@ CDS table data collection starts here  @@@@@@@@@@@@@@@@
					if(switchString == "CDS"){
						switch(switchCDS){
							case("/data"):

								int tblcol=GlobalVariable.G_rowcountFiles

								if((tbl_main).equals('//div[@id="case_tab_table"]')){
									tblcol=tblcol-3;
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[1]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals('//*[@id="sample_tab_table"]')){
									tblcol=tblcol-2;
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[1]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals('//*[@id="file_tab_table"]')){
									tblcol=tblcol-1;
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[1]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}
								break;
							default:
								System.err.println("Check CDS switch statment for this error")
								break;
						}
					}

					//@@@@@@@@@@@@@@@@ C3DC table data collection starts here  @@@@@@@@@@@@@@@@
					if(switchString == "C3DC"){
						switch(switchC3DC){

							case("/explore"):

								int tblcol=GlobalVariable.G_rowcountFiles

								if((tbl_main).equals("//*[@id='study_tab_table']")){
									tblcol=tblcol-6;
									for (int j = 0; j <tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals("//*[@id='participant_tab_table']")){
									tblcol=tblcol-4;
									for (int j = 0; j <tblcol; j =j+1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals("//*[@id='diagnosis_tab_table']")){
									tblcol=tblcol;
									for (int j = 0; j <tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals("//*[@id='treatment_tab_table']")){
									tblcol=tblcol-1;
									for (int j = 0; j <tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals("//*[@id='treatment_response_tab_table']")){
									tblcol=tblcol-1;
									for (int j = 0; j <tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}else if((tbl_main).equals("//*[@id='survival_tab_table']")){
									tblcol=tblcol-3;
									for (int j = 0; j <tblcol; j = j +1) {
										System.out.println("This is the name of column header:  "+colHeader.get(j).getAttribute("innerText"))
										String value = ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")))
										data = data + value + "||"
										System.out.println("This is the value of  table  cell:  "+value)
									}
								}
								break;
							default:
								System.err.println("Check C3DC switch statment for this error")
								break;
						}
					}

					//@@@@@@@@@@@@@@@@ CCDI table data collection starts here  @@@@@@@@@@@@@@@@  added on 8th Sep 2023
					if(switchString == "CCDI"){
						switch(switchCCDI){
							case("/explore"):
								System.out.println("Inside CCDI switch case for body data")
								int tblcol=GlobalVariable.G_rowcountFiles
								System.out.println ("This is the value of tblcol from CCDI body data :"+tblcol)

								if((tbl_main).equals('//*[@id="participant_tab_table"]')){
									System.out.println("Inside CCDI participants switch")
									tblcol=tblcol-2;    //8-3=5 leaves out alternate id col   change to 8-2
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
										//*[@id="participant_tab_table"]/div[2]/table/tbody/tr[1]/td[3]/p
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/p")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals("//*[@id='diagnosis_tab_table']")){
									System.out.println("Inside CCDI diagnosis switch")
									tblcol=tblcol+3;  //tblcol comes from the top as 8. need to add 3 to get 11 cols
									System.out.println("Value of tblcol from the diagnosis section is: "+tblcol)
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										// only for this Age column the xpath will not have the /p tag
										if(((tbl_main).equals("//*[@id='diagnosis_tab_table']")) && (colHeader.get(j).getAttribute("innerText")=="Age at Diagnosis (days)")) {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										}else {

											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											//*[@id="participant_tab_table"]/div[2]/table/tbody/tr[1]/td[3]/p
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/p")).getAttribute("innerText")) +"||")
										}
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals("//*[@id='study_tab_table']")){
									System.out.println("Inside CCDI studies switch")
									tblcol=tblcol+3;
									System.out.println("Value of tblcol from the studies section is: "+tblcol)
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										// only for two cols with (top 5) the xpath will not have the /p tag

										if(((tbl_main).equals("//*[@id='study_tab_table']")) && (colHeader.get(j).getAttribute("innerText")=="Diagnosis (Top 5)")) {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										}else if(((tbl_main).equals("//*[@id='study_tab_table']")) && (colHeader.get(j).getAttribute("innerText")=="Diagnosis Anatomic Site (Top 5)")) {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										}else if(((tbl_main).equals("//*[@id='study_tab_table']")) && (colHeader.get(j).getAttribute("innerText")=="File Type (Top 5)")) {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										}
										else {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))

											//*[@id="study_tab_table"]/div[2]/table/tbody/tr[5]/td[10]/p
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/p")).getAttribute("innerText")) +"||")
										}
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals("//*[@id='sample_tab_table']")){
									System.out.println("Inside CCDI samples tab switch")
									tblcol=tblcol+4;
									System.out.println("Value of tblcol from the samples section is: "+tblcol)
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										// only for one col the xpath will not have the /p tag
										//*[@id="sample_tab_table"]/div[2]/table/tbody/tr[1]/td[6]
										if(((tbl_main).equals("//*[@id='sample_tab_table']")) && (colHeader.get(j).getAttribute("innerText")=="Age at Sample Collection (days)")) {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										}else {
											System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
											//*[@id="sample_tab_table"]/div[2]/table/tbody/tr[1]/td[2]/p
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/p")).getAttribute("innerText")) +"||")
										}
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals('//*[@id="file_tab_table"]')){
									System.out.println("Inside CCDI files tab switch")
									tblcol=tblcol+2;
									for (int j = 1; j <=tblcol; j = j +1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										//*[@id="file_tab_table"]//tbody/tr[1]/*[2]/*[2]
										System.out.println("This is the name of column header : "+colHeader.get(j).getAttribute("innerText"))
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/p")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data : "+data)
									}
								}
								break;
							default:
								System.err.println("Check CCDI switch statment for this error")
								break;
						}
					}


					// @@@@@@@@@@@@@@@@  Canine table data collection starts here @@@@@@@@@@@@@@@@
					if(switchString == "Canine"){
						System.out.println("Inside Canine Switch Structure")
						switch(switchCanine){

							case("/explore"):
								int tblcol=GlobalVariable.G_rowcount_Katalon;

								if((tbl_main).equals("//*[@id='case_tab_table']")){
									//This is for cases tab
									data = ""
									System.out.println("This is the val of tblcol: "+tblcol+"\nThis is the output of data: "+ data)
									for (int j = 2; j<= tblcol; j = j + 1) {
										System.out.println("Value of i is: "+i+"\nValue of j is: "+j)

										if( ((tbl_main).equals("//*[@id='case_tab_table']")) && (colHeader.get(j-1).getAttribute("innerText")=="Case ID")){
											System.out.println("Inside the dog filter control structure")
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + j +"]/div/a")).getAttribute("innerText").trim()) +"||")
											System.out.println("This is the data after filtering for dog icon :"+data)
										}else {
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + j + "]")).getAttribute("innerText")) +"||")
											System.out.println("This is the value of data : "+data)
										}
									}
								}else if((tbl_main).equals("//*[@id='sample_tab_table']")){
									//This is for samples tab
									data = ""
									System.out.println("This is the val of tblcol: "+tblcol+"\nThis is the output of data: "+ data)
									for (int j = 2; j<= tblcol; j = j + 1) {
										System.out.println("Value of i is: "+i+"\nValue of j is: "+j)
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + j + "]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals("//*[@id='file_tab_table']")){
									//This is for case file tab
									tblcol=tblcol-2  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 1; j<= tblcol; j = j + 1) {
										System.out.println("Value of i is: "+i+"\nValue of j is: "+j)

										if((colHeader.get(j).getAttribute("innerText"))!="Access") {
											System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
											data = data + ( (driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText").trim()) +"||")
											System.out.println("This is the data after filtering for dog icon :"+data)
										}
									}
								}else if((tbl_main).equals("//table")){
									//This is for case file tab
									tblcol=tblcol-5  // this is needed when study files has 8 cols
									System.out.println("This is the count of tblcol when study files tab is selected: "+tblcol)
									for (int j = 1; j<= tblcol; j = j + 1) {
										System.out.println("Value of i is: "+i+"\nValue of j is: "+j)

										if((colHeader.get(j).getAttribute("innerText"))!="Access") {
											System.out.println("This is the name of column header  :"+colHeader.get(j).getAttribute("innerText"))
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"//tr" + "[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")) +"||")
											System.out.println("This is the value of data :"+data)
										}
									}
								}else{
									System.err.println("Invalid Tab! Could not read the tab")
								}
								break;

							case("/fileCentricCart"):
								System.out.println("Inside filecentric cart case of ICDC - for 10 cols after excluding Access and Remove");
							//*[@id='table_selected_files']//tbody/tr[1]/td[2]    td runs from 2 to 11
								int tblcol=GlobalVariable.G_rowcount_Katalon;
								System.out.println("This is the val of tblcol: "+tblcol)
							//i=i-1; // to start from 0 and include the first column
								System.out.println("**************** "+ data)
								data = ""
								for (int j = 2; j<= tblcol-2; j = j + 1) {
									System.out.println("Value of i is: "+i+"\nValue of j is: "+j)
									//*[@id='table_selected_files']//tbody/tr[1]/td[2]/div[2]   where div[2] is a constant
									data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/td[" + j + "]/div[2]")).getAttribute("innerText")) +"||")
									System.out.println("This is the value of data :"+data)
								}
								break;
							default:
								System.out.println("Canine Case did not match")
								break;
						} //canine switch ends here
					}//canine if ends here


					// @@@@@@@@@@@@@@@@  INS table data collection starts here @@@@@@@@@@@@@@@@
					if(switchString == "INS"){
						System.out.println("Inside INS Switch Structure")
						switch(switchINS){
							case("/explore"):
								int tblcol=GlobalVariable.G_rowcount_Katalon; //13
								System.out.println("This is the number of columns from the results table : "+tblcol)
							//In ICDC - Cases Tab and Samples tab have 12 cols; Files tab has 8 cols. Hence the counter has to be changed if the tab id is related to files tab.
								if((tbl_main).equals('//*[@id="project_tab_table"]/div/div[2]/div[3]/table')){
									System.out.println("Inside grants tab")
									tblcol=tblcol-5  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 0; j< tblcol+2; j = j + 1) {
										System.out.println("Value of i is: "+i+"\nValue of j is: "+j)
										System.out.println ("This is the value of col index starting from 0: "+j)

										System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data: "+data)
									}
									//this is for publications tab in INS***************************************
								}else if((tbl_main).equals('//*[@id="publication_tab_table"]/div/div[2]/div[3]/table')){
									System.out.println("Inside publications tab")
									tblcol=tblcol-8  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 0; j< tblcol+2; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)
										System.out.println ("This is the value of col index starting from 0: "+j)
										//*[@id="publication_tab_table"]/div/div[2]/div[3]/table//tbody/tr[1]/*[1]/*[2]  - this is the generic xpath for all columns
										//*[@id="publication_tab_table"]/div/div[2]/div[3]/table//tbody/tr[1]/td[1]/*[2]/div/span/a  - this is the specific xpath for the pubmed id col to avoid the external link image
										System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
										//if( ((tbl_main).equals('//*[@id="case_tab_table"]')) && (colHeader.get(j-1).getAttribute("innerText")=="Case ID")){
										if((colHeader.get(j).getAttribute("innerText")=="PubMed ID")){
											System.out.println("Inside the INS Pubmed ID column which has external link icon div that should be avoided")
											data = data + ( (driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]/div/span/a")).getAttribute("innerText").trim()) +"||")
											System.out.println("This is the data after eliminating the div for pubmed id external icon :"+data)
										}else {
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]")).getAttribute("innerText")) +"||")
											System.out.println("This is the value of data: "+data)
										}
									}
									//this is for datasets tab in INS***************************************
								}else if((tbl_main).equals('//*[@id="dataset_tab_table"]/div/div[2]/div[3]/table')){
									System.out.println("Inside datasets tab")
									tblcol=tblcol-7  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 0; j< tblcol+2; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)
										System.out.println ("This is the value of col index starting from 0: "+j)

										System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data: "+data)
									}
									//this is for clinical trials tab in INS***************************************
								}else if((tbl_main).equals('//*[@id="clinical_trial_tab_table"]/div/div[2]/div[3]/table')){
									System.out.println("Inside clin trials tab")
									tblcol=tblcol-10  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 0; j< tblcol+2; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)
										System.out.println ("This is the value of col index starting from 0: "+j)

										System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data: "+data)
									}
									//this is for patents tab in INS***************************************
								}else if((tbl_main).equals('//*[@id="patent_tab_table"]/div/div[2]/div[3]/table')){
									System.out.println("Inside patents tab")
									tblcol=tblcol-2  // this is needed when files tab has 11 cols
									System.out.println("This is the count of tblcol when files tab is selected: "+tblcol)
									for (int j = 0; j< tblcol+2; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)
										System.out.println ("This is the value of col index starting from 0: "+j)

										System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) +"]/*[2]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data: "+data)
									}
								}else if((statValue)==0){
									System.out.println("inside the if loop for statvalue equal to 0 : already collected the header data")
								}else{
									System.out.println("This is the val of tblcol: "+tblcol)
									System.out.println("This is the output of data **************** "+ data)
									data = ""

									for (int j = 2; j<= tblcol; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + j + "]/*[2]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data :"+data)
									}
								}
								break;
						} // INS switch ends here
					} //INS if ends here
					//@@@@@@@@@@@@@@@@ CTDC table data collection starts here  @@@@@@@@@@@@@@@@

					if(switchString == "Trials"){
						System.out.println("Inside Trials Switch Structure")
						switch(switchTrials){
							case("/case/"):
								System.out.println("Inside trials switch case")
								int tblcol=GlobalVariable.G_rowcountFiles
								System.out.println ("This is the value of tblcol variable: "+tblcol);
								for (int j = 2; j < columns_count+tblcol; j = j + 2) {
									data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + j + "]")).getText()) +"||")
								}
								break;
							case("/explore"):
								int tblcol=GlobalVariable.G_rowcount_Katalon;
								System.out.println("This is the value of the variable tblcol: "+tblcol);
								if((tbl_main).equals('//*[@id="file_tab_table"]')){
									tblcol=tblcol-2
									System.out.println("This is the count of tblcol after adjusting: "+tblcol)
								}
								if((statValue)==0){
									System.out.println("inside the if loop for statvalue equal to 0 : already collected the header data")
								}else{
									System.out.println("This is the count of tblcol inside Trials: "+tblcol) //tblcol =10 for cases tab subtract 2 from it
									System.out.println("This is the value of data before data collection: "+data)
									for (int j = 1; j <= tblcol-2; j = j + 1) {
										System.out.println("Value of i is: "+i)
										System.out.println("Value of j is: "+j)
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/td[" + j + "]/div[2]")).getAttribute("innerText")) +"||")   //*[@id="case_tab_table"]//tbody/tr[1]/td[1]/div[2]/div/a
										System.out.println("This is the value of data: "+data)
									}
								}
								break;
							default:
							//System.out.println("Trials Case did not match")
								break;
						}
					}
					//@@@@@@@@@@@@@@@@ Bento table data collection starts here  @@@@@@@@@@@@@@@
					if(switchString == "Bento"){
						System.out.println("inside Bento switch structure");

						switch(switchBento){

							case("/explore"):
								System.out.println("Inside Bento switch for all cases")
								int tblcol=GlobalVariable.G_rowcount_Katalon;
								data = ""
								System.out.println("Value of columns_count variable : "+columns_count+"\nValue of tblcol variable : "+tblcol)

								if((tbl_main).equals('//*[@id="case_tab_table"]')) {
									for (int j = 1; j <columns_count+1; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals('//*[@id="sample_tab_table"]')){
									for (int j = 1; j <columns_count+1; j = j + 1) {
										System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
										data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")) +"||")
										System.out.println("This is the value of data : "+data)
									}
								}else if((tbl_main).equals('//*[@id="file_tab_table"]')){
									for (int j = 1; j <columns_count+1; j = j + 1) {
										if((colHeader.get(j).getAttribute("innerText"))!="Access") {
											System.out.println("Value of i is: "+ i +"\nValue of j is: " + j)
											data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+1) +"]")).getAttribute("innerText")) +"||")
											System.out.println("This is the value of data : "+data)
										}
									}
								}else {
									KeywordUtil.markFailed("Invalid Tab name! Check Bento table data collection function")
								}

								break;
							case("/fileCentricCart"):
								int tblcol=GlobalVariable.G_rowcount_Katalon;
								for (int j = 1; j < columns_count+tblcol; j = j + 1) {
									System.out.println("Value of i is: "+i+"\nValue of j is: "+j)
									data = data+((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + j + "]")).getAttribute("innerText")) +"||")
									System.out.println("This is the value of data : "+data)
								}
								break;
							case("/programs"):
								System.out.println("Inside Bento switch for all Programs cases")
								int tblcol=GlobalVariable.G_rowcount_Katalon;
								System.out.println("Value of columns_count variable : "+columns_count + "\nValue of tblcol variable : "+tblcol)
								for (int j = 1; j < columns_count+1; j = j + 1) {
									System.out.println("Value of i is: "+ i +"\nValue of j is: "+j)

									//if((colHeader.get(j).getAttribute("innerText"))!="PubMed ID") {
									//System.out.println("This is the name of Pgm table column header  :"+colHeader.get(j).getAttribute("innerText"))
									//System.out.println("This is the value of data before calculating the index for innertext of the td: "+data)

									//data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + (j+1) + "]/*[2]")).getAttribute("innerText")) +"||")
									data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + j + "]")).getAttribute("innerText").trim()) +"||")
									System.out.println("This is the value of data :"+data)
									//}
								}
								break;

							default:
								KeywordUtil.markFailed("Bento Case switch did not match")
								break;
						}
					}

					wTableBodyData.add(data)
				}//for loop ends


				//System.out.println("Size of table body list in current result tab is: "+wTableBodyData.size())
				for(int index = 0; index < wTableBodyData.size(); index++) {
					System.out.println("Table body data from current page is: " + wTableBodyData.get(index))
				}
				GlobalVariable.G_CaseData= wTableHdrData + wTableBodyData;
				//System.out.println("This is the contents of globalvar G_casedata: " +GlobalVariable.G_CaseData)

				//********************* CLICKING THE NEXT BUTTON IN RESULTS FOR NEXT PAGE *******************************
				// add a counter for 10 inside this for limitting 100 records

				scrolltoViewjs(nextButton)   //added to address the unable to scroll into view issue/ another element obscures next button issue

				if (nextButton.getAttribute("class").contains("disabled")){
					break;
				} else {
					System.out.println("COLLECTED DATA FROM PAGE - " +counter);
					clickElement(nextButton); //uses jsexecutor to click
					System.out.println("Clicked next button");
					counter++;
				}
			}//while loop ends
		} //if loop for body data collection ends
		else {
			System.out.println("Not collecting the table data as the stat value is 0")
		}

		writeToExcel(webSheetName);
	}//ReadCasesTableKatalon function ends


	/**
	 * This function reads Bento Statbar
	 * @param bProgs
	 * @param bArms
	 * @param bCases
	 * @param bSamples
	 * @param bAssays
	 * @param bFiles
	 */
	@Keyword
	public void readStatBarBento(String bProgs, String bArms, String bCases, String bSamples, String bAssays, String bFiles) {
		Thread.sleep(8000);

		String xbProgs = givexpath(bProgs)
		String xbArms = givexpath(bArms)
		String xbCases = givexpath(bCases)
		String xbSamples = givexpath(bSamples)
		String xbAssays = givexpath(bAssays)
		String xbFiles = givexpath(bFiles)

		GlobalVariable.G_StatBar_Programs = driver.findElement(By.xpath(xbProgs)).getAttribute('innerHTML');
		System.out.println("This is the value of Programs count from Stat bar :"+GlobalVariable.G_StatBar_Programs)
		GlobalVariable.G_StatBar_Arms = driver.findElement(By.xpath(xbArms)).getAttribute('innerHTML');
		System.out.println("This is the value of Arms count from Stat bar :"+GlobalVariable.G_StatBar_Arms)
		GlobalVariable.G_StatBar_Cases = driver.findElement(By.xpath(xbCases)).getText();
		System.out.println("This is the value of Cases count from Stat bar :"+GlobalVariable.G_StatBar_Cases)
		GlobalVariable.G_StatBar_Samples = driver.findElement(By.xpath(xbSamples)).getText();
		System.out.println("This is the value of Samples count from Stat bar :"+GlobalVariable.G_StatBar_Samples)
		GlobalVariable.G_StatBar_Assays = driver.findElement(By.xpath(xbAssays)).getText();
		System.out.println("This is the value of Assays count from Stat bar :"+GlobalVariable.G_StatBar_Assays)
		GlobalVariable.G_StatBar_Files = driver.findElement(By.xpath(xbFiles)).getText();
		System.out.println("This is the value of Files count from Stat bar :"+GlobalVariable.G_StatBar_Files)
	}

	/**
	 * This function reads the count displayed near the cart icon in ICDC
	 * @param cmyCartCount
	 */
	@Keyword
	public void readMyCartCount(String cmyCartCount) {
		Thread.sleep(5000);
		String xcmyCartCount = givexpath(cmyCartCount)
		Thread.sleep(2000)
		GlobalVariable.G_myCartTotal = driver.findElement(By.xpath(xcmyCartCount)).getAttribute("innerText");
		System.out.println("This is the value of count from cart icon :"+GlobalVariable.G_myCartTotal)
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
	@Keyword
	public void readStatBarCanine(String cProgs, String cStuds, String cCases, String cSamples, String cFiles, String cStudyFiles) {
		Thread.sleep(5000);

		String xcProgs = givexpath(cProgs)
		String xcStuds = givexpath(cStuds)
		String xcCases = givexpath(cCases)
		String xcSamples = givexpath(cSamples)
		String xcFiles = givexpath(cFiles)
		String xcStudyFiles = givexpath(cStudyFiles)

		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Programs = driver.findElement(By.xpath(xcProgs)).getAttribute("innerText");
		System.out.println("This is the value of Programs count from Stat bar: "+GlobalVariable.G_StatBar_Programs)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Studies = driver.findElement(By.xpath(xcStuds)).getAttribute("innerText");
		System.out.println("This is the value of Studies count from Stat bar: "+GlobalVariable.G_StatBar_Studies)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Cases = driver.findElement(By.xpath(xcCases)).getAttribute("innerText");
		System.out.println("This is the value of Cases count from Stat bar: "+GlobalVariable.G_StatBar_Cases)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Samples = driver.findElement(By.xpath(xcSamples)).getAttribute("innerText");
		System.out.println("This is the value of Samples count from Stat bar: "+GlobalVariable.G_StatBar_Samples)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Files = driver.findElement(By.xpath(xcFiles)).getAttribute("innerText");
		System.out.println("This is the value of Case Files count from Stat bar: "+GlobalVariable.G_StatBar_Files)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_StudyFiles = driver.findElement(By.xpath(xcStudyFiles)).getAttribute("innerText");
		System.out.println("This is the value of Study Files count from Stat bar: "+GlobalVariable.G_StatBar_StudyFiles)
	}

	/**
	 * This function reads CCDI Hub Statbar
	 * @param cStuds
	 * @param cParticip
	 * @param cSamples
	 * @param cFiles
	 //@param cDiag  - this will be used later when diag is available in stat bar
	 */
	@Keyword
	public void readStatBarCCDIhub (String cStuds, String cParticip, String cSamples, String cFiles) {
		Thread.sleep(5000);

		String xcStuds = givexpath(cStuds)
		String xcParticip = givexpath(cParticip)
		String xcSamples = givexpath(cSamples)
		String xcFiles = givexpath(cFiles)

		System.out.println ("Inside read stat bar function for CCDI");

		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Studies = driver.findElement(By.xpath(xcStuds)).getAttribute("innerText");
		System.out.println("This is the value of Studies count from Stat bar: "+GlobalVariable.G_StatBar_Studies)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Participants = driver.findElement(By.xpath(xcParticip)).getAttribute("innerText");
		System.out.println("This is the value of Participants count from Stat bar: "+GlobalVariable.G_StatBar_Participants)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Samples = driver.findElement(By.xpath(xcSamples)).getAttribute("innerText");
		System.out.println("This is the value of Samples count from Stat bar: "+GlobalVariable.G_StatBar_Samples)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Files = driver.findElement(By.xpath(xcFiles)).getAttribute("innerText");
		System.out.println("This is the value of Case Files count from Stat bar: "+GlobalVariable.G_StatBar_Files)
		Thread.sleep(2000)
	}


	/**
	 * This function reads C3DC Hub Statbar
	 * @param cDiag
	 * @param cParticip
	 * @param cStudies
	 //@param cDiag  - this will be used later when diag is available in stat bar
	 */
	@Keyword
	public void readStatBarC3DC(String cDiag, String cParticip, String cStudies) {
		Thread.sleep(5000);

		String xcDiag = givexpath(cDiag)
		String xcParticip = givexpath(cParticip)
		String xcSamples = givexpath(cStudies)

		System.out.println ("Inside read stat bar function for C3DC");

		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Diagnosis = driver.findElement(By.xpath(xcDiag)).getAttribute("innerText");
		System.out.println("This is the value of Diagnosis count from Stat bar: "+GlobalVariable.G_StatBar_Diagnosis)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Participants = driver.findElement(By.xpath(xcParticip)).getAttribute("innerText");
		System.out.println("This is the value of Participants count from Stat bar: "+GlobalVariable.G_StatBar_Participants)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Studies = driver.findElement(By.xpath(xcSamples)).getAttribute("innerText");
		System.out.println("This is the value of Studies count from Stat bar: "+GlobalVariable.G_StatBar_Studies)
		Thread.sleep(2000)
	}


	/**
	 * This function reads CTDC Statbar
	 * @param tTrials
	 * @param tCases
	 * @param tFiles
	 */
	@Keyword
	public void readTrialsStatBar(String tTrials, String tCases, String tFiles){
		String xpTrials = givexpath(tTrials)
		String xpCases = givexpath(tCases)
		String xpFiles = givexpath(tFiles)
		GlobalVariable.G_TStatBar_Trials = driver.findElement(By.xpath(xpTrials)).getText();
		System.out.println("This is the value of Trials count from Stat bar :"+GlobalVariable.G_TStatBar_Trials)
		GlobalVariable.G_TStatBar_Cases = driver.findElement(By.xpath(xpCases)).getText();
		System.out.println("This is the value of Cases count from Stat bar :"+GlobalVariable.G_TStatBar_Cases)
		GlobalVariable.G_TStatBar_Files = driver.findElement(By.xpath(xpFiles)).getText();
		System.out.println("This is the value of Files count from Stat bar :"+GlobalVariable.G_TStatBar_Files)
	}

	@Keyword
	public void readINSStatBar(String tProgs, String tProjs,String tGrants, String tPubs, String tDsets, String tClinTrials, String tPatents){
		String xpProgs = givexpath(tProgs)
		String xpProjs = givexpath(tProjs)
		String xpGrants = givexpath(tGrants)
		String xpPubs = givexpath(tPubs)
		String xpDsets = givexpath(tDsets)
		String xpClinTrials = givexpath(tClinTrials)
		String xpPatents = givexpath(tPatents)
		GlobalVariable.G_StatBar_Programs = driver.findElement(By.xpath(xpProgs)).getText();
		System.out.println("This is the value of Programs count from Stat bar :"+GlobalVariable.G_StatBar_Programs)
		GlobalVariable.G_StatBar_Projects = driver.findElement(By.xpath(xpProjs)).getText();
		System.out.println("This is the value of Projects count from Stat bar :"+GlobalVariable.G_StatBar_Projects)
		GlobalVariable.G_StatBar_Grants = driver.findElement(By.xpath(xpGrants)).getText();
		System.out.println("This is the value of Grants count from Stat bar :"+GlobalVariable.G_StatBar_Grants)
		GlobalVariable.G_StatBar_Publications = driver.findElement(By.xpath(xpPubs)).getText();
		System.out.println("This is the value of Publications count from Stat bar :"+GlobalVariable.G_StatBar_Publications)
		GlobalVariable.G_StatBar_Datasets = driver.findElement(By.xpath(xpDsets)).getText();
		System.out.println("This is the value of Datasets count from Stat bar :"+GlobalVariable.G_StatBar_Datasets)
		GlobalVariable.G_StatBar_ClinTrials = driver.findElement(By.xpath(xpClinTrials)).getText();
		System.out.println("This is the value of Clinical Trials count from Stat bar :"+GlobalVariable.G_StatBar_ClinTrials)
		GlobalVariable.G_StatBar_Patents = driver.findElement(By.xpath(xpPatents)).getText();
		System.out.println("This is the value of Patents count from Stat bar :"+GlobalVariable.G_StatBar_Patents)
	}


	/**
	 * This function reads GMB Statbar
	 * @param gTrials
	 * @param gSubjects
	 * @param gFiles
	 */
	@Keyword
	public void readGMBStatBar(String gTrials, String gSubjects, String gFiles){
		String gmbTrials = givexpath(gTrials)
		String gmbSubjects = givexpath(gSubjects)
		String gmbFiles = givexpath(gFiles)

		Thread.sleep(5000)
		GlobalVariable.G_GStatBar_Trials = driver.findElement(By.xpath(gmbTrials)).getAttribute("innerText")
		System.out.println("This is the value of Trials count from Stat bar: "+GlobalVariable.G_GStatBar_Trials)
		Thread.sleep(2000)
		GlobalVariable.G_GStatBar_Subjects = driver.findElement(By.xpath(gmbSubjects)).getAttribute("innerText")
		System.out.println("This is the value of Subjects count from Stat bar: "+GlobalVariable.G_GStatBar_Subjects)
		Thread.sleep(2000)
		GlobalVariable.G_GStatBar_Files = driver.findElement(By.xpath(gmbFiles)).getAttribute("innerText")
		System.out.println("This is the value of Case Files count from Stat bar: "+GlobalVariable.G_GStatBar_Files)
	}


	/**
	 * This function reads CDS Statbar
	 * @param cdsStuds
	 * @param cdsDisesSite
	 * @param cdsParticipants
	 * @param cdsSamples
	 * @param cdsFiles
	 */
	@Keyword
	public void readStatBarCDS(String cdsStuds, String cdsParticipants, String cdsSamples, String cdsFiles) {
		Thread.sleep(5000);

		String cStuds = givexpath(cdsStuds)
		//String cdisSite = givexpath(cdsDisesSite)
		String cParticipants = givexpath(cdsParticipants)
		String cSamples = givexpath(cdsSamples)
		String cFiles = givexpath(cdsFiles)

		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Studies = driver.findElement(By.xpath(cStuds)).getAttribute('innerHTML');
		System.out.println("This is the value of Studies count from Stat bar:  "+GlobalVariable.G_StatBar_Studies)
		Thread.sleep(2000)
		//GlobalVariable.G_StatBar_DisSite = driver.findElement(By.xpath(cdisSite)).getText();
		//System.out.println("This is the value of Disease Sites count from Stat bar: "+GlobalVariable.G_StatBar_DisSite)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Participants = driver.findElement(By.xpath(cParticipants)).getAttribute('innerHTML');
		System.out.println("This is the value of Particp count from Stat bar:  "+GlobalVariable.G_StatBar_Participants)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Samples = driver.findElement(By.xpath(cSamples)).getAttribute('innerHTML');
		System.out.println("This is the value of Samples count from Stat bar:  "+GlobalVariable.G_StatBar_Samples)
		Thread.sleep(2000)
		GlobalVariable.G_StatBar_Files = driver.findElement(By.xpath(cFiles)).getAttribute('innerHTML');
		System.out.println("This is the value  of Files  count from Stat bar:  "+GlobalVariable.G_StatBar_Files)
	}


	/**
	 * This function returns the xpath of a given string (from the obj stored in katalons object repository)
	 * @param objname
	 * @return xpath of the object
	 */
	@Keyword
	public static String givexpath(String objname) {
		TestObject obj = findTestObject(objname)
		String xpathOfObj = obj.findPropertyValue('xpath')
		System.out.println(xpathOfObj)
		return xpathOfObj;
	}


	//@@@@@@@@@@@@@@@@ Write web result to excel @@@@@@@@@@@@@@@@
	/**
	 * This function write webData to excel
	 * @param webSheetName
	 */
	public static void writeToExcel(String webSheetName){
		//add a tabname
		try {
			String excelPath = GlobalVariable.G_WebExcel;
			File file1 = new File(excelPath);
			FileOutputStream fos = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet;

			if( file1.exists()){
				System.out.println( "File exists, creating a new worksheet in the same file.")
				FileInputStream fileinp = new FileInputStream(excelPath);
				workbook = new XSSFWorkbook(fileinp);
				sheet = workbook.createSheet(webSheetName);
				fos = new FileOutputStream(excelPath);
			}
			else{
				fos = new FileOutputStream(new File(excelPath));
				System.out.println("File does not exist, creating a new file.")
				workbook = new XSSFWorkbook();           // Create Workbook instance holding .xls file
				sheet = workbook.createSheet(webSheetName);
			}

			List<String> writeData = new ArrayList<String>();
			writeData = GlobalVariable.G_CaseData
			for( int i = 0; i < writeData.size(); i++ ){
				Row row = sheet.createRow(i);
				int cellNo = 0
				ArrayList<String> cellData = writeData.get(i).split("\\|\\|");
				for( String cellD: cellData ){
					//System.out.println("Cell data is: " + cellD )
					Cell cell = row.createCell(cellNo++);
					cell.setCellValue(cellD);
				}
			}//for loop of in
			workbook.write(fos);  //Write workbook into the excel
			fos.close(); //Close the workbook
			System.out.println("Web Data has been written to excel successfully");
			workbook.close();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}//write to excel method ends here


	/**
	 * This function is used for bento local find functionality
	 */
	@Keyword
	public void BentoLocalFindDdn() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String ddnXpath = givexpath('Object Repository/Bento/Cases_page/Bento_LocalSearch_popup');
		System.out.println("This is the value of xpath of the dynamic ddn element:"+ddnXpath);
		// Locating the Main Menu (Parent element)
		WebElement dynDDn = driver.findElement(By.xpath(ddnXpath));
		js.executeScript("arguments[0].scrollIntoView(true);", dynDDn);

		//Instantiating Actions class
		Actions actions = new Actions(driver);
		//Hovering on main menu
		actions.moveToElement(dynDDn);

		String optionXpath = givexpath('Object Repository/Bento/Cases_page/Bento_LocalSearch_option');
		System.out.println("This is the value of xpath of the option element:"+optionXpath);
		// Locating the element from Sub Menu
		WebElement firstOption = driver.findElement(By.xpath(optionXpath));
		js.executeScript("arguments[0].scrollIntoView(true);", firstOption);

		Thread.sleep(3000)
		//To mouseover on sub menu
		actions.moveToElement(firstOption);
		Thread.sleep(3000)
		//build()- used to compile all the actions into a single step
		actions.click().build().perform();
		Thread.sleep(3000)
		System.out.println("Reporting frm the keyword : about to complete running bento local find function")
	}

	@Keyword
	public void BentoLocalFindFileUpld(String filetype) {
		String fileUpldXpath = givexpath('Object Repository/Bento/Cases_page/Bento_LocalSearch_Upld_WndwsFileUpload');
		WebElement flUpld=driver.findElement(By.xpath(fileUpldXpath));
		Path inpFile;
		// windows file upload with file path
		if (filetype == 'TXT') {
			inpFile = Paths.get(System.getProperty("user.dir"), "InputFiles", "BentoUploadCaseSet.txt");
		}else if (filetype == 'CSV') {
			inpFile = Paths.get(System.getProperty("user.dir"), "InputFiles", "BentoUploadCaseSet.csv");
		}
		String inpFileStr = inpFile.toString();
		flUpld.sendKeys(inpFileStr);
		Thread.sleep(3000)
		System.out.println("This is the value of the input file for case id local find upload : "+inpFileStr)
	}

	//THIS IS FOR CTDC LOCALFIND**************************************
	@Keyword
	public void CTDCLocalFindDdn() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String ddnXpath = givexpath('Object Repository/Trials/Cases_page/Trials_LocalFind_popup');
		System.out.println("This is the value of xpath of the dynamic ddn element:"+ddnXpath);
		// Locating the Main Menu (Parent element)
		WebElement dynDDn = driver.findElement(By.xpath(ddnXpath));
		js.executeScript("arguments[0].scrollIntoView(true);", dynDDn);

		//Instantiating Actions class
		Actions actions = new Actions(driver);
		//Hovering on main menu
		actions.moveToElement(dynDDn);
		String optionXpath = givexpath('Object Repository/Trials/Cases_page/Trials_LocalFind_option');
		System.out.println("This is the value of xpath of the option element:"+optionXpath);
		// Locating the element from Sub Menu
		WebElement firstOption = driver.findElement(By.xpath(optionXpath));
		js.executeScript("arguments[0].scrollIntoView(true);", firstOption);

		Thread.sleep(3000)
		//To mouseover on sub menu
		actions.moveToElement(firstOption);
		Thread.sleep(3000)
		//build()- used to compile all the actions into a single step
		actions.click().build().perform();
		Thread.sleep(3000)
		System.out.println("Reporting frm the keyword : about to complete running ctdc local find function")
	}

	@Keyword
	public void canineUIValidation() {
		HashMap<String, String> hshmap = new HashMap<String, String>();    /*declaring HashMap */
		hshmap.put("Study Dropdown", 'Object Repository/Canine/Filter/Study/Canine_Filter_Study');  /*Adding elements to HashMap*/
		hshmap.put("Study Type Dropdown", 'Object Repository/Canine/Filter/StudyType/Canine_Filter_StudyType');
		hshmap.put("Breed Dropdown", 'Object Repository/Canine/Filter/Breed/BREED_Ddn');
		hshmap.put("Diagnosis Dropdown", 'Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn');
		hshmap.put("Primary Disease Site Dropdown", 'Object Repository/Canine/Filter/PrimDiseaseSite/PRIMARYDISEASESITE_Ddn');
		hshmap.put("Sex Dropdown", 'Object Repository/Canine/Filter/Sex/SEX_Ddn');

		System.out.println("passing hash map to the validaiton function")
		UIValidation(hshmap)  // calling the validation function
		System.out.println("successfully completed UI validaiton")
	}

	@Keyword
	public void trialsUIValidation(){
	}

	//**************************************
	@Keyword
	public void footerVal() {
		HashMap<String, String> hshmap = new HashMap<String, String>();    /*declaring HashMap */

		//About ICDC****************
		hshmap.put("Purpose Hyperlink", 'Object Repository/Canine/Footer/Purpose_Hplink');
		hshmap.put("Steering Committee Hyperlink", 'Object Repository/Canine/Footer/SteeringComm_Hplink');
		hshmap.put("CRDC Hyperlink", 'Object Repository/Canine/Footer/CRDC_Hplink');
		hshmap.put("ContactUs Hyperlink", 'Object Repository/Canine/Footer/ContactUs_Hplink');
		//About the Data *****************
		hshmap.put("ICDC DataModel Hyperlink", 'Object Repository/Canine/Footer/ICDCDataModel_Hplink');
		hshmap.put("AnalyzingData Hyperlink", 'Object Repository/Canine/Footer/AnalyzingData_Hplink');
		hshmap.put("Developers Hyperlink",'Object Repository/Canine/Footer/Developers_Hplink');
		hshmap.put("Submission Guide Hyperlink",'Object Repository/Canine/Footer/SubmissionGuide_Hplink');

		//More Information***********************
		hshmap.put("Policies Hyperlink",'Object Repository/Canine/Footer/Policies_Hplink')
		hshmap.put("Disclaimer Hyperlink",'Object Repository/Canine/Footer/Disclaimer_Hplink')
		hshmap.put("Accessibility Hyperlink",'Object Repository/Canine/Footer/Accessibility_Hplink');
		hshmap.put("FOIA Hyperlink",'Object Repository/Canine/Footer/FOIA_Hplink')

		//other links*******************
		hshmap.put("HHS Hyperlink",'Object Repository/Canine/Footer/HHS_Hplink')
		hshmap.put("NIH Hyperlink",'Object Repository/Canine/Footer/NIH_Hplink')
		hshmap.put("NCI Hyperlink",'Object Repository/Canine/Footer/NCI_Hplink')
		hshmap.put("USA Hyperlink",'Object Repository/Canine/Footer/USA_Hplink')

		//labels*******************************
		hshmap.put("Turning Discovery Label",'Object Repository/Canine/Footer/TurningDiscIntoHlth_Label')
		hshmap.put("National Cancer Institute Image",'Object Repository/Canine/Footer/NatCanInst_Img')
		hshmap.put("About ICDC Label",'Object Repository/Canine/Footer/AboutICDC_Label')
		hshmap.put("About the Data Label",'Object Repository/Canine/Footer/AboutTheData_Label')
		hshmap.put("More Info Label",'Object Repository/Canine/Footer/MoreInfo_Label')

		System.out.println("passing hash map to the validation function")
		UIValidation(hshmap)  // calling the validation function
		System.out.println("successfully completed UI validaiton")
	}


	//******************UI VALIDATION - HASHMAP VALIDATION FUNCTION ***************************
	public void UIValidation(HashMap<String, String> hmap) {
		Set set = hmap.entrySet();     /* Display content using Iterator*/
		Iterator iter = set.iterator();
		while(iter.hasNext()) {
			Map.Entry mpEntry = (Map.Entry)iter.next();
			//System.out.print("key is: "+ mpEntry.getKey() + " & Value is: "+ mpEntry.getValue());
			String elemXpath = givexpath(mpEntry.getValue())
			//System.out.println ("Xpath of the given object is : "+elemXpath)
			if(driver.findElement(By.xpath(elemXpath))!= null){
				KeywordUtil.markPassed(mpEntry.getKey()+" is Present");
			}else{
				KeywordUtil.markFailed(mpEntry.getKey()+" is Absent");
			}
		}
	}

	//compare lists***********************************************************

	//trying new comparetwolists function
	public static List<String> findMismatchedItems(List<XSSFCell> list1, List<XSSFCell> list2) {
		List<String> mismatchedItems = new ArrayList<>();

		if (list1.size() != list2.size()) {
			mismatchedItems.add("List sizes are different.");
			return mismatchedItems;
		}

		for (int i = 0; i < list1.size(); i++) {
			XSSFCell cell1 = list1.get(i);
			XSSFCell cell2 = list2.get(i);

			if (!cell1.toString().equals(cell2.toString())) {
				mismatchedItems.add("Cell " + (i + 1) + ": " + cell1.toString() + " <> " + cell2.toString());
			}
		}

		return mismatchedItems;
	}


	//trying another comparelists function
	public static List<String> findMismatchedItemstry2(List<XSSFCell> list1, List<XSSFCell> list2) {
		List<String> mismatchedItems = new ArrayList<>();

		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();

		for (XSSFCell cell : list1) {
			set1.add(cell.toString());
		}

		for (XSSFCell cell : list2) {
			set2.add(cell.toString());
		}

		if (!set1.equals(set2)) {
			mismatchedItems.add("Lists have different cell values.");
		}

		return mismatchedItems;
	}



	//@@@@@@@@@@@@@@@ SOHIL's Code @@@@@@@@@@@@@@@@@@@@
	/**
	 * This function compares two sheet. Other functions are called in this function 
	 * To perform the entire action
	 * @param webSheetName
	 * @param neoSheetName
	 */
	@Keyword
	public static void compareSheets(String webSheetName, String neoSheetName) {

		List<List<String>> UIData = new ArrayList<>();
		List<List<String>> neo4jData = new ArrayList<>();

		// Initializing files path
		String UIfilename = GlobalVariable.G_WebExcel.toString();
		String neo4jfilename = GlobalVariable.G_ResultPath.toString();

		System.out.println("This is  the  full UI file  path: " + UIfilename);
		System.out.println("This is the full neo4j file path: " + neo4jfilename);

		// Read UI output excel
		UIData = ReadExcel.readOutputExcel(UIfilename, webSheetName);
		Collections.sort(UIData, new TestRunner());

		// Read TSV or DB output excel
		neo4jData = ReadExcel.readOutputExcel(neo4jfilename, neoSheetName);
		Collections.sort(neo4jData, new TestRunner());

		System.out.println("This is the Entire UIWeb  data: " + UIData);
		System.out.println("This is the Entire neo4j  data: " + neo4jData);
		System.out.println("This is the row size of the UIWeb Output data: " + UIData.size());
		System.out.println("This is the row size of the Neo4j Output data: " + neo4jData.size());

		//PythonReader.compareLists("CompareData", UIData, neo4jData)
		compareTwoLists(UIData, neo4jData);
	}

	/**
	 * This function reads two lists and compares it's content
	 * @param l1 (specified for UI) a List that will have inner list
	 * @param l2 (specified for DB) a List that will have inner list
	 */
	public static void compareTwoLists(List<List<String>> l1, List<List<String>> l2){

		System.out.println("============== Verification of the data ==============")

		int l2row=0;

		while( l2row < l2.size() ){

			for( int l1row = 0; l1row < l1.size(); l1row++ ){

				List<String> l1rowList = l1.get(l1row)
				List<String> l2rowList = l2.get(l2row)
				boolean l1NullFlag = false, l2NullFlag = false

				int l1rowCount =l1row+2;
				int l2rowCount =l2row+2;

				System.out.println("UI Data Entire Row:  " + l1rowList)
				System.out.println("DB data Entire Row:  " + l2rowList)

				// Check if column counts do not match
				if (l1rowList.size() != l2rowList.size()) {
					System.err.println("*********** COLUMN COUNT MISMATCH ***********");
					System.err.println("UI Data Row: " + l1rowCount + " has " + l1rowList.size() + " columns.");
					System.err.println("DB Data Row: " + l2rowCount + " has " + l2rowList.size() + " columns.");
					KeywordUtil.markFailed("*********** COLUMN COUNT MISMATCH *************");
					return;  // Exit the function since the column counts do not match
				}

				for(int col = 0; col < l2rowList.size(); col++ ){

					String l1Col = l1rowList.get(col);
					String l2Col = l2rowList.get(col);

					String l1Value = l1rowList.get(col);
					String l2Value = l2rowList.get(col);

					//Check for empty cell in UI excel
					if(l1Col == null || l1Value.trim().isEmpty()){
						System.out.println("There is an empty cell in UI Data Row: " + l1rowCount + " Col: " + col );
						l1NullFlag = true
					}

					//Check for empty cell in DB excel
					if(l2Col == null || l2Value.trim().isEmpty()){
						System.out.println("There is an empty cell in DB Data Row: " + l2rowCount + " Col: " + col );
						l2NullFlag = true
					}

					//When UI and DB empty cell don't match, warn user
					if (l1NullFlag != l2NullFlag) {
						System.err.println("********** EMPTY CELL MISMATCH **********")
						l1NullFlag = false
						l2NullFlag = false
					}

					//When there is data, compare UI value against DB value
					if(l1Value.equals(l2Value)){
						System.out.println("UI data cell value is:  "+ l1Value + "\nDB data cell value is:  "+ l2Value );
						System.out.println("Content matches for Row: " + l1rowCount + " Col: " + col +" \u2713");
					}else{
						System.err.println("*********** DATA MISMATCH ***********")
						System.err.println("UI data cell value is:  "+ l1Value + "\nDB data cell value is:  "+ l2Value );
						System.err.println("Content does not match for Row: " + l1rowCount + " Col: " + col +" \u2717")
						KeywordUtil.markFailed("*********** DATA MISMATCH in compareTwoLists *************");
					}
				}
				l2row++
			}
		}
	}

	/**
	 * This function reads two lists and compares it's content
	 * @param l1 (specified for UI) a List that will have inner list
	 * @param l2 (specified for DB) a List that will have inner list
	 */
	//	public static void compareTwoLists(List<List<XSSFCell>> l1, List<List<XSSFCell>> l2){
	//
	//		System.out.println("============== Verification of the data ==============")
	//		int l2row=0;
	//
	//		while( l2row < l2.size() ){
	//
	//			for( int l1row = 0; l1row < l1.size(); l1row++ ){
	//
	//				List<XSSFCell> l1rowList = l1.get(l1row)
	//				List<XSSFCell> l2rowList = l2.get(l2row)
	//				boolean l1NullFlag = false, l2NullFlag = false
	//
	//				int l1rowCount =l1row+2;
	//				int l2rowCount =l2row+2;
	//
	//				System.out.println("UI Data Entire Row:  " + l1rowList)
	//				System.out.println("DB data Entire Row:  " + l2rowList)
	//
	//				// Check if column counts do not match
	//				if (l1rowList.size() != l2rowList.size()) {
	//					System.err.println("*********** COLUMN COUNT MISMATCH ***********");
	//					System.err.println("UI Data Row: " + l1rowCount + " has " + l1rowList.size() + " columns.");
	//					System.err.println("DB Data Row: " + l2rowCount + " has " + l2rowList.size() + " columns.");
	//					KeywordUtil.markFailed("*********** COLUMN COUNT MISMATCH *************");
	//					return;  // Exit the function since the column counts do not match
	//				}
	//
	//				for(int col = 0; col < l2rowList.size(); col++ ){
	//
	//					XSSFCell l1Col = l1rowList.get(col);
	//					XSSFCell l2Col = l2rowList.get(col);
	//
	//					String l1Value = l1rowList.get(col).getStringCellValue();
	//					String l2Value = l2rowList.get(col).getStringCellValue();
	//
	//					//Check for empty cell in UI excel
	//					if(l1Col == null || l1Col.getCellTypeEnum() == CellType.BLANK || l1Value.trim().isEmpty()){
	//						System.out.println("There is an empty cell in UI Data Row: " + l1rowCount + " Col: " + col );
	//						l1NullFlag = true
	//					}
	//
	//					//Check for empty cell in DB excel
	//					if(l2Col == null || l2Col.getCellTypeEnum() == CellType.BLANK || l2Value.trim().isEmpty()){
	//						System.out.println("There is an empty cell in DB Data Row: " + l2rowCount + " Col: " + col );
	//						l2NullFlag = true
	//					}
	//
	//					//When UI and DB empty cell don't match, warn user
	//					if (l1NullFlag != l2NullFlag) {
	//						System.err.println("********** EMPTY CELL MISMATCH **********")
	//						l1NullFlag = false
	//						l2NullFlag = false
	//					}
	//
	//					//When there is data, compare UI value against DB value
	//					if(l1Value.equals(l2Value)){
	//						System.out.println("UI data cell value is:  "+ l1Value + "\nDB data cell value is:  "+ l2Value );
	//						System.out.println("Content matches for Row: " + l1rowCount + " Col: " + col +" \u2713");
	//					}else{
	//						System.err.println("*********** DATA MISMATCH ***********")
	//						System.err.println("UI data cell value is:  "+ l1Value + "\nDB data cell value is:  "+ l2Value );
	//						System.err.println("Content does not match for Row: " + l1rowCount + " Col: " + col +" \u2717")
	//						KeywordUtil.markFailed("*********** DATA MISMATCH in compareTwoLists *************");
	//					}
	//				}
	//				l2row++
	//			}
	//		}
	//	}



	//**************************************************************************************
	//this is a duplicate of comparelists created to test the xl manifest and cart xl comparison
	@Keyword
	public static void compareManifestLists(String webCartSheetName, String manifestSheetName) {
		//pass the sheet names only. file name is not needed
		System.out.println("This is the name of the current test case from global variable : "+GlobalVariable.G_currentTCName)
		String newfilename = GlobalVariable.G_currentTCName+"_Manifest";
		String xlsxManifestName = newfilename +".xlsx";
		Path xlsxfilename = Paths.get(System.getProperty("user.dir"), "OutputFiles", xlsxManifestName);
		System.out.println("This is the file name of xlsx manifest: "+GlobalVariable.G_xlsxFileName);

		List<List<XSSFCell>> UIData = new ArrayList<>()
		List<List<XSSFCell>> manifestData = new ArrayList<>()
		String UIfilename =  GlobalVariable.G_WebExcel.toString()
		System.out.println("This is the full webdata pathname for my cart :"+UIfilename);
		UIData = ReadExcel.readExceltoWeblist(UIfilename,webCartSheetName)

		System.out.println("This is the data read and stored in arraylist UIData : "+UIData)
		System.out.println ("This is the row size of the UIdata : "+ UIData.size());
		Collections.sort( UIData , new TestRunner())

		GlobalVariable.G_xlsxFilename = xlsxfilename.toString()
		manifestData = ReadExcel.readExceltoWeblist(GlobalVariable.G_xlsxFilename, manifestSheetName)

		System.out.println ("This is the row size of the Neo4jdata : "+ manifestData.size());
		Collections.sort( manifestData , new TestRunner())
		compareTwoLists(UIData,manifestData)
	}

	/**
	 * This function validates stat-bar values
	 * @param getAppName Application name i.e 'CDS'
	 */
	@Keyword
	public static void validateStatBar(getAppName) {

		List<List<XSSFCell>> statData = new ArrayList<>()
		String neo4jfilename=  GlobalVariable.G_ResultPath.toString()
		statData = ReadExcel.readOutputExcel(neo4jfilename, GlobalVariable.G_StatTabname)

		if(getAppName=='Bento'){

			System.out.println("This is the first row - stat data read from neo4j stat sheet : "+statData[0])
			System.out.println("This is the value of Programs Count from Neo4j result "+statData.get(0).get(0).getStringCellValue())  //add in the query in input file later
			System.out.println("This is the value of Arms Count from Neo4j result "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Cases Count from Neo4j result "+statData.get(0).get(2).getStringCellValue())
			System.out.println("This is the value of Samples Count from Neo4j result "+statData.get(0).get(3).getStringCellValue())
			System.out.println("This is the value of Assays Count from Neo4j result "+statData.get(0).get(4).getStringCellValue())
			System.out.println("This is the value of Files Count from Neo4j result "+statData.get(0).get(5).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Programs)) ? KeywordUtil.markPassed("Statbar Programs count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Programs count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Arms)) ? KeywordUtil.markPassed("Statbar Arms count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Arms count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Cases)) ? KeywordUtil.markPassed("Statbar Cases count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Cases count")
			(statData.get(0).get(3).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Samples)) ? KeywordUtil.markPassed("Statbar Samples count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Samples count")
			(statData.get(0).get(4).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Assays)) ? KeywordUtil.markPassed("Statbar Assays count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Assays count")
			(statData.get(0).get(5).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Files)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		}else if (getAppName=='INS') {

			System.out.println("This is the value of Programs Count from Neo4j result "+statData.get(0).get(0).getStringCellValue())
			System.out.println("This is the value of Projects Count from Neo4j result "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Grants Count from Neo4j result "+statData.get(0).get(2).getStringCellValue())
			System.out.println("This is the value of Publications Count from Neo4j result "+statData.get(0).get(3).getStringCellValue())
			System.out.println("This is the value of Datasets Count from Neo4j result "+statData.get(0).get(4).getStringCellValue())
			System.out.println("This is the value of Clinical Trials Count from Neo4j result "+statData.get(0).get(5).getStringCellValue())
			System.out.println("This is the value of Patents from Neo4j result "+statData.get(0).get(6).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Programs)) ? KeywordUtil.markPassed("Statbar Programs count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Programs count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Projects)) ? KeywordUtil.markPassed("Statbar Projects count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Projects count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Grants)) ? KeywordUtil.markPassed("Statbar Grants count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Grants count")
			(statData.get(0).get(3).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Publications)) ? KeywordUtil.markPassed("Statbar Publications count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Publications count")
			(statData.get(0).get(4).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Datasets)) ? KeywordUtil.markPassed("Statbar Datasets count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Datasets count")
			(statData.get(0).get(5).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_ClinTrials)) ? KeywordUtil.markPassed("Statbar clinical Trials count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Clinical Trials count")
			(statData.get(0).get(6).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Patents)) ? KeywordUtil.markPassed("Statbar Patents count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Patents count")
		}else if (getAppName=='ICDC'){

			System.out.println ("control is in line 1842");
			System.out.println("This is the value of Programs Count from Neo4j result: "+statData.get(0).get(0).getStringCellValue())
			System.out.println("This is the value of Studies Count from Neo4j result: "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Cases Count from Neo4j result: "+statData.get(0).get(2).getStringCellValue())
			System.out.println("This is the value of Samples Count from Neo4j result: "+statData.get(0).get(3).getStringCellValue())
			System.out.println("This is the value of CaseFiles Count from Neo4j result: "+statData.get(0).get(4).getStringCellValue())
			System.out.println("This is the value of StudyFiles Count from Neo4j result: "+statData.get(0).get(5).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Programs)) ? KeywordUtil.markPassed("Statbar Programs count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Programs count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Studies)) ? KeywordUtil.markPassed("Statbar Studies count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Studies count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Cases)) ? KeywordUtil.markPassed("Statbar Cases count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Cases count")
			(statData.get(0).get(3).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Samples)) ? KeywordUtil.markPassed("Statbar Samples count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Samples count")
			(statData.get(0).get(4).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Files)) ? KeywordUtil.markPassed("Statbar Case Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Case Files count")
			(statData.get(0).get(5).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_StudyFiles)) ? KeywordUtil.markPassed("Statbar Study Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Study Files count")
		}else if (getAppName=='CTDC') {

			System.out.println("This is the value of Trials Count from Neo4j result "+statData.get(0).get(0).getStringCellValue())
			System.out.println("This is the value of Cases Count from Neo4j result "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Files Count from Neo4j result "+statData.get(0).get(2).getStringCellValue())
			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_TStatBar_Trials)) ? KeywordUtil.markPassed("Statbar Trials count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Trials count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_TStatBar_Cases)) ? KeywordUtil.markPassed("Statbar Cases count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Cases count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_TStatBar_Files)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		}else if (getAppName=='GMB') {

			System.out.println("This is the value of Trails Count from Neo4j result "+statData.get(0).get(0).getStringCellValue())
			System.out.println("This is the value of Subjects Count from Neo4j result "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Files Count from Neo4j result "+statData.get(0).get(2).getStringCellValue())
			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_GStatBar_Trials)) ? KeywordUtil.markPassed("Statbar Trials count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Trials count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_GStatBar_Subjects)) ? KeywordUtil.markPassed("Statbar Subjects count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Subjects count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_GStatBar_Files)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		}else if (getAppName=='CDS'){

			System.out.println("This is the value of Studies Count from Neo4j result:  "+statData.get(0).get(0).getStringCellValue())  //add in the query in input file later
			System.out.println("This is the value of Partici Count from Neo4j result:  "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Samples Count from Neo4j result:  "+statData.get(0).get(2).getStringCellValue())
			System.out.println("This is the value of Files   Count from Neo4j result:  "+statData.get(0).get(3).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Studies)) ? KeywordUtil.markPassed("Statbar Studies count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Studies count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Participants)) ? KeywordUtil.markPassed("Statbar Participants count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Participants count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Samples)) ? KeywordUtil.markPassed("Statbar Samples count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Samples count")
			(statData.get(0).get(3).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Files)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		}else if (getAppName=='CCDI'){

			System.out.println("This is the value of Studies Count from Neo4j result: "+statData.get(0).get(0).getStringCellValue())  //add in the query in input file later
			System.out.println("This is the value of Participants Count from Neo4j result: "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Samples Count from Neo4j result: "+statData.get(0).get(2).getStringCellValue())
			System.out.println("This is the value of Files Count from Neo4j result: "+statData.get(0).get(3).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Studies)) ? KeywordUtil.markPassed("Statbar Studies count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Studies count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Participants)) ? KeywordUtil.markPassed("Statbar Participants count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Participants count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Samples)) ? KeywordUtil.markPassed("Statbar Samples count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Samples count")
			(statData.get(0).get(3).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Files)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		}else if (getAppName=='C3DC'){

			System.out.println("This is the value of Diagnoses Count from Neo4j result: "+statData.get(0).get(0).getStringCellValue())  //add in the query in input file later
			System.out.println("This is the value of Participants Count from Neo4j result: "+statData.get(0).get(1).getStringCellValue())
			System.out.println("This is the value of Studies Count from Neo4j result: "+statData.get(0).get(2).getStringCellValue())

			(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Diagnosis)) ? KeywordUtil.markPassed("Statbar Diagnoses count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Diagnosis count")
			(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Participants)) ? KeywordUtil.markPassed("Statbar Participants count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Participants count")
			(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Studies)) ? KeywordUtil.markPassed("Statbar Studies count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Studies count")
		}
	}


	@Keyword
	public void validateTrialsStatBar() {
		List<List<XSSFCell>> statData = new ArrayList<>()
		String neo4jfilename=  GlobalVariable.G_ResultPath.toString()
		statData = ReadExcel.readExceltoWeblist(neo4jfilename,GlobalVariable.G_StatTabname)  //change the function name Test in parent class and here
		System.out.println("This is the first row - stat data read from neo4j stat sheet : "+statData[0])
		System.out.println("This is the value of Files Count from Neo4j result "+statData.get(0).get(0).getStringCellValue())
		System.out.println("This is the value of Cases Count from Neo4j result "+statData.get(0).get(1).getStringCellValue())
		System.out.println("This is the value of Trials Count from Neo4j result"+statData.get(0).get(2).getStringCellValue())
		(statData.get(0).get(0).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Datasets)) ? KeywordUtil.markPassed("Statbar Files count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Files count")
		(statData.get(0).get(1).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_ClinTrials)) ? KeywordUtil.markPassed("Statbar Cases count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Cases count")
		(statData.get(0).get(2).getStringCellValue().contentEquals(GlobalVariable.G_StatBar_Grants)) ? KeywordUtil.markPassed("Statbar Trials count matches"): KeywordUtil.markFailed("Mismatch in Stat Bar Trials count")
	}


	@Keyword
	public static clickTab(String TabName){

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String rawTabName = TabName
		String tabxpath = givexpath(TabName)
		System.out.println("This is the value of xpath of the element: "+tabxpath);
		//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tabxpath)));
		WebElement resultTab = driver.findElement(By.xpath(tabxpath));
		js.executeScript("arguments[0].scrollIntoView(true);", resultTab);
		js.executeScript("arguments[0].click();", resultTab);
		System.out.println("Successfully clicked desired element")
	}


	@Keyword
	public static clickTabCanineStat(String TbName){

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String rawTabName = TbName
		String tabxpath = givexpath(TbName)
		System.out.println("This is the value of xpath of the element: "+tabxpath);
		WebElement resultTab = driver.findElement(By.xpath(tabxpath));
		js.executeScript("arguments[0].scrollIntoView(true);", resultTab);
		js.executeScript("arguments[0].click();", resultTab);
		System.out.println("Successfully clicked desired element")

		String xcCases = givexpath('Object Repository/Canine/StatBar/Canine_StatBar-Cases')
		GlobalVariable.G_StatBar_Cases = driver.findElement(By.xpath(xcCases)).getAttribute("innerText");
		System.out.println("This is the value of Cases count from Stat bar :"+GlobalVariable.G_StatBar_Cases)
		Thread.sleep(1000)
	}

	@Keyword
	public static clickTabINSStat(String TbName){

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String rawTabName = TbName
		String tabxpath = givexpath(TbName)
		System.out.println("This is the value of xpath of the element: "+tabxpath);
		WebElement resultTab = driver.findElement(By.xpath(tabxpath));
		js.executeScript("arguments[0].scrollIntoView(true);", resultTab);
		js.executeScript("arguments[0].click();", resultTab);
		System.out.println("Successfully clicked desired element")

		String xcprojects = givexpath('Object Repository/INS/Statbar/INS_Statbar-Projects')
		GlobalVariable.G_StatBar_Grants = driver.findElement(By.xpath(xcprojects)).getAttribute("innerText");
		System.out.println("This is the value of Projects count from Stat bar :"+GlobalVariable.G_StatBar_Grants)
		Thread.sleep(1000)
	}

	@Keyword
	public static clickTabGMBStat(String TbName){

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String rawTabName = TbName
		String tabxpath = givexpath(TbName)
		System.out.println("This is the value of xpath of the element: "+tabxpath);
		WebElement resultTab = driver.findElement(By.xpath(tabxpath));
		js.executeScript("arguments[0].scrollIntoView(true);", resultTab);
		js.executeScript("arguments[0].click();", resultTab);
		System.out.println("Successfully clicked desired element")
	}


	@Keyword
	public static clickTabCDSStat(String TbName){

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String rawTabName = TbName
		String tabxpath = givexpath(TbName)
		System.out.println("This is the value of xpath of the element: "+tabxpath);
		WebElement resultTab = driver.findElement(By.xpath(tabxpath));
		js.executeScript("arguments[0].scrollIntoView(true);", resultTab);
		js.executeScript("arguments[0].click();", resultTab);
		System.out.println("Successfully clicked desired element")


		//String xcStudies = givexpath('Object Repository/CDS/StatBar/CDS_StatBar-Studies')
		//GlobalVariable.G_StatBar_Files = driver.findElement(By.xpath(xcStudies)).getAttribute("innerText");
		//System.out.println("This is the value of Studies count from Stat bar: "+GlobalVariable.G_StatBar_Files)
		Thread.sleep(3000)
	}

	/**
	 * This method scrolls 
	 * @param elem
	 */
	@Keyword
	public static void scrolltoViewjs(WebElement elem){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", elem);
	}
	@Keyword
	public static void clickElement(WebElement el){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", el);
	}

	@Keyword
	public static Select_case_checkbox( String caseID,String count){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//String one_path ="//a[contains( text(),'"+ caseID +"')]//parent::div//parent::td//preceding-sibling::td/div/span/span/input"


		System.out.println(" In the function  " + count + "caseid : "  + caseID )
		String one_path;
		switch(count){
			case("one"):

				if (driver.getCurrentUrl().contains("bento-tools.org/")){
					one_path ="//a[contains( text(),'"+ caseID +"')]//parent::div//parent::div//parent::td//preceding-sibling::td/div/span/span/input"
				}
				else if (driver.getCurrentUrl().contains("caninecommons")){

					one_path ="//a[contains( text(),'"+ caseID +"')]//parent::div//parent::div//parent::td//preceding-sibling::td/div/span/span/input"
				}

				WebElement checkbox =driver.findElement(By.xpath(one_path))
				js.executeScript("arguments[0].click();", checkbox)

				break;
			case ("all"):
				String all_path ="//div[@id=\'case_tab_table\']//thead/tr/th/div/span/span/input"

				System.out.println ("All Path :" + all_path )
				WebElement checkbox =driver.findElement(By.xpath(all_path))
				js.executeScript("arguments[0].click();", checkbox)

				break;
			case ("allM"):
				String all_M="//div[text()=\'Case ID\']//parent::div//parent::div//parent::span//parent::th//preceding-sibling::th/div/span/span/input"
				System.out.println ("All Path :" + all_M )
				WebElement checkbox =driver.findElement(By.xpath(all_M))
				js.executeScript("arguments[0].click();", checkbox)

				break;
			case ("caseM"):
				String all_M="//div[text()='" + caseID + "']//parent::div//parent::div//parent::span//parent::th//preceding-sibling::th/div/span/span/input"
				System.out.println ("All Path :" + all_M )
				WebElement checkbox =driver.findElement(By.xpath(all_M))
				js.executeScript("arguments[0].click();", checkbox)
				break;
		}
	}


	@Keyword
	public  static isDriverOpen(){

		try{
			DriverFactory.getCurrentWindowIndex()
			//driver.getTitle();
			KeywordUtil.logInfo("A browser instance is already open.")
			System.out.println("A browser instance is already open. Quitting the browser")
			driver.quit()
			// browser is open
		} catch(NoSuchSessionError) {
			// browser is closed
			KeywordUtil.logInfo("Browser is NOT Existing")
		}
	}


	@Keyword
	public static JsFunc() {
		String caseID ='COTC007B0203'
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String one_path ="//a[contains( text(),'"+ caseID +"')]//parent::div//parent::td//preceding-sibling::td/div/span/span/input"
		System.out.println ("one_path :" + one_path)
		WebElement checkbox =driver.findElement(By.xpath(one_path))
		js.executeScript("arguments[0].click();", checkbox)
	}


	@Keyword
	public static File_details(String tbl1, String hdr1, String nxtb1) {

		List<String> caseId = new ArrayList<String>()

		//List<String> webData = new ArrayList<String>();
		String tbl_main= givexpath(tbl1)
		String tbl_bdy=     tbl_main+"//tbody"
		GlobalVariable.G_cannine_caseTblBdy=tbl_bdy

		String tbl_str= givexpath(tbl1)                                   //"//div[contains(text(),'Case')]//parent::span//parent::th//parent::tr//parent::thead//following-sibling::tbody"
		WebElement Table =driver.findElement(By.xpath(tbl_str))

		List<WebElement> rows_table = Table.findElements(By.xpath("//*[contains(@id, \"MUIDataTableBodyRow-\")]"))
		int rows_count = rows_table.size()
		System.out.println("This is the size of the rows in the table in first page in files: "+(rows_count))
		System.out.println("This is the  url of thecurrent  page : "+driver.getCurrentUrl())

		String nxt_str=     givexpath(nxtb1)
		WebElement nextButton = driver.findElement(By.xpath(nxt_str));
		System.out.println("This is the value of next button coming from file_details function : "+nextButton)
		String hdr_str= givexpath(hdr1)
		WebElement tableHdr = driver.findElement(By.xpath(hdr_str))

		List<WebElement> colHeader = tableHdr.findElements(By.tagName("th"));
		int columns_count = (colHeader.size())-1
		System.out.println("No.of cols in the case detail page is : "+columns_count)
		String sTable
		String sHeasder
		String sNext
		String hdrdata = ""

		while(true) {
			rows_table = Table.findElements(By.xpath("//*[contains(@id, \"MUIDataTableBodyRow-\")]"))
			rows_count = rows_table.size()
			System.out.println("This is the size of the rows in the table in the current page: "+(rows_count))
			for(int i = 1; i <= rows_count; i++) {
				//rows_count
				String data = ""
				String sCase
				int tblcol=GlobalVariable.G_rowcount_Katalon; //12 //19 This is for icdc

				sCase= ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + 2 + "]")).getText())) // this is for Bento
				data =  sCase
				System.out.println ("This is the case ID before clicking:" + sCase)
				clickcase sCase  // calling the function clickcase

				// TO DO
				//Read case level stat bar
				// Neo 4 Data base query
				// Wedata from files AVALABLE FILES
				//Compare Ne4j output and Web data for file

				caseId.add(data)
			}
			if (nxtBtn.getAttribute("disabled")) break;
			nxtBtn.click()
		}
		GlobalVariable.G_CasesArray= caseId;
		System.out.println("This is the contents of globalvar G_casesarray: " +GlobalVariable.G_CasesArray)
	}
	//}

	@Keyword
	public static void clickcase(String lCases ) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i

		String Str1
		Str1 = "//a[contains(@href,'" + lCases + "')]"
		WebElement caseIDlink =driver.findElement(By.xpath(Str1))
		js.executeScript("arguments[0].click();", caseIDlink)
		System.out.println ("This is the url of the current page - case details (before reading case details table): "+driver.getCurrentUrl())
		// calling the below function reads the data in the case details table
		ReadCasesTableKatalon(GlobalVariable.G_StatBar_Grants,'Object Repository/Bento/CaseDetail_page/Bento_CDFilesTable','Object Repository/Bento/CaseDetail_page/Bento_CDFilesTable_Hdr', 'Object Repository/Bento/CaseDetail_page/Bento_CDFilesTable_NxtBtn',GlobalVariable.G_caseDetailsTabName)

		driver.navigate().back()
		System.out.println ("This is the url of the current page - all cases, AFTER reading case details table) :"+driver.getCurrentUrl())
		casedetailsQueryBuilder(lCases)
	}


	@Keyword
	public static void casedetailsQueryBuilder(String lCases ) {
		System.out.println("This is the value of lcasesfromfunction: "+lCases)
		System.out.println("First part new is : "+GlobalVariable.G_CaseDetailsQFirstPart)
		System.out.println("Second part new is : "+GlobalVariable.G_CaseDetailsQSecondPart)

		String finalQ = GlobalVariable.G_CaseDetailsQFirstPart + lCases + GlobalVariable.G_CaseDetailsQSecondPart
		System.out.println ("This is the concatenated query for breed greyhound: "+finalQ )

		GlobalVariable.G_CaseDetailQ=finalQ
		System.out.println ("This is the reassigned global variable from query builder function: "+GlobalVariable.G_CaseDetailQ )
	}
}  //class ends here
