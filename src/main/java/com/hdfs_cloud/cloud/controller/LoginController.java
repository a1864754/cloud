package com.hdfs_cloud.cloud.controller;

import com.hdfs_cloud.cloud.dao.UserMapper;
import com.hdfs_cloud.cloud.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    //有简单的做法@RequestMapping(value = "/user/login" ,method = RequestMethod.POST)
    //还有PutMapping......
    /*@PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            //重定向防止表单重复提交
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }
        map.put("msg","用户名密码错误");
        return "login";
    }*/

    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        User user=userMapper.selectUser(username);
        if (Objects.equals(password, user.getPassword())) {
            //重定向防止表单重复提交
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }
        map.put("msg","用户名密码错误");
        return "login";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Map<String,Object> map){

        if (StringUtils.isEmpty(username)) {
            map.put("msg","用户名不许为空");
            return "register";
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg","密码不许为空");
            return "register";
        }
        User user=userMapper.selectUser(username);
        if (user!=null){
            map.put("msg","用户名已存在");
            return "register";
        }
        int resultCount = userMapper.saveUser(username, password);
        if (resultCount == 0) {
            map.put("msg","注册失败");
            return "register";
        }
        map.put("msg","注册成功，请登录");
        return "login";
    }




}
