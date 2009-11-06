import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.FileInputStream;
/**
 * This is a sample to Read an  Excel Sheet using 
 * Jakarta POI API
 * @author  Elango Sundaram
 * @version 1.0
 */
public class ReadXL {
    /** Location where the Excel has to be read from. Note the forward Slash */
    public static String fileToBeRead="D:/JTest/JPOI/Read.xls";
    public static void main(String argv[]){       
        try{
				// Create a work book reference
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
				// Refer to the sheet. Put the Name of the sheet to be referred from
				// Alternative you can also refer the sheet by index using getSheetAt(int index)
				HSSFSheet sheet = workbook.getSheet("Sheet1");
				//Reading the TOP LEFT CELL
				HSSFRow row = sheet.getRow(0);
				// Create a cell ate index zero ( Top Left)
				HSSFCell cell = row.getCell((short)0);
				// Type the content
				System.out.println("THE TOP LEFT CELL--> " + cell.getStringCellValue());            
            
        }catch(Exception e) {
            System.out.println("!! Bang !! xlRead() : " + e );
        }
        
    }
    
}
