package com.shuo747.ilnpu.controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.*;
import com.shuo747.ilnpu.entity.model.BorrowModel;
import com.shuo747.ilnpu.entity.model.GradeSemesterModel;
import com.shuo747.ilnpu.entity.model.WxModel;
import com.shuo747.ilnpu.repository.*;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping(value = "/2/users")
@Controller
@EnableAutoConfiguration//此注释自动载入应用程序所需的所有Bean
public class Users2Controller {


    private static final Logger logger = LoggerFactory.getLogger(Users2Controller.class);
    @Autowired
    private BorrowDao borrowDao;
    @Autowired
    private WxUserDao wxUserDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private SemesterDao semesterDao;

    static int[] Infoindex = new int[]{2, 5, 11, 14, 15, 16, 17};
    //Set<Object> sets [][] = new Set<Object>[1][1];


    @RequestMapping(value = "/getcourses")
    @ResponseBody
    public String getCourses(String openid, String sid) {
        logger.info("getcourses >> openid = {} sid = {}", openid,sid);
        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        Student student = studentDao.findBySid(wxUser.getSid());

        System.out.println(wxUser.toString());
        if (wxUser != null){
            if (wxUser.getSid()==null){
                logger.info("getcourses >> nomatched sid ,openid = {}", openid);
                return null;
            }
            else {
                sid = wxUser.getSid().toString();
                logger.info("getcourses >> matched sid ,sid = {}", sid);
            }

        }

        else {
            logger.info("getcourses >> nomatched wxUser ,wxUser = {}", wxUser);
            return null;
        }

        //String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", sid);
        map.put("Password", student.getPassword());


        Document doc = Jsoup.parse(OkHttpUtil.post4(Url.JWC_LOGIN_7001, Url.JWC_TABLE_7001, map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");

        String current = null;
        Elements ops = doc.select("select[name=\"YearTermNO\"]").select("option");
        for (int i = ops.size()-1 ; i >=0;i--){
            Elements targrts = ops.get(i).getElementsByAttribute ("selected");
            if (targrts.size()>0){
                current = targrts.get(0).text();
            }


        }

        if (current!=null&&!semesterDao.getCurrent()[0].getCurrent().equals(current)){
            semesterDao.updateCurrentSemester(current);
            logger.info("getCourses >> new semester coming!!!!!current={}" , current);
        }
        else {
            current = semesterDao.getCurrent()[0].getCurrent();
            System.out.println("current"+current);
        }

        Elements trs = doc.select("tr[style=\"height:100px\"]");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                if (j > 0) {
                    //try {
                        for (int k = 0; k < tds.get(j).text().split("\\s").length / 5; k++) {
                            Course c = new Course(current,tds.get(j).text(), (byte) j, (byte) i, student.getSid(), 5 * k);
                            if (courseDao.checkExistCourse(c.getSemester(),c.getSid(),c.getName(),c.getRoom(),c.getTname(),c.getDay())>0){
                                //System.err.println("find"+c);
                            }
                            else {
                                logger.info("getcourses >> add new course,course = {}", c);
                                courseDao.save(c);
                            }

                        }

                            //System.out.println("tds.get(j).text().split(\"\\\\s\").length"+tds.get(j).text().split("\\s").length);
                            //oSystem.out.println("k="+k);
                    //    }

                    //} catch (Exception e) {
                        //logger.info("getcourses >> openid = {} sid = {} ex = {}", openid,sid,e);
                        //
                   // }
                }
                String text = tds.get(j).text();
                //sb.append(text+"\n");
                sb.append(text + "<br>");
                //System.out.println(text);
                //System.out.println(j);


            }

        }


        Course courses[][][] = new Course[20][7][5];
        //StringBuffer sb2 = new StringBuffer();
        Iterable<Course> course = courseDao.findAllBySid(student.getSid(),current);
        for (Course c : course) {
            String s1s[] = c.getWeek().replace("周", "").split("\\.");
            for (String s1 : s1s) {
                String s2s[] = s1.split("-");
                if (s2s.length == 1)
                    courses[toInt(s2s[0]) - 1][c.getDay() - 1][c.getSection()] = c;
                else
                    for (int i = toInt(s2s[0]); i <=toInt(s2s[1]); i++) {
                        courses[i - 1][c.getDay() - 1][c.getSection()] = c;

                    }
            }

        }

        return new Gson().toJson(courses);
    }

