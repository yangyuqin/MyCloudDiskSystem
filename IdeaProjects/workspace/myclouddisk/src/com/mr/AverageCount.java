package com.mr;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

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

/**
 * Created by yyq on 11/26/15.
 */
public class AverageCount {

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            System.out.println("line:" + line);

            System.out.println("TokenizerMapper.map...");
            System.out.println("Map key:" + key.toString() + " Map value:" + value.toString());
            //将输入的数据首先按行进行分割
            StringTokenizer tokenizerArticle = new StringTokenizer(line, "\n");
            //分别对每一行进行处理
            while (tokenizerArticle.hasMoreTokens()) {
                //每行按空格划分
                StringTokenizer tokenizerLine = new StringTokenizer(tokenizerArticle.nextToken());
                String strName = tokenizerLine.nextToken();//学生姓名部分
                String strScore = tokenizerLine.nextToken();//成绩部分

                Text name = new Text(strName);
                int scoreInt = Integer.parseInt(strScore);

                System.out.println("name:" + name + "  scoreInt:" + scoreInt);

                context.write(name, new IntWritable(scoreInt));
                System.out.println("context_map:" + context.toString());
            }
            System.out.println("context_map_111:" + context.toString());
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            int score = 0;
            System.out.println("reducer...");
            System.out.println("Reducer key:" + key.toString() + "  Reducer values:" + values.toString());
            //设置迭代器
            Iterator<IntWritable> iterator = values.iterator();
            while (iterator.hasNext()) {
                score = iterator.next().get();
                System.out.println("score:" + score);
                sum += score;
                count++;

            }
            int average = (int) sum / count;
            System.out.println("key" + key + "   average:" + average);
            context.write(key, new IntWritable(average));
            System.out.println("context_reducer:" + context.toString());
        }
    }

    public static void averagecountTest(String path01, String path02) throws Exception {
        try{
            Configuration conf = new Configuration();
            Job job = new Job(conf, "score count");
            job.setJarByClass(AverageCount.class);

            job.setMapperClass(Map.class);
            job.setCombinerClass(Reduce.class);
            job.setReducerClass(Reduce.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job, new Path(path01));
            FileOutputFormat.setOutputPath(job, new Path(path02));

            job.waitForCompletion(true) ;
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
