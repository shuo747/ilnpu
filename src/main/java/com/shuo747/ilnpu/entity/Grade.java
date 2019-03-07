
package com.shuo747.ilnpu.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;


@Entity
@Table(name="grade")
public class Grade implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String semester;
    //private String college;
    //private String major;
    //private String sgrade;
    //private String sclass;
    private String cid;
    private String cname;
    private String cmode;
    private String school;
    private String credits;
    private Long sid;
    //private String sname;
    private String results;
    private String resultstype;
    private String examtype;

    public Grade() {
    }



    public Grade(String[] s) {
        this.semester = s[0];
        //this.college = s[1];
        //this.major = s[2];
        //this.sgrade = s[3];
        //this.sclass = s[4];
        this.cid = s[5];
        this.cname = s[6];
        this.cmode = s[7];
        this.school = s[8];
        this.credits = s[9];
        this.sid = Long.parseLong(s[10]);
        //this.sname = s[11];
        this.results = s[12];
        this.resultstype = s[13];
        this.examtype = s[14];

    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", semester='" + semester + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", cmode='" + cmode + '\'' +
                ", school='" + school + '\'' +
                ", credits='" + credits + '\'' +
                ", sid=" + sid +
                ", results='" + results + '\'' +
                ", resultstype='" + resultstype + '\'' +
                ", examtype='" + examtype + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCmode() {
        return cmode;
    }

    public void setCmode(String cmode) {
        this.cmode = cmode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getResultstype() {
        return resultstype;
    }

    public void setResultstype(String resultstype) {
        this.resultstype = resultstype;
    }

    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }

/*    public Grade(String cid, String cname, String cmode, String school, String credits, String results, String resultstype, String examtype) {
        this.cid = cid;
        this.cname = cname;
        this.cmode = cmode;
        this.school = school;
        this.credits = credits;
        this.results = results;
        this.resultstype = resultstype;
        this.examtype = examtype;
    }*/




}

