package com.hdfs_cloud.cloud.dao;

import com.hdfs_cloud.cloud.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public interface UserMapper {
    @Insert("INSERT INTO user(NAME,PASSWORD) VALUES(#{name},#{password})")
    int saveUser(@Param("name") String name, @Param("password") String password);

    @Select("select id,password from user where name=#{name}")
    User selectUser(@Param("name") String name);

}
