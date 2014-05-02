package RPAdminSystem;

/**
 *
 * @author eujing
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class ResourcesManager {
    public ArrayList<RIERecord> readRIERecords (File inFile) throws IOException {
        final ArrayList<RIERecord> records = new ArrayList<> ();
        
        if (inFile.getName().endsWith(".csv")) {
            CSVParser parser = new CSVParser(new FileReader(inFile), CSVFormat.RFC4180.withDelimiter(';'));
            for (CSVRecord csvRecord : parser.getRecords()) {
                Object[] array = new Object[csvRecord.size()];
                for (int i = 0; i < array.length; i++) {
                    array[i] = csvRecord.get(i);
                }
                records.add(RIERecord.fromArray(array));
            }
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
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException ("Invalid year");
        }
    }

    public void writeRIERecords (final ArrayList<RIERecord> records, File outFile) throws IOException {
        if (outFile.getName().endsWith(".csv")) {
            try (FileWriter out = new FileWriter(outFile); CSVPrinter printer = new CSVPrinter (out, CSVFormat.RFC4180.withDelimiter(';'))) {
                for (RIERecord r : records) {
                    printer.printRecord(r.toArray());
                }
            }
        }
        else  {
            if (!outFile.getName().endsWith(".xls")) {
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
    }
    
    public ArrayList<Student> readStudents (File inFile) throws IOException {
        final ArrayList<Student> students = new ArrayList<> ();
        
        if (inFile.getName().endsWith("csv")) {
            CSVParser parser = new CSVParser(new FileReader(inFile), CSVFormat.RFC4180.withDelimiter(';'));
            for (CSVRecord csvRecord : parser.getRecords()) {
                Object[] array = new Object[csvRecord.size()];
                for (int i = 0; i < array.length; i++) {
                    array[i] = csvRecord.get(i);
                }
                students.add(Student.fromArray(array));
            }
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
            try (FileWriter out = new FileWriter(outFile); CSVPrinter printer = new CSVPrinter (out, CSVFormat.RFC4180.withDelimiter(';'))) {
                for (Student s : students) {
                    printer.printRecord(s.toArray());
                }
            }
        }
        else {
            if (!outFile.getName().endsWith(".xls")) {
                outFile = new File (outFile.getName() + ".xls");
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
}
