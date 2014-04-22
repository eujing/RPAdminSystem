/**
 *
 * @author eujing
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.*;
import jxl.read.biff.BiffException;

public class RecordsManager {
    public ArrayList<Record> getRIERecords () throws IOException {
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
                //TODO: Check for grade
                records.add(new Record(userid, category, title, desc1, desc2, award, year, ""));
            }
        }
        catch (BiffException ex) {
            ex.printStackTrace();
        }
        
        return records;
    }
    
    public static void main (String[] args) {
        RecordsManager rm = new RecordsManager ();
        try {
            for (Record r : rm.getRIERecords ()) {
                System.out.println(r.toString());
            }
        }
        catch (IOException ex) {
            System.out.println("File not found");
        }
    }
}
