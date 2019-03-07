package com.shuo747.ilnpu.controller;


import com.google.gson.Gson;
import com.shuo747.ilnpu.common.Url;
import com.shuo747.ilnpu.entity.*;
import com.shuo747.ilnpu.repository.*;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import com.shuo747.ilnpu.utils.rc.RCYKT;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Controller
//@EnableAutoConfiguration//此注释自动载入应用程序所需的所有Bean
public class TestController {



    @Autowired
    private CourseDao courseDao;



    @Autowired
    private NewsDao newsDao;

    @Autowired
    private ExamDao examDao;
    @Autowired
    private SemesterDao semesterDao;





    @RequestMapping("set")
    @ResponseBody
    public String set() {
       /* Course course = new Course("Web技术与应用" ,"李文超", "405机房（405）" ,"1-5.8-16周 ","2节");
        courseDao.save(course);*/

        return null;
        //return OkHttpUtil.get(url3,null);
    }


    @RequestMapping("g")
    @ResponseBody
    public String g(String a ,String b ) {



        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String,String> map = new HashMap<String,String>();
        map.put("WebUserNO",a);
        map.put("Password",b);
        return OkHttpUtil.post2(url,url2,map);
        //return OkHttpUtil.get(url3,null);
    }


    @RequestMapping("wx")
    @ResponseBody
    public String wx(String code) {



        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String res = "";
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        //String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String,String> map = new HashMap<String,String>();
        map.put("appid","wx21230874eb4d653d");
        map.put("secret","b50cdbe05d7cadb915d78e796c5fff6a");
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        //return OkHttpUtil.get(url3,null);

        res=OkHttpUtil.get(url,map);
        System.out.println(res);
        return res;
    }



    /*@RequestMapping("/")
    @ResponseBody
    public String root() {



        //http://202.118.120.84:800/ACTIONLOGON.APPPROCESS

        String url = "http://202.118.120.84:800/ACTIONLOGON.APPPROCESS";
        String url2 = "http://202.118.120.84:800/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
        String url3 = "http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101";
        Map<String,String> map = new HashMap<String,String>();
        map.put("WebUserNO","1611030213");
        map.put("Password","111222");



        Document doc = Jsoup.parse(OkHttpUtil.post2(url,url2,map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");
        Elements trs = doc.select("tr[style=\"height:100px\"]");
        for(int i = 0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            for(int j = 0;j<tds.size();j++){
                    if (j>0) {
                        try {
                            for (int k = 0 ; k < tds.get(j).text().split("\\s").length/5;k++){
                                courseDao.save(new Course(tds.get(j).text(), (byte) j,(byte)i,5*k));
                                //System.out.println("tds.get(j).text().split(\"\\\\s\").length"+tds.get(j).text().split("\\s").length);
                                //oSystem.out.println("k="+k);
                            }

                        } catch (Exception e) {
                            //System.err.println(e.getMessage());
                        }
                    }
                    String text = tds.get(j).text();
                    //sb.append(text+"\n");
                    sb.append(text+"<br>");
                    //System.out.println(text);
                    //System.out.println(j);



            }

        }

        return sb.toString();

    }*/


    @RequestMapping("/find")
    @ResponseBody
    public String find() {
        //ArrayList<Course> [] [] [] ccc = new ArrayList<Course> [20] [7] [5];
        Course courses[][][] = new Course[20][7][5];
        StringBuffer sb = new StringBuffer();
        Iterable<Course> course = courseDao.findAllBySid(null,null);
        for (Course c: course){
            String s1s[] = c.getWeek().replace("周","").split("\\.");
            for (String s1:s1s){
                String s2s[] = s1.split("-");
                if (s2s.length==1)
                    courses[toInt(s2s[0])-1][c.getDay()-1][c.getSection()] = c;
                else
                for (int i = toInt(s2s[0]);i<toInt(s2s[1]);i++){
                    courses[i-1][c.getDay()-1][c.getSection()] = c;

                }
            }

        }
        /*for (Course c1[][]:courses)
            for (Course c2[]:c1)
                for (Course c3:c2)
                    try {
                        sb.append(c3.toString()+"<br>");
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }*/

        //sb.append(c);
        return new Gson().toJson(courses);
    }


