package com.shuo747.ilnpu.task;


import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.Grade;
import com.shuo747.ilnpu.entity.Student;
import com.shuo747.ilnpu.repository.GradeDao;
import com.shuo747.ilnpu.repository.StudentDao;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class UpDateGrade {

    private static final Logger logger = LoggerFactory.getLogger(GradeTask.class);
    @Autowired
    private GradeDao gradeDao;


    @Async("updateGradeExecutor")
    public void updateItemGrade(Long sid) {
        logger.info("updateItemGrade >>  sid = {} ", sid);
        if (sid==null)
            return ;
        Map<String, String> map = new HashMap<>();
        map.put("Student", String.valueOf(sid));
        map.put("BeginYearTermNO", "");
        map.put("ClassNO", "");
        map.put("ComeYear", "");
        map.put("Course", "");
        map.put("DeptNO", "");
        map.put("EndYearTermNO", "");
        map.put("MajorNO", "");
        ArrayList<Grade> dbList = null;
        ArrayList<String> list = null;
        Document doc = null;
        /*if (Math.random()>0.4){
            doc = Jsoup.parse(OkHttpUtil.post(Url.COMMON_GRADE, map));
        }
        else {
            doc = Jsoup.parse(OkHttpUtil.post(Url.COMMON_GRADE2, map));
        }*/
        doc = Jsoup.parse(OkHttpUtil.post(Url.COMMON_GRADE2, map));
        Elements trs = doc.select("tr[align=\"center\"]");

        //select("table#table_style_e");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            //list = new ArrayList<String>();
            /*
            for (int j = 0; j < tds.size(); j++) {
                //list.add(tds.get(i).text());
                //System.out.println(tdes.get(i).text());
                sb.append(tds.get(j).text()+"&nbsp");
            }
            */
            //try {
            Grade grade = new Grade(trs.get(i).text().split("\\s"));
            if (gradeDao.checkExistGrade(grade.getSid(),grade.getSemester(),grade.getResultstype(),grade.getCname())>0){
                //System.out.println("find"+grade.toString());
                //break;
            }
            else {
                logger.info("updateItemGrade >>  sid = {} grade = {} 产生新数据!!", sid,grade);
                try {
                    gradeDao.save(grade);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }






}
