package com.shuo747.ilnpu.entity.model;


import com.shuo747.ilnpu.entity.Grade;

import java.io.Serializable;
import java.util.List;

public class GradeSemesterModel implements Serializable {
    String semester;
    List<Grade> list;

    public GradeSemesterModel(String semester, List list) {
        this.semester = semester;
        this.list = list;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<Grade> getList() {
        return list;
    }

    public void setList(List<Grade> list) {
        this.list = list;
    }
}
