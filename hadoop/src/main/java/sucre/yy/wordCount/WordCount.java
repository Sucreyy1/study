package sucre.yy.wordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCount {

    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        /**
         * 框架每传一行数据,则调用一次这个mapper
         * 将输入mapper的数据切分成单词并计数1
         *
         * @param key     这一行的起始点在文件中的偏移量
         * @param value   这一行的内容
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //获取输入的数据
            String line = value.toString();
            //将这一行切分出各个单词
            String[] words = line.split(" ");
            //遍历数组,输出<单词,1>
            for (String word : words) {
                context.write(new Text(word), new IntWritable(1));
            }
        }
    }

    public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        /**
         * 框架每传进来一个kv组,reduce方法就被调用一次
         *
         * @param key     mapper输出的key
         * @param values  mapper输出的value
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable value : values) {
                count += value.get();
            }
            context.write(new Text(key), new IntWritable(count));
        }
    }

    /**
     * main方法来定义和启动这个mr程序
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        /*
        把业务逻辑相关的信息,比如哪个是mapper,哪个是reducer,要处理的数据在哪里,输入的结果放在哪里.
         */
        Configuration conf = new Configuration();
        String yarnip = "192.168.26.128";
        conf.set("fs.defaultFS","hdfs://192.168.26.128:9000");
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("yarn.resourcemanager.address", yarnip+":"+8032);
        conf.set("yarn.resourcemanager.scheduler.address", yarnip+":"+8030);

        Job wcjob = Job.getInstance(conf);
        wcjob.setJar("/home/hadoop/wordCount.jar");
        wcjob.setJarByClass(WordCount.class);

        wcjob.setMapperClass(WordCountMapper.class);
        wcjob.setReducerClass(WordCountReducer.class);
        //设置我们的业务逻辑mapper类的输出key和value的数据类型
        wcjob.setMapOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(IntWritable.class);
        //设置我们的业务逻辑reducer类的输出key和value的数据类型
        wcjob.setOutputKeyClass(Text.class);
        wcjob.setMapOutputValueClass(IntWritable.class);

        //指定要处理的数据所在的位置
        FileInputFormat.setInputPaths(wcjob, "hdfs://Sucre:9000/wordcount/data/big.txt");
        //指定处理完之后的结果所保存的位置
        FileOutputFormat.setOutputPath(wcjob, new Path("hdfs://Sucre:9000/wordcount/output/"));

        //向yarn集群提交这个job
        boolean b = wcjob.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}