    public Course[][][] getCoursesArray(String openid) {
        logger.info("getCoursesArray >> openid = {}", openid);
        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        Student student = studentDao.findBySid(wxUser.getSid());

        String sid = null;
        if (wxUser != null){
            sid = wxUser.getSid().toString();
            if (wxUser.getSid()==null){
                logger.info("getCoursesArray >> nomatched sid ,openid = {}", openid);
                return null;
            }

        }

        //String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", sid);
        map.put("Password", student.getPassword());


        Document doc = Jsoup.parse(OkHttpUtil.post4(Url.JWC_LOGIN_7001, Url.JWC_TABLE_7001, map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");

        String current = null;
        Elements ops = doc.select("select[name=\"YearTermNO\"]").select("option");
        for (int i = ops.size()-1 ; i >=0;i--){
            Elements targrts = ops.get(i).getElementsByAttribute ("selected");
            if (targrts.size()>0){
                current = targrts.get(0).text();
            }


        }

        if (current!=null&&!semesterDao.getCurrent()[0].getCurrent().equals(current)){
            semesterDao.updateCurrentSemester(current);
            logger.info("getCoursesArray >> new semester coming!!!!!current={}" , current);
        }
        else {
            current = semesterDao.getCurrent()[0].getCurrent();
            System.out.println("current"+current);
        }

        Elements trs = doc.select("tr[style=\"height:100px\"]");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                if (j > 0) {
                    try {
                        for (int k = 0; k < tds.get(j).text().split("\\s").length / 5; k++) {
                            System.out.println(tds.get(j).text());
                            Course c = new Course(current,tds.get(j).text(), (byte) j, (byte) i, student.getSid(), 5 * k);
                            if (courseDao.checkExistCourse(c.getSemester(),c.getSid(),c.getName(),c.getRoom(),c.getTname(),c.getDay())>0){
                                //System.err.println("find"+c);
                            }
                            else {
                                logger.info("getCoursesArray >> add new course,course = {}", c);
                                courseDao.save(c);
                            }
                        }

                    } catch (Exception e) {
                        //System.err.println(e.getMessage());
                    }
                }
                String text = tds.get(j).text();
                //sb.append(text+"\n");
                sb.append(text + "<br>");
                //System.out.println(text);
                //System.out.println(j);


            }

        }


        Course courses[][][] = new Course[20][7][5];
        //StringBuffer sb2 = new StringBuffer();
        Iterable<Course> course = courseDao.findAllBySid(student.getSid(),current);
        for (Course c : course) {
            String s1s[] = c.getWeek().replace("周", "").split("\\.");
            for (String s1 : s1s) {
                String s2s[] = s1.split("-");
                if (s2s.length == 1)
                    courses[toInt(s2s[0]) - 1][c.getDay() - 1][c.getSection()] = c;
                else
                    for (int i = toInt(s2s[0]); i <= toInt(s2s[1]); i++) {
                        courses[i - 1][c.getDay() - 1][c.getSection()] = c;

                    }
            }

        }

        return courses;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get(WxModel wxModel) {
        logger.info("get >> wxModel ={} ", wxModel);
        /*String code = request.getParameter("code");
        String key = request.getParameter("key");
        String iv = request.getParameter("iv");

        System.out.println(request.toString());
        System.out.println(code);
        System.out.println(key);*/
        System.out.println("wxModel");
        System.out.println(wxModel);
        String res = "";
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "wx21230874eb4d653d");
        map.put("secret", "");
        map.put("js_code", wxModel.getCode());
        map.put("grant_type", "authorization_code");

        //return OkHttpUtil.get(url3,null);
        res = OkHttpUtil.get(url, map);
        //System.out.println(res);

        JsonObject jsonObject = (JsonObject) new JsonParser().parse(res);

        WxUser result;
        Gson gson = new Gson();
        if (jsonObject.has("openid")) {
            String openid = jsonObject.get("openid").getAsString();
            String session_key = jsonObject.get("session_key").getAsString();
            result = wxUserDao.findByWxUserOpenid(openid);
            if (result!=null) {
                logger.info("get >> 存在用户 wxModel ={}", result);
                //System.out.println("存在用户" + result);
                return gson.toJson(result);
            }
            else {
                logger.info("get >> 新建用户 openid ={}", openid);
                try {
                    return gson.toJson(wxUserDao.save(new WxUser(openid, session_key)));
                } catch (Exception e) {
                    WxUser wxUserTemp = wxUserDao.findByWxUserOpenid(openid);
                    logger.info("get >> 新建用户出错尝试获取数据库 wxUser={}", wxUserTemp);
                    return gson.toJson(wxUserTemp);
                }
            }

        }


        return null;


    }

    @RequestMapping(value = "/appendUserInfo")
    @ResponseBody
    public void appendUserInfo(Userinfo userinfo) {
        logger.info("appendUserInfo >> userinfo ={} ", userinfo);
        if (userinfo==null) return;
        Userinfo userinfoTemp = userinfoDao.checkExistUserinfo(userinfo.getOpenid());
        if (userinfoTemp!=null&&userinfoTemp.equals(userinfo)){
            System.out.println("无更改" + userinfo);
        }
        else {
            userinfoDao.save(userinfo);
            logger.info("appendUserInfo >> find userinfo update");
        }
        return;


    }


