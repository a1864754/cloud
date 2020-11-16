package com.hdfs_cloud.cloud.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class Upload {
    public static final String FS_DEFAULT_FS = "fs.defaultFS";
    public static final String HDFS_HOST = "hdfs://49.232.221.239:9000";
    public static final String CROSS_PLATFORM = "mapreduce.app-submission.cross-platform";


    public static void main(String[] args) throws IOException {

        Configuration conf = new Configuration();

        conf.setBoolean(CROSS_PLATFORM, true);
        conf.set(FS_DEFAULT_FS, HDFS_HOST);

        GenericOptionsParser optionsParser = new GenericOptionsParser(conf, args);

        String[] remainingArgs = optionsParser.getRemainingArgs();
        if (remainingArgs.length < 2) {
            System.err.println("Usage: upload <source> <dest>");
            System.exit(2);
        }

        Path source = new Path(args[0]);
        Path dest = new Path(args[1]);

        FileSystem fs = FileSystem.get(conf);

        fs.copyFromLocalFile(true, false, source, dest);
    }
}
