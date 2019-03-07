package com.shuo747.ilnpu.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    public Long sid;
    private String name;
    private String tname;
    private String room;
    private String week;
    private String semester;
    private byte times;
    private byte day;
    private byte section;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public byte getTimes() {
        return times;
    }

    public void setTimes(byte times) {
        this.times = times;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public byte getSection() {
        return section;
    }

    public void setSection(byte section) {
        this.section = section;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Course() {
    }

    public Course(Long sid, String name, String tname, String room, String week, String semester, byte times, byte day, byte section) {
        this.sid = sid;
        this.name = name;
        this.tname = tname;
        this.room = room;
        this.week = week;
        this.semester = semester;
        this.times = times;
        this.day = day;
        this.section = section;
    }



    public Course(String name, String tname, String room, String week, byte times, byte day) {
        this.name = name;
        this.tname = tname;
        this.room = room;
        this.week = week;
        this.times = times;
        this.day = day;
    }

    public Course(String semester,String s,byte day,byte section,Long sid,int k) {
        String [] strings = s.split("\\s");
        this.semester = semester;
        this.name = strings[0+k];
        this.tname = strings[1+k];
        this.room = strings[2+k];
        this.week = strings[3+k];
        this.times = Byte.parseByte(strings[4+k].replace("节",""));
        this.day = day;
        this.section = section;
        this.sid = sid;

    }

    public Course(String s,byte day,byte section,Long sid,int k) {
        String [] strings = s.split("\\s");
        this.name = strings[0+k];
        this.tname = strings[1+k];
        this.room = strings[2+k];
        this.week = strings[3+k];
        this.times = Byte.parseByte(strings[4+k].replace("节",""));
        this.day = day;
        this.section = section;
        this.sid = sid;

    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tname='" + tname + '\'' +
                ", room='" + room + '\'' +
                ", week='" + week + '\'' +
                ", times='" + times + '\'' +
                ", day=" + day +
                ", section=" + section +
                '}';
    }
}
