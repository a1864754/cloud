package com.hdfs_cloud.cloud.controller;

import com.hdfs_cloud.cloud.dao.EmployeeDao;
import com.hdfs_cloud.cloud.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

//@Controller
//public class HDFSController {
//    @Autowired
//    EmployeeDao employeeDao;
//    //查出所有员工
//    @GetMapping("/hdfslist")
//    public String list(Model model){
//        Collection<Employee> employees = employeeDao.getAll();
//
//        //放在请求域中共享
//        model.addAttribute("hdfslist",employees);
//        //System.out.println(employees);
//
//        return "hdfs_list/list";
//    }
//}
