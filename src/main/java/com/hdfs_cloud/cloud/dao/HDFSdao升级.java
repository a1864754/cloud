package com.hdfs_cloud.cloud.dao;

import com.hdfs_cloud.cloud.entities.Employee;
import com.hdfs_cloud.cloud.entities.HDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class HDFSdao升级 {

    private static Map<Integer, Employee> filelist = null;

    @Autowired
    private DepartmentDao departmentDao;

//    static {
//        filelist = new HashMap<Integer, Employee>();
//
//        filelist.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1, new Department(101, "D-AA")));
//        filelist.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1, new Department(102, "D-BB")));
//        filelist.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0, new Department(103, "D-CC")));
//        filelist.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0, new Department(104, "D-DD")));
//        filelist.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1, new Department(105, "D-EE")));
//    }


    private static Integer initId = 1006;

    public void save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(initId++);
        }

        employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
        filelist.put(employee.getId(), employee);
    }

    public Collection<Employee> getAll() {
        return filelist.values();
    }

    public Employee get(Integer id) {
        return filelist.get(id);
    }

    public void delete(Integer id) {
        filelist.remove(id);
    }
}