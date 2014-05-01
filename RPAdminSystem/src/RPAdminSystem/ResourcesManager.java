package RPAdminSystem;

/**
 *
 * @author eujing
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ResourcesManager {
    public ArrayList<RIERecord> readRIERecords (File inFile) throws IOException {
        final ArrayList<RIERecord> records = new ArrayList<> ();
        
        if (inFile.getName().endsWith(".csv")) {
            CSV csv = CSV.separator(';').quote('"').create();
            csv.read(inFile, new CSVReadProc() {
                @Override
                public void procRow (int rowIndex, String... values) {
                    records.add(RIERecord.fromArray(values));
                }
            });
        }
        else {
            try {
                Workbook wb = Workbook.getWorkbook(inFile);
                Sheet sheet = wb.getSheet(0);
                if (sheet.getColumns() != 7 && sheet.getColumns() != 8) {
                    throw new IllegalArgumentException ("Invalid RIE records file");
                }
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] c = sheet.getRow(i);
                    String userid = verifyUserid(c[0].getContents());
                    String category = c[1].getContents();
                    String title = c[2].getContents();
                    String desc1 = c[3].getContents();
                    String desc2 = c[4].getContents();
                    String award = c[5].getContents();
                    int year = verifyYear(c[6].getContents());
                    String grade = "";
                    if (c.length >= 8) {
                        grade = c[7].getContents();
                    }
                    records.add(new RIERecord(userid, category, title, desc1, desc2, award, year, grade));
                }
            }
            catch (BiffException ex) {
                ex.printStackTrace();
            }
        }
        
        return records;
    }
    
    public String verifyUserid (String userid) {
        if (userid.length() == 8 && userid.startsWith("h")) {
            return userid;
        }
        else {
            throw new IllegalArgumentException ("Invalid UserID");
        }
    }
    
    public int verifyYear (String year) {
        int value = 0;
        try {
            value = Integer.parseInt(year);
            if (value < 0 || value > 9999) {
                throw new IllegalArgumentException ("Invalid year");
            }
            return value;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException ("Invalid year");
        }
    }

    public void writeRIERecords (final ArrayList<RIERecord> records, File outFile) throws IOException {
        if (outFile.getName().endsWith(".csv")) {
            CSV csv = CSV.separator(';').quote('"').create();
            csv.write(outFile, new CSVWriteProc() {
                @Override
                public void process (CSVWriter out){
                    for (RIERecord r : records) {
                        Object[] array = r.toArray();
                        out.writeNext(Arrays.asList(array).toArray(new String[array.length]));
                    }
                }
            });
        }
        else if (!outFile.getName().endsWith(".xls")) {
            outFile = new File (outFile.getName() + ".xls");
        }
        
        WritableWorkbook wb = Workbook.createWorkbook(outFile);
        wb.createSheet("RIE Records", 0);
        WritableSheet sheet = wb.getSheet(0);
        
        try {
            for (int i = 0; i < records.size(); i++) {
                Object[] array = records.get(i).toArray();
                for (int j = 0; j < array.length; j++) {
                    sheet.addCell(new Label (j, i, array[j].toString()));
                }
            }
            
            wb.write();
            wb.close();
        }
        catch (WriteException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Student> readStudents (File inFile) throws IOException {
        final ArrayList<Student> students = new ArrayList<> ();
        
        if (inFile.getName().endsWith("csv")) {
            CSV csv = CSV.separator(';').quote('"').create();
            csv.read(inFile, new CSVReadProc() {
                @Override
                public void procRow (int rowIndex, String... values) {
                    students.add(Student.fromArray(values));
                }
            });
        }
        else {
            try {
                Workbook wb = Workbook.getWorkbook(inFile);
                Sheet sheet = wb.getSheet(0);
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] c = sheet.getRow(i);
                    String userid = verifyUserid(c[0].getContents());
                    String name = c[1].getContents();
                    students.add(new Student(userid, name));
                }
            }
            catch (BiffException ex) {
                ex.printStackTrace();
            }
        }
        
        return students;
    }
    
    public void writeStudents (final ArrayList<Student> students, File outFile) throws IOException {
        if (outFile.getName().endsWith(".csv")) {
            CSV csv = CSV.separator(';').quote('"').create();
            csv.write(outFile, new CSVWriteProc() {
                @Override
                public void process (CSVWriter out){
                    for (Student s : students) {
                        Object[] array = s.toArray();
                        out.writeNext(Arrays.asList(array).toArray(new String[array.length]));
                    }
                }
            });
        }
        
        WritableWorkbook wb = Workbook.createWorkbook(outFile);
        wb.createSheet("Students", 0);
        WritableSheet sheet = wb.getSheet(0);
        
        try {
            for (int i = 0; i < students.size(); i++) {
                Object[] array = students.get(i).toArray();
                for (int j = 0; j < array.length; j++) {
                    sheet.addCell(new Label (j, i, array[j].toString()));
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
