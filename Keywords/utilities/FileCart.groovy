package utilities

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.Keys;
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import internal.GlobalVariable
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Cookie as Cookie





public class FileCart extends TestRunner implements Comparator<List<XSSFCell>>{
	public int compare( List<XSSFCell> l1, List<XSSFCell> l2 ){
		return l1.get(0).getStringCellValue().compareTo( l2.get(0).getStringCellValue() )
	}

	public  static WebElement nxtBtn
	/**
	 * This function reads input excels and assigns global variables to each query...
	 * @param sheetData
	 * @param dr
	 */

	@Keyword
	public  void readInput(String input_file) {

		Path file_input = Utils.getQueryFilePath(input_file);
		if ( file_input !=null) {
			KeywordUtil.markPassed("Test case file loaded " + "This is the full filepath after converting to string :"+file_input.toString())
			GlobalVariable.InputExcel=file_input.toString()
		}
		else{
			KeywordUtil.markPassed ("Password File is not found" )
		}


		KeywordUtil.logInfo("Global variable set for file is :  " + GlobalVariable.InputExcel )
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
		System.out.println ( "Row count is  : " + countrow);
		countcol = sheet.getRow(0).getLastCellNum();
		System.out.println("Col count is : " + countcol);

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

		KeywordUtil.markPassed("Data loaded from input file for the test case. " )
		driver = CustomBrowserDriver.createWebDriver();
		TestRunner.driver = driver;
		System.out.println("This is the driver from ICDC case details keyword : "+driver)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		excelparsing(sheetData_K,driver);
		System.out.println("This is the value of sheetdata array from create driver function : "+sheetData_K)
	}

	@Keyword
	private void excelparsing(List<List<XSSFCell>> sheetData, WebDriver dr) {

		System.out.println("This is the value of browser driver from excelparsing function: " + dr)
		System.out.println("This is urlname: " + GlobalVariable.G_Urlname)

		dr.get(GlobalVariable.G_Urlname)
		dr.manage().window().maximize()
		System.out.println("The window is maximized")
		Thread.sleep(3000)

		int countrow = sheetData.size()
		System.out.println("Row count from initializing fnc: " + countrow)
		System.out.println("Sheet data size: " + sheetData.get(0).size())

		for (int i = 1; i < countrow; i++) {

			List<XSSFCell> datarow = sheetData.get(i)
			System.out.println("Columns size from initializing fnc: " + datarow.size())

			for (int j = 0; j < datarow.size(); j++) {

				System.out.println("Value of i: " + i + " Value of j: " + j)

				XSSFCell cell = datarow.get(j)
				cell.setCellType(Cell.CELL_TYPE_STRING)

				String columnName = sheetData.get(0).get(j).getStringCellValue().trim()
				String cellValue = cell.getStringCellValue().trim()

				switch(columnName) {

					case "facetFilters":
						GlobalVariable.G_FacetFilters = cellValue
						System.out.println("Facet filters from input excel: " + GlobalVariable.G_FacetFilters)
						break

					case "TabName":
						GlobalVariable.G_inputTabName = cellValue
						System.out.println("This is the tab name from input excel: " + GlobalVariable.G_inputTabName)
						break

					case "buttonType":
						GlobalVariable.G_ButtonType = cellValue
						System.out.println("This is the button type from input excel: " + GlobalVariable.G_ButtonType)
						break

					case "totalFilesQuery":
						GlobalVariable.G_TotalFilesQuery = cellValue
						System.out.println("This is the total files query from input excel: " + GlobalVariable.G_TotalFilesQuery)
						break

					case "fileCartQuery":
						GlobalVariable.G_cartQuery = cellValue
						System.out.println("This is the file cart query from input excel: " + GlobalVariable.G_cartQuery)
						break

					case "TsvExcel":
						GlobalVariable.G_dbexcel = cellValue

						Path dbfilepath = Paths.get(System.getProperty("user.dir"), "OutputFiles", GlobalVariable.G_dbexcel )

						GlobalVariable.G_ResultPath = dbfilepath.toString()

						System.out.println("This is the TSV Excel path: " + GlobalVariable.G_ResultPath)
						break

					case "WebExcel":
						GlobalVariable.G_WebExcel = cellValue
						GlobalVariable.G_OutputFileName = GlobalVariable.G_WebExcel

						Path outputDir = Paths.get(System.getProperty("user.dir"), "OutputFiles")
						GlobalVariable.G_OutputDir = outputDir.toString()

						Path filepath = Paths.get(System.getProperty("user.dir"), "OutputFiles", GlobalVariable.G_WebExcel)

						GlobalVariable.G_WebExcel = filepath.toString()

						System.out.println("This is the Web Excel path: " + GlobalVariable.G_WebExcel)
						break

					default:
						System.out.println("Skipping column: " + columnName)
						break
				}
			}
		}
	}


