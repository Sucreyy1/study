package sucre.yy.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

public class HDFSClient {

    FileSystem fs = null;

    @Before
    public void init() throws Exception {
        //new Configuration()的时候.它就会去加载jar包中的hdfs-default.xml
        Configuration conf = new Configuration();
        //参数优先级:1.客户端代码中设置的值 2.classpath下的用户自定义配置文件 3.服务器的默认配置
        conf.set("fs.defaultFS", "hdfs://192.168.26.128:9000");
        conf.set("dfs.replication", "1");
        fs = FileSystem.get(conf);
        //第二种获取方式,如果这样获取,conf里面就可以不用配"fs.defaultFS"参数,而且这个客户端的身份标识已经是root用户
        //fs = FileSystem.get(new URI("hdfs://192.168.26.128:9000"), conf, "root");
    }

    /**
     * 将本地文件上传至hdfs
     * @throws Exception
     */
    @Test
    public void testAddFileToHdfs() throws Exception{
        //本地需要上传的文件
        Path src = new Path("D:\\data-csv\\population.csv");
        //hdfs目标目录
        Path targetPath=new Path("/csvdata");
        fs.deleteOnExit(targetPath);
        fs.copyFromLocalFile(src,targetPath);
        fs.close();
    }

    /**
     * 将hdfs文件拷贝至本地目录
     * @throws IOException
     */
    @Test
    public void testDownloadFileToLocal() throws IOException {
        fs.copyToLocalFile(new Path("/csvdata/"),new Path("D:/data-csv/xx.csv"));
        fs.close();
    }

    /**
     * 创建,删除,重命名目录或者文件
     * @throws IOException
     */
    @Test
    public void testMkdirAndDeleteAndRename() throws IOException {
        //创建目录
        fs.mkdirs(new Path("/a/b/c"));
        //删除文件夹,如果是非空文件夹,参数2必须为true
        fs.delete(new Path("/a/b/c"),true);
        //重命名文件或者文件夹
        fs.rename(new Path("/a"),new Path("/a2"));
        fs.close();
    }

}
