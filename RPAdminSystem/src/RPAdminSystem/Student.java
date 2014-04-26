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
public class Student {
    String userid;
    String name;
    
    public static final String[] columnNames = new String[] {"UserID", "Name"};
    
    public Student (String userid, String name) {
        this.userid = userid;
        this.name = name;
    }
    
    public Object[] toArray () {
        return new Object[] {
            (Object) userid,
            (Object) name
        };
    }
    
    public static Student fromArray (Object[] array) {
        if (array.length != 2) throw new IllegalArgumentException ("Wrong sized array");
        return new Student ((String) array[0], (String) array[1]);
    }
    
    public String getUserid () {
        return userid;
    }
    
    public void setUserid (String userid) {
        this.userid = userid;
    }
    
    public String getName () {
        return name;
    }
    
    public void setname (String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Student)) {
            return false;
        }
        
        Student s = (Student) o;
        return userid.equals(s.userid) && name.equals(s.name);
    }
}
