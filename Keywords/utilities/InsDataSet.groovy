package utilities
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
import com.kms.katalon.core.configuration.RunConfiguration
import org.apache.poi.ss.usermodel.*

import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import java.awt.Color

class InsDataSet extends TestRunner {

	public static WebDriver driver

	public String title;
	public String primaryDisease;
	public String participantCount;
	public String sampleCount;

	public DatasetRow(String title, String primaryDisease, String participantCount, String sampleCount) {
		this.title = title;
		this.primaryDisease = primaryDisease;
		this.participantCount = participantCount;
		this.sampleCount = sampleCount;
	}


	public void extractAllDatasets() {

		PythonReader.readFile("datasets.py");
		List<List<String>> uiDataRows = new ArrayList<>();
		driver = DriverFactory.getWebDriver();

		// Add header
		uiDataRows.add(List.of("Title", "dbGaP Accession", "Primary Disease", "Participant Count", "Sample Count", "Description"));

		while (true) {
			List<WebElement> cards = driver.findElements(By.xpath("//*[@class='container']")); // Adjust as needed

			for (WebElement card : cards) {
				String title = safeText(card, ".//a[starts-with(@href, '#/dataset/phs')]");
				String dbGapId = safeText(card, ".//a[starts-with(@href, 'https://www.ncbi.nlm')]");
				String primaryDisease = safeText(card, ".//*[@class='itemSpan']");
				String participantCount = safeText(card, ".//*[@class='textSpan caseCountHighlight']");
				String sampleCount = safeText(card, ".//*[@class='textSpan sampleCountHighlight']");
				String description = safeText(card, ".//*[@class='textSpan']");

				System.out.println("Title is: "+ title);
				System.out.println("dbGap Accession is: "+ dbGapId);
				System.out.println("Primary Disease: "+ primaryDisease);
				System.out.println("Participant Count: "+ participantCount);
				System.out.println("Sample Count: "+ sampleCount);
				System.out.println("Description is: "+ description);
				uiDataRows.add(List.of(title, dbGapId, primaryDisease, participantCount, sampleCount, description));
			}

			// Try clicking "Next"
			try {
				//
				WebElement next = driver.findElement(By.xpath("//*[contains(@class, 'bspage-link-next')]"));
				if (next.getAttribute("class") != null && next.getAttribute("class").contains("disabled")) {
					break;
				}
				next.click();
				Thread.sleep(1500); // wait for content to load
			} catch (NoSuchElementException e) {
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		writeDataToExcel(uiDataRows);
	}


	private String safeText(WebElement parent, String xpath) {
		try {
			return parent.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			return "";
		}
	}



	public void compareUIvsTsv() {
		TestRunner.compareSheets("WebDatasets","TsvDatasets");
	}




	private void writeDataToExcel(List<List<String>> data) {

		String outputPath = RunConfiguration.getProjectDir() + "/OutputFiles/TC01_INS_DataSets-EntirePage_WebData.xlsx"

		Workbook workbook = new XSSFWorkbook()
		Sheet sheet = workbook.createSheet("WebDatasets")

		for (int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(i)
			List<String> rowData = data[i]
			for (int j = 0; j < rowData.size(); j++) {
				row.createCell(j).setCellValue(rowData[j])
			}
		}

		FileOutputStream out = null
		try {
			out = new FileOutputStream(outputPath)
			workbook.write(out)
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			if (out != null) out.close()
			if (workbook != null) workbook.close()
		}
	}
}//class ends