    @RequestMapping(value = "/getstu")
    @ResponseBody
    public String getStu(String openid) {
        logger.info("getstu >> openid = {} ", openid);

        //System.out.println(wxUserDao.findSidByWxUserName(openid));
        Student student = studentDao.findBySid(wxUserDao.findByWxUserOpenid(openid).getSid());
        if (student==null){
            logger.info("getstu >> openid = {} nomatched sid", openid);
            return null;
        }
        logger.info("getstu >> openid = {} findstu = {}", openid,student.getName());

        return new Gson().toJson(student);


    }


    @RequestMapping(value = "/bind")
    @ResponseBody
    /**
     * 0:pwd error
     * 1:pwd correct
     */
    public String bind(String openid, String sid, String password) {
        /*String code = request.getParameter("code");
        String key = request.getParameter("key");
        String iv = request.getParameter("iv");*/
        logger.info("bind >> openid = {} sid = {} password = {} ", openid,sid,password);

        Map<String, Object> map = new HashMap<>();
        if (!judgeSid(sid, password)) {
            map.put("res", 0);
            logger.info("bind >> sid&pwd failed! sid = {} password = {} ",sid,password);
            return new Gson().toJson(map);
        }

        logger.info("bind >> sid&pwd correct! sid = {} password = {} ",sid,password);
        try {
            studentDao.save(new Student(Long.parseLong(sid), password, getStuInfoArray(sid, password)));
            wxUserDao.updateSidByOpenid(openid, Long.parseLong(sid));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        logger.info("bind >> bind compete! sid = {} openid = {} ",sid,openid);
        //return new Gson().toJson(wxUser);

        map.put("res", 1);
        map.put("wxuser", wxUser);
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/bindandgetstu")
    @ResponseBody
    /**
     * 0:pwd error
     * 1:pwd correct
     */
    public String bindAndGetStu(String openid, String sid, String password) {
        logger.info("bindandgetstu >> openid = {} sid = {} password = {} ", openid,sid,password);
        /*String code = request.getParameter("code");
        String key = request.getParameter("key");
        String iv = request.getParameter("iv");
*/
        Map<String, Object> map = new HashMap<>();
        if (!judgeSid(sid, password)) {
            map.put("res", 0);
            logger.info("bindandgetstu >> sid&pwd failed! sid = {} password = {} ",sid,password);
            return new Gson().toJson(map);
        }

        Student student = null;
        try {
            student = new Student(Long.parseLong(sid), password, getStuInfoArray(sid, password));
            if (studentDao.findBySid(Long.parseLong(sid))==null){
                logger.info("bindandgetstu >> add new student stu = {}",student);
                studentDao.save(student);
            }
            else {
                if (studentDao.findBySid(Long.parseLong(sid)).equals(student)){
                    logger.info("bindandgetstu >> find exist student stu = {}",student);
                }
                else {
                    logger.info("bindandgetstu >> find exist student info update stu = {}",student);
                    studentDao.save(student);
                }
            }


            wxUserDao.updateSidByOpenid(openid, Long.parseLong(sid));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }

        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        logger.info("bindandgetstu >> bind compete! sid = {} openid = {} ",sid,openid);

        map.put("res", 1);
        map.put("wxuser", wxUser);
        map.put("stu", student);
        map.put("courses", getCoursesArray(openid));
        return new Gson().toJson(map);
    }


    @RequestMapping("set")
    @ResponseBody
    public String set() {
       /* Course course = new Course("Web技术与应用" ,"李文超", "405机房（405）" ,"1-5.8-16周 ","2节");
        courseDao.save(course);*/

        return null;
        //return OkHttpUtil.get(url3,null);
    }

/*
    @RequestMapping("g")
    @ResponseBody
    public String g(String a, String b) {


        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", a);
        map.put("Password", b);
        return OkHttpUtil.post2(url, url2, map);
        //return OkHttpUtil.get(url3,null);
    }
    */


    @RequestMapping("wx")
    @ResponseBody
    public String wx(String code) {


        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String res = "";
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "wx21230874eb4d653d");
        map.put("secret", "b50cdbe05d7cadb915d78e796c5fff6a");
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        //return OkHttpUtil.get(url3,null);

        res = OkHttpUtil.get(url, map);
        //System.out.println(res);
        return res;


    }



    //@RequestMapping("/")
    //@ResponseBody
    public String root() {


        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //个人信息
        String url2 = "http://202.118.120.84:800/ACTIONFINDSTUDENTINFO.APPPROCESS?mode=1&showMsg=";
        String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", "1602060326");
        map.put("Password", "19980311456qaz");

        System.out.println(OkHttpUtil.post2(url, url2, map));
        Document doc = Jsoup.parse(OkHttpUtil.post2(url, url2, map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");
        Elements trs = doc.select("table[style=\"BORDER-COLLAPSE:collapse\"]").select("tr");
        //Elements trs = doc.select("tr");

        String[] info = new String[7];
        for (int i = 0; i < Infoindex.length; i++) {
            System.out.println("+++++" + trs.get(Infoindex[i]).select("td").get(2).text());
            info[i] = trs.get(Infoindex[i]).select("td").get(2).text();
        }

        studentDao.save(new Student(info));
        /*for(int i = 0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            System.out.println("外"+i);
            for(int j = 0;j<tds.size();j++){
                System.out.println("内"+j);
                    String text = tds.get(j).text();
                    //sb.append(text+"\n");
                    sb.append(text+"<br>");
                    System.out.println(text);



            }System.out.println("-----");

        }*/

        return sb.toString();

    }



    public static String[] getStuInfoArray(String u, String p) {
        //String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //个人信息
        //String url2 = "http://202.118.120.84:800/ACTIONFINDSTUDENTINFO.APPPROCESS?mode=1&showMsg=";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", u);
        map.put("Password", p);

        //System.out.println(OkHttpUtil.post2(url, url2, map));
        Document doc = Jsoup.parse(OkHttpUtil.post4(Url.JWC_LOGIN_7001, Url.JWC_PERSON_INFO_7001, map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");
        Elements trs = doc.select("table[style=\"BORDER-COLLAPSE:collapse\"]").select("tr");
        //Elements trs = doc.select("tr");
        String[] info = new String[7];
        for (int i = 0; i < Infoindex.length; i++) {

            info[i] = trs.get(Infoindex[i]).select("td").get(2).text().replaceAll(" ","").replaceAll("\\p{C}","");

//            System.out.println(info[i].indexOf("nbsp"));
//            System.out.println("+++++" + info[i] );
        }
        return info;
    }

    @RequestMapping("judgesid")
    @ResponseBody
    public boolean judgeSid(String sid, String password) {
        logger.info("judgeSid >> sid = {} password = {} ",sid,password);
        String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", sid);
        map.put("Password", password);
        Document document = Jsoup.parse(OkHttpUtil.post5(map));
        Elements titles = document.getElementsByTag("title");
        if (titles.size()>0&&titles.get(0).text().equals("网络综合平台")){
            logger.info("judgeSid >> correct！ sid = {} password = {} ",sid,password);
            return true;
        }

        logger.info("judgeSid >> failed！ sid = {} password = {} ",sid,password);
        return false;

    }


    @RequestMapping("getgrade")
    @ResponseBody
    public String getGrade(String sid) {
        logger.info("getGrade >>  sid = {} ", sid);
        if (sid==null)
            return null;
        Map<String, String> map = new HashMap<>();
        //String url = "http://202.118.120.84:7001/ACTIONQUERYSTUDENTSCOREBYJM.APPPROCESS?mode=2";
        map.put("Student", sid);
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
        /*if (Math.random()>0.1){
            doc = Jsoup.parse(OkHttpUtil.post(Url.COMMON_GRADE2, map));
        }
        else {
            doc = Jsoup.parse(OkHttpUtil.post(Url.COMMON_GRADE, map));
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
                    logger.info("getGrade >>  sid = {} grade = {} 产生新数据!!", sid,grade);
                    try {
                        gradeDao.save(grade);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            //}
            //catch (Exception e){}

            //System.out.println(trs.get(i).text());
            //sb.append("<br>");

        }

        return getGradeJson(sid);
        //return null;
    }

    @RequestMapping("getgradecache")
    @ResponseBody
    public String getGradeCache(String openid,String sid) {
        logger.info("getGradeCache >>  openid = {} sid = {} ", openid, sid);
        return getGradeJson(sid);
    }

    public static int toInt(String s) {
        return Integer.parseInt(s);
    }


    //@RequestMapping("test")
    //@ResponseBody
    public String test() {
        return new Gson().toJson(gradeDao.findALLSemester((long) 1611030213));
    }

    //@RequestMapping("test2")
    //@ResponseBody
    public String getGradeJson(String sid) {
        long l = 0;
        try {
            l = Long.parseLong(sid);
        } catch (NumberFormatException e) {
            return null;
        }
        List<String> list = new ArrayList<>();
        list =  gradeDao.findALLSemester(l);
        List<GradeSemesterModel> list1 = new ArrayList();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int o1var = Integer.parseInt(o1.substring(0, 4));
                int o2var = Integer.parseInt(o2.substring(0, 4));
                String o1vars = o1.substring(12, 13);
                String o2vars = o2.substring(12, 13);
                System.out.println(o1var+"\t"+o2var+"\t"+o1vars+"\t"+o2vars);
                if (o1var > o2var)
                    return -1;
                else if (o1var == o2var){
                    if (o1vars.equals("二"))
                        return -1;
                    else return 1;
                }
                else return 1;
            }
        });
        for (int i =0;i<list.size();i++){
            list1.add(new GradeSemesterModel(list.get(i),gradeDao.findALLBySemester(l,list.get(i))));
            //list1.add(new GradeSemesterModel(list.get(i),gradeDao.findMainValueBySemester(l,list.get(i))));
        }
        return new Gson().toJson(list1);
        //return JSON.toJSONString(list1);
    }


    @RequestMapping("getGradeList")
    @ResponseBody
    public List<GradeSemesterModel> getGradeList(String sid) {
        logger.info("getGradeList >>  sid = {} ", sid);
        long l = Long.parseLong(sid);
        List<String> list = new ArrayList<>();
        list =  gradeDao.findALLSemester(l);
        List<GradeSemesterModel> list1 = new ArrayList();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int o1var = Integer.parseInt(o1.substring(0, 4));
                int o2var = Integer.parseInt(o2.substring(0, 4));
                String o1vars = o1.substring(12, 13);
                String o2vars = o2.substring(12, 13);
                //System.out.println(o1var+"\t"+o2var+"\t"+o1vars+"\t"+o2vars);
                if (o1var > o2var)
                    return -1;
                else if (o1var == o2var){
                    if (o1vars.equals("二"))
                        return -1;
                    else return 1;
                }
                else return 1;
            }
        });
        for (int i =0;i<list.size();i++){
            list1.add(new GradeSemesterModel(list.get(i),gradeDao.findALLBySemester(l,list.get(i))));
        }
        return list1;
    }


    @RequestMapping("getPCard")
    @ResponseBody
    public String getPCard(String openid,String sid) {
        logger.info("getPCard >> openid = {} sid = {}", openid,sid);
        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        System.out.println(wxUser.toString());
        if (wxUser != null){
            if (wxUser.getSid()==null){
                logger.info("getPCard >> nomatched sid ,openid = {}", openid);
                return null;
            }
            else {
                sid = wxUser.getSid().toString();
                logger.info("getPCard >> matched sid ,sid = {}", sid);
            }

        }
        else {
            logger.info("getPCard >> nomatched wxUser ,wxUser = {}", wxUser);
            return null;
        }
        String res = null;
        try {
            res = OkHttpUtil.post6(Url.PCARD_LOGIN,Url.PCARD_ACCOUNT_SHOW,sid);
        } catch (Exception e) {
            logger.info("getPCard >> failed! ,wxUser = {}", wxUser);
            return null;
        }
        return res;
    }

    @RequestMapping("getPCardBill")
    @ResponseBody
    public String getPCardBill(String openid,String sid) {
        logger.info("getPCardBill >> openid = {} sid = {}", openid,sid);
        WxUser wxUser = wxUserDao.findByWxUserOpenid(openid);
        System.out.println(wxUser.toString());
        Map<String,String> map = new HashMap();

        if (wxUser != null){
            if (wxUser.getSid()==null){
                logger.info("getPCardBill >> nomatched sid ,openid = {}", openid);
                return null;
            }
            else {
                sid = wxUser.getSid().toString();
                logger.info("getPCardBill >> matched sid ,sid = {}", sid);
                map.put("page", "1");
                map.put("rp", "50");
                map.put("sortname", "jndatetime");
                map.put("sortorder", "desc");
                map.put("trjnquary", "7");
            }

        }

        else {
            logger.info("getPCardBill >> nomatched wxUser ,wxUser = {}", wxUser);
            return null;
        }
        String res = null;
        try {
            res = OkHttpUtil.post7(Url.PCARD_LOGIN,Url.PCARD_BILL,sid,map);
        } catch (Exception e) {
            logger.info("getPCardBill >> failed! ,wxUser = {}", wxUser);
            return null;
        }
        return res;
    }


    //@RequestMapping("test3")
    //@ResponseBody
    public String test3() {
        String url = "http://libweixin.lnpu.edu.cn/m/reader/check_login.action";
        Map<String,String> map = new HashMap<>();
        map.put("name","1611030216");
        map.put("passwd","1611030216");

        return OkHttpUtil.post(url,map);
    }

    @RequestMapping("getBorrows")
    @ResponseBody
    public String getBorrows(HttpServletResponse response, String sid) {
        logger.info("getBorrows >>  sid = {} ", sid);
        if (sid==null) return null;
        //坑爹图书馆，首次会验证姓名。。。
        boolean flag;
        //String url = "http://libweixin.lnpu.edu.cn/m/reader/check_login.action";
        String url = "http://202.118.121.235:80/m/reader/check_login.action";
        String url2 = "http://202.118.121.235:80/m/reader/lend_list.action";
        String url3 = "http://202.118.121.235:80/m/reader/lend_list.action?page=2";
        String url4 = "http://202.118.121.235:80/m/reader/check_name.action";
        String url5 = "http://202.118.121.235:80/m/reader/lend_hist.action";

        Map<String, String> map = new HashMap<>();
        String passwd = sid;
        try {
            if (Long.parseLong(sid)>1700000000)
                passwd = "123456";
        } catch (NumberFormatException e) {
            return null;
        }
        map.put("name", sid);
        map.put("passwd", passwd);
        String res = OkHttpUtil.post(url, map);
        System.out.println("-------"+res);
        if (res==null||res.length()==0){
            response.setStatus(404);
            return null;
        }
        List<Borrow> borrows = new ArrayList<>();
        BorrowModel borrowModel = new BorrowModel();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(res);
        //首次登录姓名问题
        if (jsonObject.get("success").getAsBoolean()){
            flag = true;
            logger.info("getBorrows >> login success! >> sid = {} ", sid);
        }

        else {
            String name = studentDao.findNameBySid(Long.parseLong(sid));
            logger.info("getBorrows >> need verify name! sid = {} name = {} ", sid,name);
            Map<String, String> map2 = new HashMap<>();
            map2.put("name", studentDao.findNameBySid(Long.parseLong(sid)));
            map2.put("username", sid);
            map2.put("passwd", passwd);
            map2.put("submitType", "reg");
            map2.put("type","1");

            //map2.put("name", studentDao.findNameBySid(Long.parseLong(sid)));
            //map2.put("type","1");
            //System.out.println(OkHttpUtil.post3(url,url4,map,map2));
            JsonObject jsonObject2 = (JsonObject) new JsonParser().parse(OkHttpUtil.post3(url,url4,map,map2));
            flag = jsonObject2.get("success").getAsBoolean();
        }

        if (flag) {
            logger.info("getBorrows >> login success & verifed sid = {} ", sid);
            Document doc = Jsoup.parse(OkHttpUtil.post2(url, url2, map));
            Elements weui_panel = doc.select("div[class=\"weui_panel\"]");
            Elements weui_panel_hd = weui_panel.select("div[class=\"weui_panel_hd\"]");
            Elements weui_panel_bd = weui_panel.select("div[class=\"weui_panel_bd\"]");
            Elements error = weui_panel.select("div[class=\"huiwen-content-padded error\"]");

            Elements as = weui_panel_bd.select("a[class=\"weui_media_box weui_media_text\"]");
            Elements renewsas = weui_panel_bd.select("div[class=\"weui_btn_area\"]").select("a");

            /*
            for (int i = 0;i<weui_panel.size();i++){
                //System.out.println(as.get(i).text());
                Elements weui_panel = doc.select("div[class=\"weui_panel\"]").select("a");
                for(int j =0;j<;)
            }
            */
            if (weui_panel_hd.size() > 0)
                borrowModel.currentData = weui_panel_hd.get(0).text().substring(5).replace("]","");
            if (error.size() > 0)
                borrowModel.error = error.get(0).text();
            if (as.size() > 0)
            for (int i = 0;i<as.size();i++) {
                String [] s = as.get(i).select("ul").text().split("\\s");
                //System.out.println(as.get(i).select("ul").text());

                //书名与作者
                String [] s2 = as.get(i).select("h4").get(0).text().split("/");
                Borrow borrow = new Borrow(s,s2);
                borrow.setTimelent(as.get(i).select("p").get(0).text());
                borrow.setSid(Long.parseLong(sid));
                if (renewsas.size() > 0)
                    borrow.setBarcode(renewsas.get(i).attr("onclick").substring(7).replace("\')",""));
                borrows.add(borrow);

            }






            Document doc2 = Jsoup.parse(OkHttpUtil.post2(url, url3, map));
            Elements weui_panel2 = doc2.select("div[class=\"weui_panel\"]");
            Elements weui_panel_bd2 = weui_panel2.select("div[class=\"weui_panel_bd\"]");
            Elements as2 = weui_panel_bd2.select("a[class=\"weui_media_box weui_media_text\"]");
            Elements renewsas2 = weui_panel_bd2.select("div[class=\"weui_btn_area\"]").select("a");

            /*
            for (int i = 0;i<weui_panel.size();i++){
                //System.out.println(as.get(i).text());
                Elements weui_panel = doc.select("div[class=\"weui_panel\"]").select("a");
                for(int j =0;j<;)
            }
            */
            if (as2.size() > 0){
                for (int i = 0;i<as2.size();i++) {
                    String [] s = as2.get(i).select("ul").text().split("\\s");
                    //System.out.println(as.get(i).select("ul").text());

                    //书名与作者
                    String [] s2 = as2.get(i).select("h4").get(0).text().split("/");
                    Borrow borrow = new Borrow(s,s2);
                    borrow.setTimelent(as2.get(i).select("p").get(0).text());
                    if (renewsas.size() > 0)
                        borrow.setBarcode(renewsas2.get(i).attr("onclick").substring(7).replace("\')",""));
                    borrows.add(borrow);

                }

            }





            Document doc3 = Jsoup.parse(OkHttpUtil.post2(url, url5, map));
            Elements weui_panel_hd3 = doc3.select("div[class=\"weui_panel\"]").select("div[class=\"weui_panel_hd\"]");

            if (weui_panel_hd3.size() > 0)
                borrowModel.total = weui_panel_hd3.get(0).text().substring(5).replace("]", "");


            try {

            } catch (Exception e) {

            }
            for (int i = 0;i<borrows.size();i++)
                try {
                    borrowDao.save(borrows.get(i));
                } catch (Exception e) {

                }
            borrowModel.borrows = borrows;


            return new Gson().toJson(borrowModel);

        }
        return null;


    }


    @RequestMapping("getBorrowsHistory")
    @ResponseBody
    public String getBorrowsHistory(String sid) {
        logger.info("getBorrowsHistory >>  sid = {} ", sid);
        //坑爹图书馆，首次会验证姓名。。。
        boolean flag = false;
        //计算页数
        int page= 0;
        String url = "http://202.118.121.235:80/m/reader/check_login.action";
        //String url2 = "http://202.118.121.235:80/m/reader/lend_list.action";
        //String url3 = "http://202.118.121.235:80/m/reader/lend_list.action?page=2";
        //String url4 = "http://202.118.121.235:80/m/reader/check_name.action";
        String url5 = "http://202.118.121.235:80/m/reader/lend_hist.action";
        Map<String, String> map = new HashMap<>();
        String passwd = sid;
        try {
            if (Long.parseLong(sid)>1700000000)
                passwd = "123456";
        } catch (NumberFormatException e) {
            return null;
        }
        map.put("name", sid);
        map.put("passwd", passwd);
        String res = OkHttpUtil.post(url, map);
        System.out.println(res);
        List<Borrow> borrows = new ArrayList<>();
        BorrowModel borrowModel = new BorrowModel();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(res);
        //首次登录姓名问题
        if (jsonObject.get("success").getAsBoolean()){
            flag = true;

        }
        

        if (flag) {
            logger.info("getBorrowsHistory >> login success! >> sid = {} ", sid);
            Document doc = Jsoup.parse(OkHttpUtil.post2(url, url5, map));
            Elements weui_panel = doc.select("div[class=\"weui_panel\"]");
            Elements weui_panel_hd = weui_panel.select("div[class=\"weui_panel_hd\"]");
            Elements weui_panel_bd = weui_panel.select("div[class=\"weui_panel_bd\"]");
            Elements error = weui_panel.select("div[class=\"huiwen-content-padded error\"]");

            Elements as = weui_panel_bd.select("a[class=\"weui_media_box weui_media_text\"]");
            Elements renewsas = weui_panel_bd.select("div[class=\"weui_btn_area\"]").select("a");


            //判断页数总页数


            /*
            for (int i = 0;i<weui_panel.size();i++){
                //System.out.println(as.get(i).text());
                Elements weui_panel = doc.select("div[class=\"weui_panel\"]").select("a");
                for(int j =0;j<;)
            }
            */
            if (weui_panel_hd.size() > 0) {
                String data = weui_panel_hd.get(0).text().substring(5).replace("]", "");
                borrowModel.currentData = data;
                page = (int) Math.ceil(Double.parseDouble(data) / 10);
                logger.info("getBorrowsHistory >> sid = {} >> page = {} >> currentData = {} ", sid,page,data);
            }
            if (error.size() > 0)
                borrowModel.error = error.get(0).text();


            for (int k =1;k<=page;k++) {
                String pageUrl = url5+"?page="+k;
                Document docTemp = Jsoup.parse(OkHttpUtil.post2(url, pageUrl, map));
                Elements weui_panelTemp = docTemp.select("div[class=\"weui_panel\"]");
                Elements weui_panel_hdTemp = weui_panelTemp.select("div[class=\"weui_panel_hd\"]");
                Elements weui_panel_bdTemp = weui_panelTemp.select("div[class=\"weui_panel_bd\"]");
                //Elements errorTemp = weui_panelTemp.select("div[class=\"huiwen-content-padded error\"]");
                Elements asTemp = weui_panel_bdTemp.select("a[class=\"weui_media_box weui_media_text\"]");




                if (asTemp.size() > 0)
                    for (int i = 0; i < asTemp.size(); i++) {
                        String[] s = asTemp.get(i).select("ul").text().split("\\s");
                        //System.out.println(as.get(i).select("ul").text());

                        //书名与作者
                        String[] s2 = asTemp.get(i).select("h4").get(0).text().split("/");
                        //Borrow borrow = new Borrow(s, s2);
                        Borrow borrow = new Borrow(s2[0],s2[1],s[0],s[1]);
                        borrow.setTimelent(asTemp.get(i).select("p").get(0).text());
                        borrows.add(borrow);

                    }

            }


            try {

            } catch (Exception e) {

            }
            borrowModel.borrows = borrows;


            return new Gson().toJson(borrowModel);

        }
        return null;


    }

    @RequestMapping("renew")
    @ResponseBody
    public boolean renew(String sid, String barcode) {
        logger.info("renew >> sid = {} barcode = {} ", sid,barcode);
        if (barcode==null){
            return false;
        }
        String url = "http://202.118.121.235:80/m/reader/check_login.action";
        String url2 = "http://202.118.121.235:80/m/reader/renew.action?barcode=";
        Map<String,String> map = new HashMap<>();
        String passwd = sid;
        try {
            if (Long.parseLong(sid)>1700000000)
                passwd = "123456";
        } catch (NumberFormatException e) {
            return false;
        }
        map.put("name",sid);
        map.put("passwd",passwd);

        url2 = url2+barcode;
        boolean flag = false;
        String res = OkHttpUtil.post2(url,url2,map);
        System.out.println(res);
        Document doc = Jsoup.parse(res);
        Elements data = doc.select("h2[class=\"weui_msg_title\"]");
        if (data.size()>0){
            flag = data.get(0).text().equals("续借成功！");
        }
        return flag;
    }



    @RequestMapping(value = "/getNews")
    @ResponseBody
    public String getNews(String sid) {
        logger.info("getNews >> sid = {} ", sid);

        try {
            List<News> news = newsDao.findAllOrderByTime();
            List<News> newsRes;
            if (news.size()>15){
                return new Gson().toJson(news.subList(0,14));
            }
            else {
                return new Gson().toJson(news.subList(0,news.size()-1));
            }

        }
        catch (Exception e){
            logger.info("getNews filed>> sid = {} ", sid);
        }


        return null;
    }

    public void updateNews() {
        String res = OkHttpUtil.get(Url.SCHOOL_NEWS,null);
        Document doc = Jsoup.parse(res);
        Elements table = doc.select("table[class=\"winstyle27654\"]");
        if (table.size()<=0)
            return;
        Elements trs = doc.select("tr[height=\"20\"]");
        //System.out.println(trs.text());
        if (trs.size()<=0)
            return;
        for (int i = 0; i<trs.size();i++){
            Elements as = trs.get(i).select("a");
            String href = as.get(0).attr("href");
            Elements span = trs.get(i).select("span[class=\"timestyle27654\"]");
            //System.out.println(as.get(0).text());
            //System.out.println(span.get(0).text());
            //System.out.println(href);


            Document doc2 = Jsoup.parse(OkHttpUtil.get(Url.SCHOOL_DOMAIN+href,null));
            Elements table2 = doc2.select("div[class=\"Section0\"]");
            //System.out.println(table2.toString());


            try {
                newsDao.save(new News(as.get(0).text(),span.get(0).text(),table2.toString()));
            } catch (Exception e) {
                //e.printStackTrace();
            }

        }
    }



    @RequestMapping("/getExam")
    @ResponseBody
    public String r(String sid,String sclass) throws Exception {
        logger.info("getExam >> sid = {} sclass = {} ", sid,sclass);
        String res = null;


        try {
            res = new Gson().toJson(examDao.findOwnBySClss(sclass,semesterDao.getCurrent()[0].getCurrent()));
        } catch (Exception e) {
            logger.info("getExam Error >> sid = {} sclass = {} ", sid,sclass);
            return res;
        }
        logger.info("getExam Success >> sid = {} sclass = {} res = {}", sid,sclass,res);
        return res;

    }

    @RequestMapping(value = "/appendStuInfo")
    @ResponseBody
    public String appendStuInfo(String sid, String dormitory, String dormitoryID, String tel) {
        return null;
    }


    public static void p(Object o) {
        System.out.println(o);
    }

}
