package com.shuo747.ilnpu.entity.model;

import javax.persistence.Entity;

public class GradeModel {

    private String  cid;
    private String  cname;
    private String  cmode;
    private String  school;
    private String  credits;
    private String  results;
    private String  resultstype;
    private String  examtype;

    public GradeModel(String cid, String cname, String cmode, String school, String credits, String results, String resultstype, String examtype) {
        this.cid = cid;
        this.cname = cname;
        this.cmode = cmode;
        this.school = school;
        this.credits = credits;
        this.results = results;
        this.resultstype = resultstype;
        this.examtype = examtype;
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
}
