package RPAdminSystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eujing
 */
public class Record {
    String userid;
    String category;
    String title;
    String desc1;
    String desc2;
    String award;
    int year;
    String grade;
    
    public static final String[] columnNames = new String[] {"UserID", "Category", "Title", "Desc1", "Desc2", "Award", "Year", "Grade"};
    
    public Record (String userid, String category, String title, String desc1, String desc2, String award, int year, String grade) {
        this.userid = userid;
        this.category = category;
        this.title = title;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.award = award;
        this.year = year;
        this.grade = grade;
    }
    
    public static Record fromArray (Object[] array) {
        if (array.length != 8) throw new IllegalArgumentException ("Wrong sized array");
        return new Record (
                (String) array[0],
                (String) array[1],
                (String) array[2],
                (String) array[3],
                (String) array[4],
                (String) array[5],
                (Integer) array[6],
                (String) array[7]
        );
    }
    
    public Object[] toArray () {
        return new Object[] {
            (Object) userid,
        (Object) category,
        (Object) title,
        (Object) desc1,
        (Object) desc2,
        (Object) award,
        (Object) year,
        (Object) grade
        };
    }
    
    public String getUserid () {
        return this.userid;
    }
    
    public void setUserid (String userid) {
        this.userid = userid;
    }
    
    public String getCategory () {
        return this.category;
    }
    
    public void setCategory (String category) {
        this.category = category;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public void setTitle (String title) {
        this.title = title;
    }
    
    public String getDesc1 () {
        return this.desc1;
    }
    
    public void setDesc1 (String desc1) {
        this.desc1 = desc1;
    }
    
    public String getDesc2 () {
        return this.desc2;
    }
    
    public void setDesc2 (String desc2) {
        this.desc2 = desc2;
    }
    
    public String getAward () {
        return this.award;
    }
    
    public void setAward (String award) {
        this.award = award;
    }
    
    public int getYear () {
        return this.year;
    }
    
    public void setYear (int year) {
        this.year = year;
    }
    
    public String getGrade () {
        return this.grade;
    }
    
    public void setGrade (String grade) {
        this.grade = grade;
    }
    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Record)) {
            return false;
        }
        
        Record r = (Record) o;
        return userid.equals(r.userid) &&
                category.equals(r.category) &&
                title.equals(r.title) &&
                desc1.equals(r.desc1) &&
                desc2.equals(r.desc2) &&
                award.equals(r.award) &&
                year == r.year &&
                grade.equals(r.grade);
    }
}