	// Choose and click the Add Files button for the active result tab.
	@Keyword
	def clickAddFilesButton(String tabName, String buttonType) {

		String buttonXpath = ""

		switch(tabName.trim().toLowerCase()) {

			case "cases":
			case "casestab":

				buttonXpath = buttonType.equalsIgnoreCase("All") ?
				"//button[contains(.,'Add Files for All Cases')]" :
				"//button[contains(.,'Add Files for Selected Cases')]"
				break

			case "samples":
			case "samplestab":

				buttonXpath = buttonType.equalsIgnoreCase("All") ?
				"//button[contains(.,'Add Files for All Samples')]" :
				"//button[contains(.,'Add Files for Selected Sample')]"
				break

			case "case files":
			case "casefilestab":

				buttonXpath = buttonType.equalsIgnoreCase("All") ?
				"//button[contains(.,'Add All Files')]" :
				"//button[contains(.,'Add Selected Files')]"
				break

			case "study files":
			case "studyfilestab":

				buttonXpath = buttonType.equalsIgnoreCase("All") ?
				"//button[contains(.,'Add All Files')]" :
				"//button[contains(.,'Add Selected Files')]"
				break

			default:
				KeywordUtil.markFailed("Invalid tab name: " + tabName)
		}

		TestObject addFilesButton = new TestObject("addFilesButton")

		addFilesButton.addProperty(
				"xpath",
				ConditionType.EQUALS,
				buttonXpath
				)

		WebUI.scrollToPosition(0, 300)

		WebUI.waitForElementClickable(addFilesButton, 30)

		WebUI.click(addFilesButton)

		System.out.println("Clicked button: " + buttonXpath)

		// Handle popup only for ALL buttons
		if(buttonType.equalsIgnoreCase("All")) {

			TestObject yesButton = new TestObject("yesButton")

			yesButton.addProperty(
					"xpath",
					ConditionType.EQUALS,
					"//button[normalize-space()='Yes']"
					)

			WebUI.waitForElementClickable(yesButton, 20)

			WebUI.click(yesButton)

			System.out.println("Clicked YES on confirmation popup")
		}
	}


	@Keyword
	def TotalFilesInCart() {

		TestObject cartCountObject = findTestObject('Canine/fileCentricCart/Canine_totalCount')

		WebUI.waitForElementVisible(cartCountObject, 20)

		String rawCount = WebUI.getText(cartCountObject)?.trim()

		WebUI.comment("Raw My Files cart count text is: " + rawCount)

		String fileCount = rawCount?.replaceAll("[^0-9]", "")

		if (!fileCount) {
			KeywordUtil.markFailedAndStop(
					"Could not read My Files cart count"
					)
		}

		GlobalVariable.G_myCartTotal = fileCount

		WebUI.comment("My Files cart count is: " + fileCount)

		return fileCount
	}