    @RequestMapping("/m")
    @ResponseBody
    public String m() throws Exception {

        Request request = new Request.Builder()
                .url(Url.PCARD_VALIDATE+Math.random())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                //System.out.println(response.body().string());
                BufferedImage img = ImageIO.read(response.body().byteStream());
                String validate = String.valueOf(RCYKT.recognize(img));
                //System.out.println(validate);
                return String.valueOf(validate);
            }
        } catch (Exception e) {
            //logger.error("okhttp3 put error >> ex = {}", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return null;


    }


    @RequestMapping("/n")
    @ResponseBody
    public String n(String sid) throws Exception {

        Map<String,String> map = new HashMap();
        map.put("WebUserNO", "1611030213");
        map.put("Password", "111222");
        String res = OkHttpUtil.post6(Url.PCARD_LOGIN,Url.PCARD_ACCOUNT_SHOW,sid);
        //JsonObject jsonObject = (JsonObject) new JsonParser().parse(res);

 /*       if (jsonObject.has("sno")) {
            String sid2 = jsonObject.get("sno").getAsString();
            JsonArray jsonArray = jsonObject.get("userAccountList").getAsJsonArray();

           for (int i = 0;i<jsonArray.size();i++){
               System.out.println(jsonArray.get(i).getAsString());
           }

        }*/

        Gson gson = new Gson();
        PCard resp = gson.fromJson(res, PCard.class);
        System.out.println(resp);


        return OkHttpUtil.post6(Url.PCARD_LOGIN,Url.PCARD_ACCOUNT_SHOW,sid);


    }

    @RequestMapping("/o")
    @ResponseBody
    public String o(String sid) throws Exception {

        Map<String,String> map = new HashMap();
        map.put("page", "1");
        map.put("rp", "50");
        map.put("sortname", "jndatetime");
        map.put("sortorder", "desc");
        //map.put("accquary", "72999");
        map.put("trjnquary", "7");

        //map.put("trjnquary", "7");
        String res = OkHttpUtil.post7(Url.PCARD_LOGIN,Url.PCARD_BILL,sid,map);

        return res;

    }
    public static int toInt(String s){
        return Integer.parseInt(s);
    }


    @RequestMapping("/p")
    @ResponseBody
    public String p() throws Exception {

        String res = OkHttpUtil.get(Url.SCHOOL_NEWS,null);
        Document doc = Jsoup.parse(res);
        Elements table = doc.select("table[class=\"winstyle27654\"]");
        if (table.size()<=0)
            return null;
        Elements trs = doc.select("tr[height=\"20\"]");
        //System.out.println(trs.text());
        if (trs.size()<=0)
            return null;
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


            newsDao.save(new News(as.get(0).text(),span.get(0).text(),table2.toString()));

        }

        return res;

    }



    @RequestMapping("/q")
    @ResponseBody
    public String q() throws Exception {

        String res = new Gson().toJson(newsDao.findAll());

        return res;

    }


    @RequestMapping("/r")
    @ResponseBody
    public String r(String sid,String sclass) throws Exception {

        String res = new Gson().toJson(examDao.findOwnBySClss(sclass,semesterDao.getCurrent()[0].getCurrent()));

        return res;

    }

    @RequestMapping("/s")
    @ResponseBody
    public String s() throws Exception {

        String res = "0";
        String sid = "1611030213";
        String pwd = "111222";
        Map<String, String> map = new HashMap<String, String>();
        map.put("WebUserNO", sid);
        map.put("Password", pwd);


        Document doc = Jsoup.parse(OkHttpUtil.post4(Url.JWC_LOGIN_7001, Url.JWC_TABLE_7001, map));
        StringBuffer sb = new StringBuffer();
        // 根据id获取table
        //Elements table = doc.getElementsByAttributeValue("height","100px");
        //height:100px
        // 使用选择器选择该table内所有的<tr> <tr/>
        //Elements trs = table.select("tr");
        //遍历该表格内的所有的<tr> <tr/>
        //Elements trs = doc.select("table").select("tr");
        Elements ops = doc.select("select[name=\"YearTermNO\"]").select("option");
        for (int i = ops.size()-1 ; i >=0;i--){
            Elements targrts = ops.get(i).getElementsByAttribute ("selected");
            if (targrts.size()>0){
                res = targrts.get(0).text();
            }


        }

        return res;

    }


    @RequestMapping("/t")
    @ResponseBody
    public Semester[] t() throws Exception {
        return semesterDao.getCurrent();
    }


    @RequestMapping("/u")
    @ResponseBody
    public void u() throws Exception {
        List<Course> courses = new ArrayList<>();
        long sid = 1611030101;
        while (sid<=1611030130){
            courses.add(new Course(sid,"实验-软件安全技术","李文超","405机房","12-13周","2018-2019学年第一学期",(byte) 4,(byte)5,(byte)2));
            courses.add(new Course(sid,"实验-软件项目管理","李艳杰","405机房","14-15周","2018-2019学年第一学期",(byte) 4,(byte)2,(byte)2));
            courses.add(new Course(sid,"实验-计算机组成原理","邓红卫","学院5楼实验室","14-16周","2018-2019学年第一学期",(byte) 4,(byte)5,(byte)2));
            courses.add(new Course(sid,"实验-软件建模技术","石元博","304机房","12-15周","2018-2019学年第一学期",(byte) 4,(byte)4,(byte)4));
            courses.add(new Course(sid,"实验-软件质量保证与测试技术","石元博","307机房","13-16周","2018-2019学年第一学期",(byte) 4,(byte)5,(byte)4));

            sid++;
        }

        courseDao.saveAll(courses);

    }


    @RequestMapping("/v")
    @ResponseBody
    public void v() throws Exception {

        String url = "http://www.lnpu.edu.cn/info/12723/190570.htm";

        Document doc2 = Jsoup.parse(OkHttpUtil.get(url,null));
        //原方案
        //Elements table2 = doc2.select("div[class=\"Section0\"]");
        Elements table2 = doc2.select("div[class=\"v_news_content\"]");
        System.out.println(table2.toString());
        System.out.println("--------------------------\n\n");

        System.out.println(table2.toString().replaceAll("<style>.*?</style>",""));




    }




}
