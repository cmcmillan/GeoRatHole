import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.FileOutputStream;
/**
 * This is a sample to create an 
 * Excel Sheet using Jakarta POI API
 * @author  Elango Sundaram
 * @version 1.0
 */
public class CreateXL {
    /** A place for the output Excel file to go */
    public static String outputFile="D:/JTest/JPOI/excel_in_java.xls";
    public static void main(String argv[]){
	   try{
            // Create a New XL Document
            HSSFWorkbook wb = new HSSFWorkbook();
            // Make a worksheet in the XL document created
            HSSFSheet sheet = wb.createSheet();
            // Create row at index zero ( Top Row)
            HSSFRow row = sheet.createRow((short)0);
            // Create a cell at index zero ( Top Left)
            HSSFCell cell = row.createCell((short) 0);
            // Lets make the cell a string type
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            // Type some content
            cell.setCellValue("Have a Cup of XL");
            // The Output file is where the xls will be created
            FileOutputStream fOut = new FileOutputStream(outputFile);
            // Write the XL sheet
            wb.write(fOut);
            fOut.flush();
            // Done Deal..
            fOut.close();
            System.out.println("File Created ..");
            
        }catch(Exception e) {
            System.out.println("!!BANG!! xlCreate() : " + e );
        }
        
    }
    
}