	@Keyword
	public void multiFunctionFileCart(
			String tbl,
			String tblHdr,
			String nxtBtn,
			String webdataFileName,
			String tsvDataFileName,
			String fileCartQuery,
			String totalFilesQuery
	) throws IOException {

		System.out.println("Inside multiFunctionFileCart")

		String uiCartCount = TotalFilesInCart()
		System.out.println("UI File Cart count is: " + uiCartCount)

		int cartCount = convStringtoInt(uiCartCount)

		if (cartCount != 0) {

			// Set queries for Python scripts
			GlobalVariable.G_cartQuery = fileCartQuery
			GlobalVariable.G_StatQuery = totalFilesQuery

			// Set output Excel file paths because Data Binding does not populate GlobalVariables
			GlobalVariable.G_WebExcel = Paths.get(
					System.getProperty("user.dir"),
					"OutputFiles",
					webdataFileName
					).toString()

			GlobalVariable.G_ResultPath = Paths.get(
					System.getProperty("user.dir"),
					"OutputFiles",
					tsvDataFileName
					).toString()

			GlobalVariable.G_OutputFileName = webdataFileName
			GlobalVariable.G_dbexcel = tsvDataFileName
			GlobalVariable.G_OutputDir = Paths.get(
					System.getProperty("user.dir"),
					"OutputFiles"
					).toString()

			// Sheet names inside Excel files
			String uiSheetName = "CartWebData"
			String tsvSheetName = "TsvDataFileCart"


			System.out.println("UI Excel file path: " + GlobalVariable.G_WebExcel)
			System.out.println("TSV Excel file path: " + GlobalVariable.G_ResultPath)
			System.out.println("UI sheet name used for writeToExcel: " + uiSheetName)
			System.out.println("TSV sheet name used for Python ResultTabs: " + tsvSheetName)
			System.out.println("File Cart query: " + GlobalVariable.G_cartQuery)
			System.out.println("Total Files query: " + GlobalVariable.G_StatQuery)

			Utils.RESULT_TAB_NAME = tsvSheetName

			// Read UI File Cart table and write to UI Excel
			readFileCartTable(tbl, tblHdr, nxtBtn, uiSheetName)

			System.out.println("After reading File Cart table from UI")

			writeCurrentRowExcelForPython(
					fileCartQuery,
					totalFilesQuery,
					tsvDataFileName,
					webdataFileName
					)

			// Run File Cart TSV query
			PythonReader.readFile('ResultTabs.py')

			System.out.println("After running File Cart TSV query")

			// Compare UI Excel sheet with TSV Excel sheet (map UI columns to TSV order)
			compareFileCartSheets(uiSheetName, tsvSheetName)

			System.out.println("After comparing File Cart UI and TSV data")

			// Run totalFilesQuery for count validation
			PythonReader.readFile('Statbar.py')

			System.out.println("After running totalFilesQuery")

			// Validate UI cart count vs TSV count
			validateFileCartCount()
		} else {
			System.out.println("Skipping File Cart validation because cart count is 0")
		}

		System.out.println("File Cart validation completed")
	}


