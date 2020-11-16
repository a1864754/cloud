package com.hdfs_cloud.cloud.entities;

import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.ApplicationPath;
import java.util.Date;

@Data
@SpringBootApplication
public class HDFS {

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "HDFS_file_model{" +
                "fileName='" + fileName + '\'' +
                '}';
    }

    private String fileName;

}
