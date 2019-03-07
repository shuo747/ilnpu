package com.shuo747.ilnpu.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Student implements Serializable {

    @Id
    private Long sid;
    private String password;
    private String name;
    private String sex;
    private String idcard;
    private String college;
    private String major;
    private String sclass;
    private String sgrade;




    public Student() {
    }

    public Student(Long sid, String password) {
        this.sid = sid;
        this.password = password;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getSgrade() {
        return sgrade;
    }

    public void setSgrade(String sgrade) {
        this.sgrade = sgrade;
    }

    public Student(String[] info) {
        this.name = info [0] ;
        this.sex = info [1] ;
        this.idcard = info [2] ;
        this.college = info [3] ;
        this.major = info [4] ;
        this.sclass = info [5] ;
        this.sgrade = info [6] ;
    }

    public Student(long sid, String password, String[] info) {
        this.sid = sid;
        this.password = password;
        this.name = info [0];
        this.sex = info [1];
        this.idcard = info [2];
        this.college = info [3];
        this.major = info [4];
        this.sclass = info [5];
        this.sgrade = info [6];

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (sid != null ? !sid.equals(student.sid) : student.sid != null) return false;
        if (password != null ? !password.equals(student.password) : student.password != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (idcard != null ? !idcard.equals(student.idcard) : student.idcard != null) return false;
        if (college != null ? !college.equals(student.college) : student.college != null) return false;
        if (major != null ? !major.equals(student.major) : student.major != null) return false;
        if (sclass != null ? !sclass.equals(student.sclass) : student.sclass != null) return false;
        return sgrade != null ? sgrade.equals(student.sgrade) : student.sgrade == null;
    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (idcard != null ? idcard.hashCode() : 0);
        result = 31 * result + (college != null ? college.hashCode() : 0);
        result = 31 * result + (major != null ? major.hashCode() : 0);
        result = 31 * result + (sclass != null ? sclass.hashCode() : 0);
        result = 31 * result + (sgrade != null ? sgrade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", idcard='" + idcard + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", sclass='" + sclass + '\'' +
                ", sgrade='" + sgrade + '\'' +
                '}';
    }
}