	@Keyword
	public static void readFileCartTable(
			String tbl1,
			String hdr1,
			String nxtb1,
			String webSheetName
	) throws IOException {

		WebDriverWait wait = new WebDriverWait(driver, 30)

		List<String> wTableHdrData = new ArrayList<String>()
		List<String> wTableBodyData = new ArrayList<String>()

		String tbl_main = givexpath(tbl1)
		String tbl_bdy = tbl_main + "/tbody"

		System.out.println("File Cart table xpath: " + tbl_main)
		System.out.println("File Cart table body xpath: " + tbl_bdy)

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tbl_bdy)))

		String hdr_str = givexpath(hdr1)
		String newHdr = hdr_str + "/tr"

		WebElement tableHdr = driver.findElement(By.xpath(newHdr))
		List<WebElement> colHeader = tableHdr.findElements(By.tagName("th"))

		int columns_count = colHeader.size()

		// Same th indices for header row and every data row (skip Access / Clear Cart once)
		List<Integer> includedColumnIndices = new ArrayList<>()
		String hdrdata = ""

		for (int j = 0; j < columns_count; j++) {

			String headerText = colHeader.get(j).getAttribute("innerText").trim()

			if (headerText &&
					!headerText.equalsIgnoreCase("Access") &&
					!headerText.equalsIgnoreCase("Clear Cart")) {

				includedColumnIndices.add(j)
				hdrdata = hdrdata + headerText + "||"
			}
		}

		wTableHdrData.add(hdrdata)

		System.out.println("File Cart headers: " + wTableHdrData)
		System.out.println("File Cart included column indices (th): " + includedColumnIndices)

		// Drop stale UI output so compare reads the latest scrape (not an old CartWebData sheet)
		File webOutputFile = new File(GlobalVariable.G_WebExcel.toString())
		if (webOutputFile.exists()) {
			webOutputFile.delete()
			System.out.println("Removed previous UI output file: " + webOutputFile.getAbsolutePath())
		}

		int counter = 1

		while (counter <= 9) {

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tbl_bdy)))

			WebElement tableBody = driver.findElement(By.xpath(tbl_bdy))
			List<WebElement> rows_table = tableBody.findElements(By.tagName("tr"))

			int rows_count = rows_table.size()

			System.out.println("File Cart rows count on page " + counter + ": " + rows_count)

			for (int i = 1; i <= rows_count; i++) {

				String data = ""

				for (int j : includedColumnIndices) {

					String headerText = colHeader.get(j).getAttribute("innerText").trim()
					System.out.println("This is the name of column header: " + headerText)



					if((colHeader.get(j).getAttribute("innerText"))!="Access") {
						//						System.out.println("This is the name of column header: "+colHeader.get(j).getAttribute("innerText"))
						//						data = data + ( (driver.findElement(By.xpath(tbl_bdy +"/tr[" + i + "]/td[" + (j+2) +"]")).getAttribute("innerText").trim()) +"||")//(j+1)
						//						System.out.println("This is the data after filtering for dog icon :"+data)
						//					}
						String cellText = driver.findElement(
								By.xpath(tbl_bdy + "/tr[" + i + "]/td[" + (j + 2) + "]")
								).getAttribute("innerText").trim().replaceAll("\\s+", " ")

						data = data + cellText + "||"

						System.out.println("This is the data after filtering Access column: " + data)
					}

					System.out.println("File Cart row data: " + data)
				}
				wTableBodyData.add(data)
			}

			String nxt_str = givexpath(nxtb1)
			WebElement nextButton = driver.findElement(By.xpath(nxt_str))

			if (nextButton.getAttribute("disabled")) {
				break
			} else {
				System.out.println("Collected File Cart data from page: " + counter)
				clickElement(nextButton)
				counter++
				Thread.sleep(2000)
			}
		}

		GlobalVariable.G_CaseData = wTableHdrData + wTableBodyData

		System.out.println("File Cart data stored in G_CaseData: " + GlobalVariable.G_CaseData)

		System.out.println("Before writeToExcel")
		System.out.println("webSheetName: " + webSheetName)
		System.out.println("GlobalVariable.G_WebExcel: " + GlobalVariable.G_WebExcel)
		System.out.println("GlobalVariable.G_OutputFileName: " + GlobalVariable.G_OutputFileName)
		System.out.println("GlobalVariable.G_OutputDir: " + GlobalVariable.G_OutputDir)

		Utils.writeToExcel(webSheetName)

		System.out.println("After writeToExcel")

		System.out.println("File Cart web data written to Excel successfully")
	}


	@Keyword
	public static void compareFileCartSheets(String webSheetName, String tsvSheetName) {

		String uiFilename = GlobalVariable.G_WebExcel.toString()
		String tsvFilename = GlobalVariable.G_ResultPath.toString()

		List<String> tsvColumnOrder = [
			"File Name",
			"File Type",
			"Association",
			"Description",
			"Format",
			"Size",
			"Study Code",
			"Case ID"
		]

		System.out.println("Comparing File Cart sheets with column mapping")
		System.out.println("UI file: " + uiFilename)
		System.out.println("TSV file: " + tsvFilename)

		Map<String, Integer> uiHeaderIndex = readSheetHeaderIndex(uiFilename, webSheetName)
		Map<String, Integer> tsvHeaderIndex = readSheetHeaderIndex(tsvFilename, tsvSheetName)

		List<List<String>> uiAligned = mapRowsByHeaders(uiFilename, webSheetName, uiHeaderIndex, tsvColumnOrder)
		List<List<String>> tsvAligned = mapRowsByHeaders(tsvFilename, tsvSheetName, tsvHeaderIndex, tsvColumnOrder)

		Collections.sort(uiAligned, { a, b -> a.get(0).compareTo(b.get(0)) })
		Collections.sort(tsvAligned, { a, b -> a.get(0).compareTo(b.get(0)) })

		System.out.println("UI aligned row count: " + uiAligned.size())
		System.out.println("TSV aligned row count: " + tsvAligned.size())

		Utils.compareTwoLists(uiAligned, tsvAligned)
	}


	private static Map<String, Integer> readSheetHeaderIndex(String filename, String sheetName) {

		Map<String, Integer> headerIndex = new HashMap<>()
		FileInputStream fis = new FileInputStream(filename)
		XSSFWorkbook workbook = new XSSFWorkbook(fis)
		XSSFSheet sheet = workbook.getSheet(sheetName)
		XSSFRow headerRow = sheet.getRow(0)

		for (int c = 0; c < headerRow.getLastCellNum(); c++) {
			String header = getCellStringValue(headerRow.getCell(c))
			if (header) {
				headerIndex.put(header, c)
			}
		}

		workbook.close()
		fis.close()

		return headerIndex
	}


	private static List<List<String>> mapRowsByHeaders(
			String filename,
			String sheetName,
			Map<String, Integer> headerIndex,
			List<String> columnOrder
	) {

		List<List<String>> alignedRows = new ArrayList<>()
		FileInputStream fis = new FileInputStream(filename)
		XSSFWorkbook workbook = new XSSFWorkbook(fis)
		XSSFSheet sheet = workbook.getSheet(sheetName)

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			XSSFRow row = sheet.getRow(r)
			if (row == null) {
				continue
			}

			List<String> alignedRow = new ArrayList<>()

			for (String columnName : columnOrder) {
				Integer colIndex = headerIndex.get(columnName)
				String value = colIndex != null ? getCellStringValue(row.getCell(colIndex)) : ""

				if (columnName.equalsIgnoreCase("Size")) {
					value = normalizeFileCartSize(value)
				}

				alignedRow.add(value)
			}

			alignedRows.add(alignedRow)
		}

		workbook.close()
		fis.close()

		return alignedRows
	}


	private static String getCellStringValue(XSSFCell cell) {

		if (cell == null) {
			return ""
		}

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue()?.trim() ?: ""
			case Cell.CELL_TYPE_NUMERIC:
				double num = cell.getNumericCellValue()
				if (num == Math.floor(num)) {
					return String.valueOf((long) num)
				}
				return String.valueOf(num)
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue())
			case Cell.CELL_TYPE_FORMULA:
				cell.setCellType(Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue()?.trim() ?: ""
			default:
				return ""
		}
	}


	private static String normalizeFileCartSize(String value) {

		if (!value || value.trim().isEmpty()) {
			return ""
		}

		String trimmed = value.trim()

		if (trimmed ==~ /[\d.]+/) {
			try {
				double bytes = Double.parseDouble(trimmed)
				if (bytes >= 1e9) {
					return String.format("%.2f GB", bytes / 1e9)
				}
				if (bytes >= 1e6) {
					return String.format("%.2f MB", bytes / 1e6)
				}
				if (bytes >= 1e3) {
					return String.format("%.2f KB", bytes / 1e3)
				}
				return String.format("%.0f B", bytes)
			} catch (NumberFormatException ignored) {
				return trimmed
			}
		}

		return trimmed
	}


	@Keyword
	public static void validateFileCartCount() {

		List<List<XSSFCell>> statData = new ArrayList<>()

		String tsvFileName = GlobalVariable.G_ResultPath.toString()

		statData = ReadExcel.readExceltoWeblist(
				tsvFileName,
				GlobalVariable.G_StatTabname
				)

		String tsvCartCount = statData.get(0).get(2).getStringCellValue().trim()

		System.out.println("This is File Cart count from TSV result: " + tsvCartCount)
		System.out.println("This is File Cart count from UI: " + GlobalVariable.G_myCartTotal)

		if (tsvCartCount.contentEquals(GlobalVariable.G_myCartTotal)) {
			KeywordUtil.markPassed("File Cart count matches")
		} else {
			KeywordUtil.markFailed(
					"Mismatch in File Cart count. UI: "
					+ GlobalVariable.G_myCartTotal
					+ " TSV: "
					+ tsvCartCount
					)
		}
	}


	@Keyword
	def applyFacetFilters(String facetFilters) {

		if (!facetFilters || facetFilters.trim().equalsIgnoreCase("NA")) {
			System.out.println("No facet filters provided")
			return
		}

		String[] filters = facetFilters.split("\\r?\\n")

		for (String filter : filters) {

			if (!filter?.trim()) {
				continue
			}

			String[] parts = filter.split("=")

			if (parts.length != 2) {
				KeywordUtil.markFailedAndStop("Invalid facet filter format: " + filter)
			}

			String facetName = parts[0].trim()
			String facetValue = parts[1].trim()

			System.out.println("Applying facet filter: " + facetName + " = " + facetValue)

			switch(facetName.toLowerCase()) {

				case "study":
					TestRunner.clickTab('Canine/Filter/Study/Canine_Filter_Study')
					clickFacetValue("Study", facetValue)
					break

				case "breed":
					TestRunner.clickTab('Canine/Filter/Breed/BREED_Ddn')
					clickFacetValue("Breed", facetValue)
					break

				case "sex":
					TestRunner.clickTab('Canine/Filter/Sex/Canine_Filter_Sex')
					clickFacetValue("Sex", facetValue)
					break

				default:
					KeywordUtil.markFailedAndStop("Unsupported facet filter: " + facetName)
			}

			WebUI.delay(1)
		}
	}


	def clickFacetValue(String facetName, String facetValue) {

		String checkboxId = "checkbox_" + facetName + "_" + facetValue.trim()

		TestObject checkbox = new TestObject("checkbox_" + facetName + "_" + facetValue)

		checkbox.addProperty(
				"xpath",
				ConditionType.EQUALS,
				"//input[@id='" + checkboxId + "']"
				)

		WebUI.waitForElementPresent(checkbox, 20)

		WebElement checkboxElement = WebUI.findWebElement(checkbox, 20)

		WebUI.executeJavaScript(
				"arguments[0].scrollIntoView({block:'center'});",
				Arrays.asList(checkboxElement)
				)

		WebUI.executeJavaScript(
				"arguments[0].click();",
				Arrays.asList(checkboxElement)
				)

		System.out.println("Clicked facet value: " + checkboxId)
	}

	@Keyword
	def setInputExcelForPython(String inputFileName) {

		GlobalVariable.InputExcel = Utils.getQueryFilePath(inputFileName).toString()

		System.out.println("Input Excel for Python is: " + GlobalVariable.InputExcel)
	}

	@Keyword
	def writeCurrentRowExcelForPython(
			String fileCartQuery,
			String totalFilesQuery,
			String tsvExcel,
			String webExcel,
			String tabName = 'CasesTab',
			String buttonType = 'All',
			String facetFilters = 'NA'
	) {
		Utils.createDirctory("OutputFiles")

		Path tempFile = Paths.get(
				System.getProperty("user.dir"),
				"OutputFiles",
				"TC02_ICDC_Cart_current_row.xlsx"
				)

		XSSFWorkbook workbook = new XSSFWorkbook()
		XSSFSheet sheet = workbook.createSheet("Sheet1")

		List<String> headers = [
			"facetFilters",
			"TabName",
			"buttonType",
			"totalFilesQuery",
			"fileCartQuery",
			"TsvExcel",
			"WebExcel"
		]

		Row headerRow = sheet.createRow(0)
		for (int i = 0; i < headers.size(); i++) {
			headerRow.createCell(i).setCellValue(headers.get(i))
		}

		List<String> values = [
			facetFilters,
			tabName,
			buttonType,
			totalFilesQuery,
			fileCartQuery,
			tsvExcel,
			webExcel
		]

		Row dataRow = sheet.createRow(1)
		for (int i = 0; i < values.size(); i++) {
			dataRow.createCell(i).setCellValue(values.get(i))
		}

		FileOutputStream fos = new FileOutputStream(tempFile.toFile())
		workbook.write(fos)
		fos.close()
		workbook.close()

		GlobalVariable.InputExcel = tempFile.toString()
		System.out.println("Current row Excel for Python is: " + GlobalVariable.InputExcel)
	}
	
	
@Keyword
def shouldRunTest(String runTest) {

    if (runTest == null || runTest.trim().isEmpty()) {
        KeywordUtil.markFailedAndStop(
            "RunTest value is empty. Expected Yes or No."
        )
        return false
    }

    if (runTest.trim().equalsIgnoreCase("Yes")) {
        KeywordUtil.logInfo("RunTest = Yes. Test will be executed.")
        return true
    }

    if (runTest.trim().equalsIgnoreCase("No")) {
        KeywordUtil.logInfo("RunTest = No. Test will be skipped.")
        return false
    }

    KeywordUtil.markFailedAndStop(
        "Invalid RunTest value: '" + runTest + "'. Expected Yes or No."
    )

    return false
}


}//class ends