/**
 * 
 */
package com.shuo747.ilnpu.controller;

import com.google.gson.Gson;
import com.shuo747.ilnpu.entity.News;
import com.shuo747.ilnpu.entity.Student;
import com.shuo747.ilnpu.entity.Userinfo;
import com.shuo747.ilnpu.repository.NewsDao;
import com.shuo747.ilnpu.repository.StudentDao;
import com.shuo747.ilnpu.repository.UserinfoDao;
import com.shuo747.ilnpu.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.*;

/**
 * Main Controller.
 * 
 * @since 1.0.0 2017年7月26日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Controller
public class MainController {

	@Autowired
	private UserinfoDao userinfoDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private NewsDao newsDao;




    /*@RequestMapping("/error")
    @ResponseBody
    public String errpr() {
        return "error";
    }*/
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	@GetMapping("/index")
	public String index(Model model) {
		//List<List<Userinfo>> u = ListUtil.split(userinfoDao.findAll(),4);
		//model.addAttribute("u",u);
		return "index";
	}

    @GetMapping("/allWxUser")
    public String allWxUser(Model model) {
        List<List<Userinfo>> u = ListUtil.split(userinfoDao.findAll(),4);
        model.addAttribute("u",u);
        return "allwxuser";
    }

    @RequestMapping("/search2")
    public String search2(Model model,String uuu) {
        long l = 0;
        if (uuu==null||uuu.length()==0) return "index";
        List<Student> students = studentDao.findAll();
        List<Student> studentsResult = new ArrayList<>();
        Set<Student> studentSet = new HashSet<>();
        try {
            l = Long.parseLong(uuu);
            model.addAttribute("stu",studentDao.findBySid(l));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.out.println("------非纯数字");
            String [] strings = uuu.split("\\s");
            System.out.println(strings.length);
            if (strings.length==1){
                for (int i = 0;i < students.size();i++){
                    if (equalsStu(students.get(i),strings[0]))
                        studentSet.add(students.get(i));
                }
            }
            if (strings.length>1)
            for (String s : strings){
                for (int i = 0;i < students.size();i++){
                    if (equalsStu(students.get(i),strings))
                        studentSet.add(students.get(i));
                }

            }

            model.addAttribute("stu",studentSet);
        }
        catch (Exception e){
            return "test";
        }finally {
            model.addAttribute("uuu",uuu);
        }
        //studentDao.findBySid(l);

        System.out.println(uuu);
        //model.addAttribute("u",u);
        return "test";
    }


    @RequestMapping("/search")
    public String search(Model model,String uuu) {
        long l = 0;
        if (uuu==null||uuu.length()==0) return "redirect:/admin/stu";
        List<Student> students = studentDao.findAll();
        Set<Student> studentSet = new HashSet<>();

            String [] strings = uuu.split("\\s");
            System.out.println(strings.length);

                for (String s : strings) {
                    for (int i = 0; i < students.size(); i++) {
                        if (equalsStu(students.get(i), strings))
                            studentSet.add(students.get(i));
                    }
                }
        model.addAttribute("uuu",uuu);
        model.addAttribute("stu",studentSet);

        //model.addAttribute("u",u);
        return "test";
    }


    public static boolean equalsStu(Student student,String uuu) {
	    if (student==null) return false;
        boolean flag = false;
        if (student.getName().contains(uuu)) flag = true;
        if (student.getCollege().contains(uuu)) flag = true;
        if (student.getIdcard().contains(uuu)) flag = true;
        if (student.getMajor().contains(uuu)) flag = true;
        if (student.getSclass().contains(uuu)) flag = true;
        if (student.getSgrade().contains(uuu)) flag = true;
        if (student.getSex().contains(uuu)) flag = true;
        return flag;
    }

    public static boolean equalsStu(Student student,String []uuus) {
        if (student==null) return false;
        boolean[] flag = new boolean[uuus.length];
        boolean flagFinal = false;
        for(int i = 0;i<uuus.length;i++){
            String uuu = uuus[i];
            if (String.valueOf(student.getSid()).contains(uuu)) flag[i] = true;
            if (student.getName().contains(uuu)) flag[i] = true;
            if (student.getCollege().contains(uuu)) flag[i] = true;
            if (student.getIdcard().contains(uuu)) flag[i] = true;
            if (student.getMajor().contains(uuu)) flag[i] = true;
            if (student.getSclass().contains(uuu)) flag[i] = true;
            if (student.getSgrade().contains(uuu)) flag[i] = true;
            if (student.getSex().contains(uuu)) flag[i] = true;
        }

        for (int i = 0;i<flag.length;i++){
            if (i==0){
                flagFinal = flag[i];
            }
            flagFinal = flagFinal&&flag[i];

        }

        return flagFinal;
    }

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
		return "login";
	}


    @RequestMapping("/admin/stu")
    public String root(Model model){

        model.addAttribute("stu",studentDao.findAll());

        //String res = new Gson().toJson(newsDao.findAll());

        //return "redirect:/pages/index.html";
        return "test";

    }


    @ResponseBody
    @RequestMapping("/admin/saveCourse")
    public boolean saveCourse(Model model){

        model.addAttribute("stu",studentDao.findAll());

        //String res = new Gson().toJson(newsDao.findAll());

        //return "redirect:/pages/index.html";
        return false;

    }

    //@RequestMapping("/admin/log")
    public String toArrayByFileReader2(Model model) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader("log/ilnpu.log");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            //int i = 0;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
                //System.out.println(str);
                //i++;
                //if (i>20)
                //    break;
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(arrayList);
        List<MyLog> myLogs = new ArrayList<>(100);
        //arrayList = (ArrayList<String>) arrayList.subList(0,20<arrayList.size()?20:arrayList.size()-1);
        for (int i = 0;i<arrayList.size();i++){
            myLogs.add(new MyLog(arrayList.get(i)));
            if (i>100)
                break;
        }

        model.addAttribute("log",myLogs);
        // 返回数组
        return "users/log";
    }

    @RequestMapping("/admin/log")
    public String toArrayByFileReader1(Model model) {
        int line = 100;
        // 使用ArrayList来存储每行读取到的字符串
        List<String> arrayList = readLastNLine(new File("log/ilnpu.log"),line);
       /* //Collections.reverse(arrayList);
        List<MyLog> myLogs = new ArrayList<>(100);
        //arrayList = (ArrayList<String>) arrayList.subList(0,20<arrayList.size()?20:arrayList.size()-1);
        for (int i = 0;i<arrayList.size();i++){
            myLogs.add(new MyLog(arrayList.get(i)));
            if (i>100)
                break;
        }*/

        model.addAttribute("log",arrayList);
        // 返回数组
        return "users/log";
    }




    public static List<String> readLastNLine(File file, long numRead)
    {
        // 定义结果集
        List<String> result = new ArrayList<String>();
        //行数统计
        long count = 0;

        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead())
        {
            return null;
        }

        // 使用随机读取
        RandomAccessFile fileRead = null;
        try
        {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L)
            {
                return result;
            }
            else
            {
                //初始化游标
                long pos = length - 1;
                while (pos > 0)
                {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n')
                    {
                        //使用readLine获取当前行
                        String line = new String(fileRead.readLine().getBytes("ISO-8859-1"),"utf-8");
                        //保存结果
                        result.add(line);

                        //打印当前行
                        //System.out.println(line);

                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead)
                        {
                            break;
                        }
                    }
                }
                if (pos == 0)
                {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fileRead != null)
            {
                try
                {
                    //关闭资源
                    fileRead.close();
                }
                catch (Exception e)
                {
                }
            }
        }

        return result;
    }
    class MyLog{
        String date = "";
        String status = "";
        String value = "";

        public MyLog(String value) {
            this.value = value;
            try {
                this.date = value.substring(11,24);
                this.status = value.substring(25,29);
            } catch (Exception e) {
            }
        }

        public String getDate() {
            return date;
        }

        public String getStatus() {
            return status;
        }

        public String getValue() {
            return value;
        }
    }



    @RequestMapping("/news")
    public String news(Model model){

        try {
            List<News> news = newsDao.findAllOrderByTime();
            List<News> newsRes;
            if (news.size()>15){
                model.addAttribute("news",news.subList(0,14));
                return "news";
            }
            else {
                model.addAttribute("news",news.subList(0,news.size()-1));
                return "news";
            }

        }
        catch (Exception e){

        }


        return "index";

    }

}