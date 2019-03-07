package com.shuo747.ilnpu.controller;


import com.google.gson.Gson;
import com.shuo747.ilnpu.entity.Course;
import com.shuo747.ilnpu.repository.CourseDao;
import com.shuo747.ilnpu.utils.okhttp.OkHttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/test")
//@Controller
@EnableAutoConfiguration//此注释自动载入应用程序所需的所有Bean
public class Test2Controller {


    @Autowired
    private CourseDao courseDao;





    @RequestMapping("1")
    @ResponseBody
    public String t1() throws IOException {
        String url = "http://202.118.120.84:7001/ACTIONQUERYCLASSROOMUSEBYWEEKDAYSECTION.APPPROCESS?mode=2";
        Document doc = Jsoup.connect(url).get();
        //Document doc = Jsoup.parse(S.s);
        Elements trs = doc.select("tr");
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<trs.size();i++){
            //Elements tds = trs.select("td");
            //for (;;);
            sb.append(trs.get(i).text()+"<br>");
        }
        return sb.toString();
        //return OkHttpUtil.get(url3,null);
    }

    @RequestMapping("2")
    @ResponseBody
    public String t2() throws IOException {
        String string = "<div class=\"Section0\" style=\"vsb_temp: 15.6000pt;\">\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">各位同学：</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 24pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan; mso-char-indent-count: 2.0000;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\">2019年全国硕士研究生招生考试报名工作即将开始，为指导我校学生科学报考，帮助同学们制定高效、合理的备考计划，掌握考研答题方法与技巧，提高学生考研成功率，校大学生学业发展指导中心经过精心筹备，决定连续举办四期考研指导讲座。</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 24pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">现将第四期相关安排通知如下：</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">题目：</span></span><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">备战考研数学</span>,助力梦想腾飞</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">主讲人：</span></span><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 1.0000pt;\"><span style=\"font-family: 宋体;\">聂宏</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">讲座时间：</span>2018年10月10日 &nbsp;下午3：30 — 5: 00</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">讲座地点：四教</span>A座202教室</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">欢迎广大同学踊跃参加！</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><strong><span class=\"16\" style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; font-weight: bold; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\"><span style=\"font-family: 宋体;\">内容简介：</span></span></strong></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\">1.<span style=\"font-family: 宋体;\">考研数学考纲解析；</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\">2.考研数学备考计划制定与实施；</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\">3.解密考研数学解题方法与应试技巧；</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: left; line-height: 150%; text-indent: 23.05pt; margin-top: 7.8pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 0.0000pt;\">4.考研数学真题剖析及复习策略。</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"line-height: 150%; text-indent: 216pt; margin-top: 5pt; margin-right: 24pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan; mso-char-indent-count: 18.0000;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 1.0000pt;\">&nbsp;</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: right; line-height: 150%; text-indent: 252pt; margin-top: 5pt; margin-right: 24pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan; mso-char-indent-count: 21.0000;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 1.0000pt;\"><span style=\"font-family: 宋体;\">大学生学业发展指导中心</span></span></p>\n" +
                " <p class=\"MsoNormal\" style=\"text-align: right; line-height: 150%; text-indent: 276pt; margin-top: 5pt; margin-right: 24pt; margin-bottom: 5pt; -ms-text-autospace: ideograph-numeric; mso-pagination: widow-orphan; mso-char-indent-count: 23.0000;\"><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 1.0000pt;\">2018年9月30日</span></p>\n" +
                " <p class=\"MsoNormal\" style=\"line-height: 150%; text-indent: 60.25pt; -ms-text-autospace: ideograph-numeric; mso-pagination: none; mso-char-indent-count: 5.0000;\"><strong><span style=\"line-height: 150%; font-family: 宋体; font-size: 12pt; font-weight: bold; mso-spacerun: &quot;yes&quot;; mso-font-kerning: 1.0000pt;\">&nbsp;</span></strong></p>\n" +
                "</div>";
        return string;
        //return OkHttpUtil.get(url3,null);
    }

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

    public static int toInt(String s){
        return Integer.parseInt(s);
    }

}
