package ctdc.utilities

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.util.concurrent.TimeUnit;

import internal.GlobalVariable

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver as FirefoxDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.*
import java.lang.String as String
import groovy.transform.Field as Field


import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.*
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileDescriptor
import java.io.File
import java.lang.String
import java.lang.Object
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.String as String
import groovy.transform.Field as Field
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import static org.junit.Assert.*;
import internal.GlobalVariable
import ctdc.utilities.ReadExcel   //to use various functions from the class: ExcelToArray



public class RunTestcase implements Comparator<List<XSSFCell>>{
	public int compare( List<XSSFCell> l1, List<XSSFCell> l2 ){
		return l1.get(0).getStringCellValue() .compareTo( l2.get(0).getStringCellValue() )
	}


	public static WebDriver driver

	@Keyword
	public  void Run( String InputExcelname,String pwd_file) {

		Thread.sleep(2000)
		Path filepath = Paths.get(System.getProperty("user.dir"), "TestData", InputExcelname); // give the Input Excel Name in manual mode in TC
		System.out.println("This is the full filepath after converting to string :"+filepath.toString());
		Path file_pwd = Paths.get(System.getProperty("user.dir"), "TestData", pwd_file); // give the Input Excel Name in manual mode in TC
		GlobalVariable.G_pwd_file=file_pwd.toString()
		GlobalVariable.G_InputExcelFileName=filepath.toString()

		List<List<XSSFCell>> sheetData = new ArrayList<>();
		FileInputStream fis = new FileInputStream(filepath.toString());
		XSSFWorkbook workbook = new XSSFWorkbook(fis); // Create an excel workbook from the file system.
		int numberOfSheets = workbook.getNumberOfSheets();  	// Get the  sheets on the workbook

		int countrow = 0
		int countcol= 0


		XSSFSheet sheet = workbook.getSheetAt(0);  //
		countrow = sheet.lastRowNum- sheet.firstRowNum;
		System.out.println ( "Row count is  : " + countrow);
		countcol = sheet.getRow(0).getLastCellNum();
		System.out.println("Col count is : " + countcol);

		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			List<XSSFCell> data = new ArrayList<>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				data.add(cell);
			}
			sheetData.add(data);
		}
		excelparsing(sheetData,driver);
	}


	private static void excelparsing(List<List<XSSFCell>> sheetData, WebDriver Dr2) {//this is initializing second sheet - test case
		int countrow = 0   // Iterates the data and print it out to the console.
		countrow = sheetData.size();
		System.out.println ( " sheetdata size countrow " + countrow )   //delete
		System.out.println ( "sheet  data size :" + sheetData.get(0).size())  //delete

		for (int ii = 1; ii < countrow; ii++){
			List<XSSFCell> datarow = sheetData.get(ii);

			for (int jj = 0; jj < datarow.size(); jj++){

				XSSFCell cell = datarow.get(jj);

				switch(sheetData.get(0).get(jj).getStringCellValue().trim() )
				{

					case("propertyName"):
						GlobalVariable.G_propertyName = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("propertyvalue"):
						GlobalVariable.G_propertyvalue = sheetData.get(ii).get(jj).getStringCellValue()
						System.out.println ( " THE propertyvalue BEING saved :  "  +  GlobalVariable.G_propertyvalue )
						break;
					case("locateby"):
						GlobalVariable.G_locateby = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("locatorvalue"):
						GlobalVariable.G_locatorvalue = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("action"):
						GlobalVariable.G_Action = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("Query"):
						GlobalVariable.G_Query = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("Page"):
						GlobalVariable.G_Page = sheetData.get(ii).get(jj).getStringCellValue().trim()
						if( GlobalVariable.G_Page=="na"){


						}
						else {

							driver.get(GlobalVariable.G_Page)
						}
						break;
					case("Function"):
						System.out.println ( "  the value to be uses in the function call  : " + sheetData.get(ii).get(jj).getStringCellValue().trim() )
						switch(sheetData.get(ii).get(jj).getStringCellValue().trim() )
						{
							case("InitialLoad"):
								ReadExcel.initialLoad()
								driver=browserDriver (GlobalVariable.G_Browser)
								if( GlobalVariable.G_Page=="na"){
								}
								else {

									driver.get(GlobalVariable.G_Page)
								}

								break;
							case ("Dbconnect"):
								System.out.println  (" In dataload")
								ReadExcel.Neo4j()
								break;
							case ("action_click"):
								driver.manage().window().maximize()

								driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
								driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

								WebDriverWait wait = new WebDriverWait(driver, 30);
								WebElement ElementFromExcel
								ElementFromExcel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(GlobalVariable.G_locatorvalue)))

								ElementFromExcel.click()
								Thread.sleep(3000)
								System.out.println( " clicked on :" + GlobalVariable.G_locatorvalue )

								break;
							case("Select_case_checkbox"):
								String one_all = sheetData.get(ii).get(2).getStringCellValue().trim()
								String Casenum= sheetData.get(ii).get(3).getStringCellValue().trim()
								Select_case_checkbox(driver,Casenum,one_all )
								break;
							case("webdata"):
								List<String> WData = new ArrayList<String>();
								WData=ReadCasesTable(driver)

								break;

							case("compare"):
								compareLists();
								break;
							case("StoreGlobal"):


								GlobalVariable.(sheetData.get(ii).get(3).getStringCellValue())=sheetData.get(ii).get(6).getStringCellValue()

								break;
							case("verify"):
								verify_text(driver,"American Staffordshire Terrier" , GlobalVariable.G_locatorvalue  )
								break ;
							default:
								System.out.println ("nothing in function column :")
								break;
						}
						break;
					case("Run"):   // create a code to use this Run Flag value to decide processing that rows data
						GlobalVariable.G_Run = sheetData.get(ii).get(jj).getStringCellValue()
						break;
					case("Otherimportuser"):
						break;
					default :
						System.out.println ( " here at the last ")
						break;
				}
			}
		}

		//gayathri added these lines

	}

	//----------------web data --------------

	public static Select_case_checkbox( WebDriver driver,String caseID,String count){

		System.out.println(" In the function dumbo " + count + "caseid : "  + caseID )

		switch(count){
			case("one"):
				System.out.println("in case 1")

				String one_path ="//a[contains( text(),"+ caseID +")]//parent::div//parent::td//preceding-sibling::td"
				System.out.println(" In the function dumbo1 "  + one_path )



				driver.findElement(By.xpath(one_path)).click()  //driver.findElement(By.xpath('//a[contains( text(),caseID)]//parent::div//parent::td//preceding-sibling::td'))
				break;
			case ("all"):
				System.out.println(" In the function dumbo ALL")
				driver.findElement(By.xpath("//div[text()=\"Case ID\"]//parent::span//parent::th//preceding-sibling::th")).click()
				break;

		}
	}


	@Keyword
	public static  ReadCasesTable(WebDriver driver) throws IOException {
		List<String> webData = new ArrayList<String>();
		driver.manage().window().maximize();        // WebDriver driver = DriverFactory.getWebDriver()
		Thread.sleep(3000)

		String tbl_str= GlobalVariable.G_cannine_caseTbl 			//"//div[ contains(text(),'Case')]//parent::span//parent::th//parent::tr//parent::thead//parent::table"
		String hdr_str= GlobalVariable.G_cannine_caseHdr			//"//div[contains(text(),'Case')]//parent::span//parent::th//parent::tr//parent::thead"
		String nxt_str=	GlobalVariable.G_cannine_NxtBtn			    //"//span[contains(text(),'Row')]//parent::div//button[@tabindex='0']"
		String tbl_bdy=	GlobalVariable.G_cannine_caseTblBdy								//"//div[contains(text(),'Case')]//parent::span//parent::th//parent::tr//parent::thead//following-sibling::tbody"

		WebElement Table =driver.findElement(By.xpath(tbl_str))
		List<WebElement> rows_table = Table.findElements(By.xpath("//*[contains(@id, \"MUIDataTableBodyRow-\")]"))
		int rows_count = rows_table.size()
		System.out.println("This is the size of the rows in the table in first page:"+(rows_count))
		WebElement nextButton = driver.findElement(By.xpath(nxt_str));

		WebElement tableHdr = driver.findElement(By.xpath(hdr_str))
		List<WebElement> colHeader = tableHdr.findElements(By.tagName("th"));
		int columns_count = (colHeader.size())-1
		System.out.println("No.of cols is : "+columns_count)
		String hdrdata = ""
		for(int c=1;c<=columns_count;c++){
			//hdrdata = hdrdata + ((colHeader.get(c).getText()));
			hdrdata = hdrdata + ((colHeader.get(c).getText()) + "||");
		}
		webData.add(hdrdata);
		System.out.println("Size of web data list with header :" +webData.size())
		for(int index = 0; index < webData.size(); index++) {
			System.out.println("Web Data: with header data is :" + webData.get(index))
		}
		//driver.findElement(By.xpath('//*[@id="root"]/div[3]/div/div[2]/div[1]/div[2]/label/button')).click() // G added this line to close the view
		while(true)
		{
			rows_table = Table.findElements(By.xpath("//*[contains(@id, \"MUIDataTableBodyRow-\")]"))
			rows_count = rows_table.size()
			System.out.println("This is the size of the rows in the table in the current page:"+(rows_count))

			for(int i = 1; i <= rows_count; i++) { //rows_count
				String data = ""
				int tblcol=Integer.parseInt(GlobalVariable.G_rowcount);

				for (int j = 3; j < columns_count+tblcol; j = j + 2) {  //tblcol  changed to columns_count+12

					data = data + ((driver.findElement(By.xpath(tbl_bdy +"/tr" + "[" + i + "]/*[" + j + "]")).getText()) +"||")

				}
				webData.add(data)

			}
			System.out.println("Size of Web Data list with header in current page is : " + webData.size())
			//			for(int index = 0; index < webData.size(); index++) {
			//				System.out.println("Web Data: from current page is" + webData.get(index))
			//			}
			if (!nextButton.isEnabled()) break;
			nextButton.click()
		}
		GlobalVariable.G_CaseData= webData;

		writeToExcel();
	}



	//*********************************************************************************
	//function to write webData to excel -- this writes the web results to excel

	public static void writeToExcel(){
		try
		{
			String excelPath = GlobalVariable.G_WebExcel;
			FileOutputStream fos = new FileOutputStream(new File(excelPath));
			XSSFWorkbook workbook = new XSSFWorkbook(); 		// Create Workbook instance holding .xls file
			XSSFSheet sheet = workbook.createSheet("WebDataCanine"); 		// Create a new Worksheet
			List<String> writeData = new ArrayList<String>();
			writeData = GlobalVariable.G_CaseData
			//System.out.println ("This is the value of writedata from excelpath function :"+writeData)

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
			System.out.println("Canine Web Data has been written to excel successfully");
			workbook.close();
		}catch (IOException ie)
		{
			ie.printStackTrace();
		}

	}//excel method ends here


	//**********************************************************************************
	// fix this function to verify
	public static verify_text( WebDriver driver ,String to_comp , String locator_id){
		String actualString =driver.findElement(By.xpath(locator_id)).getText();
		String expectedString = to_comp;
		if (assertTrue(actualString.contains(expectedString)) ) {
			System.out.println " passed the assertion ";
		}
		else {
			System.out.println( "failed asserted stringcompare ")

		}
	}



	//check if null**********************************************************

	// verify element present



	//compare lists***********************************************************
	public static void compareTwoLists( List<List<XSSFCell>> l1, List<List<XSSFCell>> l2 ){
		System.out.println ("Comparing two Lists");
		for( int l2row = 0; l2row < l2.size(); l2row++ ){
			List<XSSFCell> l2rowList = l2.get(l2row)
			//System.out.println(" L2Row contents: " + l2rowList )

			for( int l1row = 0; l1row < l1.size(); l1row++ ){
				List<XSSFCell> l1rowList = l1.get(l1row)
				//System.out.println(" L1Row contents: " + l1rowList )
				if( l2rowList.get(0).getStringCellValue() == l1rowList.get(0).getStringCellValue() ) //takes CTDCID as the primary key for comparison
				{
					System.out.println(" L1Row contents Matched with: " + l1rowList + " and: " + l2rowList )
					boolean l1NullFlag = false, l2NullFlag = false
					for(int col = 0; col < l2rowList.size(); col++ ){ //compares all the columns in the excels - for each row
						if( l1rowList.get(col) == null || l1rowList.get(col).equals("") || l1rowList.get(col).getCellType() == l1rowList.get(col).CELL_TYPE_BLANK ){
							System.out.println("There is a NULL entry in L1 Row")
							l1NullFlag = true
						}
						if( l2rowList.get(col) == null || l2rowList.get(col).equals("") || l2rowList.get(col).getCellType() == l2rowList.get(col).CELL_TYPE_BLANK ){
							System.out.println("There is a NULL entry in L2 Row")
							l2NullFlag = true
						}
						if( l1NullFlag == l2NullFlag )	System.out.println("Content Matches for col: "+ col)
						else System.out.println("Content does not match for col: " + col )
						if( l1NullFlag || l2NullFlag )	continue 
						System.out.println("L1Cell: "+ l1rowList.get(col).getStringCellValue() + " L2 Cell: "+ l2rowList.get(col).getStringCellValue() )
						if( l1rowList.get(col).getStringCellValue() == l2rowList.get(col).getStringCellValue() ){
							System.out.println("Content matches for col: " + col )
						}
						else{
							System.out.println("Content does not match for col: " + col )
							System.out.println( "L1Row Value: " + l1rowList.get(col).getStringCellValue() )
							System.out.println( "L2Row Value: " + l2rowList.get(col).getStringCellValue() )
						}
					}

				}else{

					// add what the code should display if contents mismatch outside the main loop for CTDC ID
				}
			}

		}
	}

	@Keyword
	public static void compareLists() {
		List<List<XSSFCell>> UIData = new ArrayList<>()
		List<List<XSSFCell>> neo4jData = new ArrayList<>()
		String UIfilename =  GlobalVariable.G_WebExcel.toString()   //UIfilepath.toString()
		System.out.println("This is the full uifilepath after converting to string :"+UIfilename);
		UIData = ReadExcel.readExceltoWeblist(UIfilename)  //change the function name Test in parent class and here
		System.out.println("This is the data read after going through Test function : "+UIData)
		System.out.println ("This is the row size of the UIdata : "+ UIData.size());
		Collections.sort( UIData , new RunTestcase() )

		String neo4jfilename=  GlobalVariable.G_ResultPath.toString()
		System.out.println("This is the full neo4j filepath after converting to string :"+neo4jfilename);
		neo4jData = ReadExcel.readExceltoWeblist(neo4jfilename)  //change the function name Test in parent class and here
		System.out.println ("This is the row size of the Neo4jdata : "+ neo4jData.size());
		Collections.sort( neo4jData , new RunTestcase() )

		compareTwoLists(UIData,neo4jData)
	}


	public static void writeResults(File f, String st){


		OutputStream os = null;
		try {
			// below true flag tells OutputStream to append
			os = new FileOutputStream(f, true);
			os.write(st.getBytes(), 0, st.length());
			System.out.println("ADDED TO FILE");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}



	@Keyword
	public static WebDriver browserDriver(String browserName) {

		WebDriver dr
		switch(browserName)
		{
			case("Chrome"):
				driver = new ChromeDriver()
				Path driverPath = Paths.get(System.getProperty("user.dir"), "chromedriver.exe");
				System.setProperty('webdriver.chrome.driver', driverPath.toString())
				System.out.println ( "  new driver done !!")
				dr=driver
				break;

			case("Firefox"):
				Path driverPath = Paths.get(System.getProperty("user.dir"), "geckodriver.exe");
				System.setProperty('webdriver.gecko.driver', driverPath.toString())
				driver = new FirefoxDriver()  //resolve this issue
				dr=driver
			//	browserDriver=dvr
				break;

			default:
				System.out.println ("Nothing in Browser column")
				dr=driver
				break;
		}
	}

}