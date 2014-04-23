/**
 *
 * @author eujing
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ResourcesManager {
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
                String grade = "";
                if (c.length >= 8) {
                    grade = c[7].getContents();
                }
                records.add(new Record(userid, category, title, desc1, desc2, award, year, grade));
            }
        }
        catch (BiffException ex) {
            ex.printStackTrace();
        }
        
        return records;
    }
    
    public void writeRIERecords (ArrayList<Record> records) throws IOException {
        WritableWorkbook wb = Workbook.createWorkbook(new File ("RIE_records.xls"));
        wb.createSheet("RIE Records", 0);
        WritableSheet sheet = wb.getSheet(0);
        
        try {
            for (int col = 0; col < Record.columnNames.length; col++) {
                sheet.addCell(new Label (col, 0, Record.columnNames[col]));
            }
            for (int i = 0; i < records.size(); i++) {
                Object[] array = records.get(i).toArray();
                for (int j = 0; j < array.length; j++) {
                    sheet.addCell(new Label (j, i+1, array[j].toString()));
                }
            }
            
            wb.write();
            wb.close();
        }
        catch (WriteException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Student> readStudents () throws IOException {
        ArrayList<Student> students = new ArrayList<> ();
        
        try {
            Workbook wb = Workbook.getWorkbook(new File ("students.xls"));
            Sheet sheet = wb.getSheet(0);
            for (int i = 1; i < sheet.getRows(); i++) {
                Cell[] c = sheet.getRow(i);
                String userid = c[0].getContents();
                String name = c[1].getContents();
                students.add(new Student(userid, name));
            }
        }
        catch (BiffException ex) {
            ex.printStackTrace();
        }
        
        return students;
    }
    
    public void writeStudents (ArrayList<Student> students) throws IOException {
        WritableWorkbook wb = Workbook.createWorkbook(new File ("Non_submissions.xls"));
        wb.createSheet("Students", 0);
        WritableSheet sheet = wb.getSheet(0);
        
        try {
            for (int col = 0; col < Student.columnNames.length; col++) {
                sheet.addCell(new Label (col, 0, Student.columnNames[col]));
            }
            for (int i = 0; i < students.size(); i++) {
                Object[] array = students.get(i).toArray();
                for (int j = 0; j < array.length; j++) {
                    sheet.addCell(new Label (j, i+1, array[j].toString()));
                }
            }
            
            wb.write();
            wb.close();
        }
        catch (WriteException ex) {
            ex.printStackTrace();
        }
    }
}
