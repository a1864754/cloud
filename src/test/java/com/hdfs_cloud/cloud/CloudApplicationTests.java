package com.hdfs_cloud.cloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.awt.dnd.DropTarget;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;

@SpringBootTest
class CloudApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
