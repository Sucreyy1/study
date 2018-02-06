package sucre.yy.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 用流的方式访问hdfs
 */
public class HDFSStream {

    private FileSystem fs = null;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://192.168.26.128:9000"), conf, "root");
    }

    @Test
    public void testDownloadFileToLocal() throws IOException {
        //先获取一个文件的输入流--针对hdfs上的
        FSDataInputStream in = fs.open(new Path("/csvdata/population.csv"));
        //再构造一个文件的输出流--针对本地的
        FileOutputStream out = new FileOutputStream(new File("D:/test.csv"));
        //再将输入流中的数据输出到输出流
        IOUtils.copyBytes(in, out, 4096);
    }

    @Test
    public void testBlock() throws Exception {
        FSDataInputStream file = fs.open(new Path("/csvdata/population.csv"));
        //文件信息
        FileStatus[] listStatus = fs.listStatus(new Path("/csvdata/population.csv"));
        //获取文件的block信息
        BlockLocation[] fileBlockLocations = fs.getFileBlockLocations(listStatus[0], 0L, listStatus[0].getLen());
        //第一个block的长度
        long length = fileBlockLocations[0].getLength();
        //第一个block的起始偏移量
        long offset = fileBlockLocations[0].getOffset();
        System.out.println(fileBlockLocations.length);
        System.out.println(length);
        System.out.println(offset);
        file.read(offset, new byte[4096], 0, 4096);
    }
}
