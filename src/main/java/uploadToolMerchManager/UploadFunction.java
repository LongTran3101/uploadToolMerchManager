package uploadToolMerchManager;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UploadFunction {
	static WebDriver driver = new ChromeDriver();
	static void upload( ) {
		try {
			List<uploadFile> lstFile=ReadExcel();
			Upload.btnNewButton.setEnabled(false);
			WebDriverManager.chromedriver().setup();
		
			driver.get("http://45.32.101.196:8080/");
			driver.findElement(By.xpath("//*[@id=\"container\"]/div[2]/form/input[1]")).sendKeys(Upload.user.getText().trim());
			driver.findElement(By.xpath("//*[@id=\"container\"]/div[2]/form/input[2]")).sendKeys(Upload.pass.getText().trim());
			driver.findElement(By.xpath("//*[@id=\"container\"]/div[2]/form/button")).click();
			Thread.sleep(5000);
			if(lstFile.isEmpty())
			{
				Upload.textArea.setText("Không có dữ liệu file excel");
			}else {
				for (int i = 0; i < lstFile.size(); i++) {
					driver.get("http://45.32.101.196:8080/user/uploadMultifileExcel");
					Thread.sleep(3000);
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/form/div[2]/div[1]/div[4]/div/input")));
					driver.findElement(By.xpath("/html/body/div[3]/form/div[2]/div[1]/div[4]/div/input")).sendKeys(lstFile.get(i).getPath());
					Thread.sleep(1000);
					driver.findElement(By.xpath("/html/body/div[2]/form/div/div/div[2]/button")).click();
					Thread.sleep(10000);
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notification")));
					Thread.sleep(1000);
				}
			}
			
			Thread.sleep(10000);
			driver.close();
			Upload.btnNewButton.setEnabled(true);
		} catch (Exception e) {
			driver.close();
			e.printStackTrace();
		}
	}
	
	
	static List<uploadFile> ReadExcel()
	{
		   List<uploadFile> lst=new ArrayList<uploadFile>();
		try {
			FileInputStream fileStream=new FileInputStream(Upload.file);
			 XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
			    XSSFSheet worksheet = workbook.getSheetAt(0);
			 
			    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			    	try {
			    		uploadFile upload = new uploadFile();
				        XSSFRow row = worksheet.getRow(i);;
						upload.setPath(row.getCell(0).getStringCellValue());
						
						lst.add(upload);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
			    	
			    }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lst;
	
	}
	public static String getDataCell(Cell cell)
	{
		String value = "";
		try {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				  value = String.valueOf(cell.getNumericCellValue());
			} else {
				      value = cell.getStringCellValue();
			}
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		return value;
		
		
	
	}
}
