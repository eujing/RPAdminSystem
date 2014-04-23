/**
 *
 * @author eujing
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RecordsManager {
    public ArrayList<Record> readRIERecords () throws IOException {
        ArrayList<Record> records = new ArrayList<> ();
        
        try {
            Workbook wb = Workbook.getWorkbook(new File ("RIE_records.xls"));
            Sheet sheet = wb.getSheet(0);
            for (int i = 1; i < sheet.getRows(); i++) {
                Cell[] c = sheet.getRow(i);
                String userid = c[0].getContents();
                String category = c[1].getContents();
                String title = c[2].getContents();
                String desc1 = c[3].getContents();
                String desc2 = c[4].getContents();
                String award = c[5].getContents();
                int year = Integer.parseInt(c[6].getContents());
                records.add(new Record(userid, category, title, desc1, desc2, award, year, ""));
            }
        }
        catch (BiffException ex) {
            ex.printStackTrace();
        }
        
        return records;
    }
    
    public void writeRIERecords (ArrayList<Record> records) throws IOException {
        WorkbookSettings settings = new WorkbookSettings ();
        WritableWorkbook wb = Workbook.createWorkbook(new File ("RIE_records.xls"));
        wb.createSheet("RIE Records", 0);
        WritableSheet sheet = wb.getSheet(0);
        
        for (int i = 0; i < records.size(); i++) {
            
        }
    }
}
