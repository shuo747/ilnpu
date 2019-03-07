package com.shuo747.ilnpu.controller;

import java.util.*;

import com.shuo747.ilnpu.entity.*;
import com.shuo747.ilnpu.entity.model.GradeSemesterModel;
import com.shuo747.ilnpu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * User 控制器.
 * 
 * @since 1.0.0 2017年7月9日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WxUserDao wxUserDao;
	@Autowired
	private UserinfoDao userinfoDao;
	@Autowired
	private StudentDao studentDao;
    @Autowired
    private GradeDao gradeDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private SemesterDao semesterDao;
	
	/**
	 * 查询所有用户
	 * @param model
	 * @return
	 */
	@GetMapping
	public ModelAndView list(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		model.addAttribute("title", "用户管理");
		return new ModelAndView("users/list","userModel",model);
	}



	/**
	 * 根据 id 查询用户
	 * @param openid
	 * @param model
	 * @return
	 */
	@GetMapping("{openid}")
	public ModelAndView view(@PathVariable("openid") String openid, Model model) {
		long l = wxUserDao.findSidByWxUserName(openid);
		Optional<Student> student = Optional.ofNullable(studentDao.findBySid(l));
        Optional<Userinfo> userinfo = userinfoDao.findById(openid);
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

        String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};
        List<List<Course>> listList = new ArrayList<>(7);
        for (int i = 0;i<7;i++){
            listList.add(new ArrayList<Course>());
        }
        String current = semesterDao.getCurrent()[0].getCurrent();
        Iterable<Course> courseList = courseDao.findAllBySid(l,current);
        for(Course c:courseList){
            System.out.println(c);
            listList.get(c.getDay()-1).add(c);
        }
		model.addAttribute("stu", student.get());
        model.addAttribute("user", userinfo.get());
        model.addAttribute("grades", list1);
        model.addAttribute("courses", listList);
        model.addAttribute("weeks", weeks);
		return new ModelAndView("users/test","userModel",model);
	}



    /**
     * 根据 id 查询用户
     * @param sid
     * @param model
     * @return
     */
    @GetMapping("s/{sid}")
    public ModelAndView view2(@PathVariable("sid") String sid, Model model) {
        long l = Long.parseLong(sid);
        Optional<Student> student = Optional.ofNullable(studentDao.findBySid(l));
        Optional<Userinfo> userinfo = null;
        try {
            userinfo = userinfoDao.findById(wxUserDao.findOpenidBySid(l));
            model.addAttribute("user", userinfo.get());
        } catch (Exception e) {

        }
        //Optional<Userinfo> userinfo = userinfoDao.findById("oG8AR0amsl1xEnkjVDXbF888p7R4");
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


        String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};
        List<List<Course>> listList = new ArrayList<>(7);
		for (int i = 0;i<7;i++){
			listList.add(new ArrayList<Course>());
		}
		String current = semesterDao.getCurrent()[0].getCurrent();
        Iterable<Course> courseList = courseDao.findAllBySid(l,current);
        for(Course c:courseList){
        	//System.out.println(c);
        	listList.get(c.getDay()-1).add(c);
		}

        model.addAttribute("stu", student.get());

        model.addAttribute("grades", list1);
		model.addAttribute("courses", listList);
        model.addAttribute("weeks", weeks);
        return new ModelAndView("users/test","userModel",model);
    }


	/**
	 * 根据 id 查询用户
	 * @param sid
	 * @param model
	 * @return
	 */
	@GetMapping("c/{sid}")
	public ModelAndView view3(@PathVariable("sid") String sid, Model model) {
		long l = Long.parseLong(sid);
		Optional<Student> student = Optional.ofNullable(studentDao.findBySid(l));
		//Optional<Userinfo> userinfo = userinfoDao.findById(wxUserDao.findOpenidBySid(l));
		Optional<Userinfo> userinfo = userinfoDao.findById("oG8AR0amsl1xEnkjVDXbF888p7R4");
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
		model.addAttribute("stu", student.get());
		model.addAttribute("user", userinfo.get());
		model.addAttribute("grades", list1);
		return new ModelAndView("users/test","userModel",model);
	}
	
	/**
	 * 获取创建表单页面
	 * @param model
	 * @return
	 */
	@GetMapping("/form")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user", new User(null, null, null));
		model.addAttribute("title", "创建用户");
		return new ModelAndView("users/form","userModel",model);
	}
	
	/**
	 * 保存或者修改用户
	 * @param user
	 * @return
	 */
	@PostMapping
	public ModelAndView saveOrUpdateUser(User user) {
		userRepository.save(user);
		return new ModelAndView("redirect:/users");// 重定向到 list页面
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return new ModelAndView("redirect:/users"); // 重定向到 list页面
	}
	
	/**
	 * 获取修改用户的界面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/modify/{id}")
	public ModelAndView modify(@PathVariable("id") Long id, Model model) {
		Optional<User> user = userRepository.findById(id);
		model.addAttribute("user", user.get());
		model.addAttribute("title", "修改用户");
		return new ModelAndView("users/form","userModel",model);
	}
}