package com.shuo747.ilnpu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Borrow implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String barcode;
    private String bname;
    private String author;
    private String timelent;
    private String timeback;
    private String location;
    private String details;
    private Long sid;


    public Borrow() {
    }

    public Borrow(String[] uls) {
        try {
            this.timeback = uls[0];
            this.location = uls[1];
            this.details = uls[2];
        }
        catch (Exception e){
            System.out.println(uls);
        }

    }

    public Borrow(String[] uls, String[] s2) {
        try {
            this.timeback = uls[0];
            this.location = uls[1];
            this.details = uls[2];

            this.bname=s2[0];
            this.author=s2[1];
        }
        catch (Exception e){
            //System.out.println(uls + s2);
            //System.out.println(failed);

        }
    }

    public Borrow(String s, String s1, String s2, String s3) {
        this.timeback = s2;
        this.location = s3;
        this.bname=s;
        this.author=s1;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getTimelent() {
        return timelent;
    }

    public void setTimelent(String timelent) {
        this.timelent = timelent;
    }

    public String getTimeback() {
        return timeback;
    }

    public void setTimeback(String timeback) {
        this.timeback = timeback;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
}
