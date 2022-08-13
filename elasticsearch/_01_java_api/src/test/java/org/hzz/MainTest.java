package org.hzz;

import org.hzz.entity.JobDetail;
import org.hzz.service.JobFullTextService;
import org.hzz.service.impl.JobFullTextServiceImpl;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MainTest {
    private static Logger logger = Logger.getLogger(MainTest.class.getName());
    private JobFullTextService jobFullTextService;

    @BeforeTest
    public void before(){
        logger.info("connect...");
        jobFullTextService = new JobFullTextServiceImpl();
    }

    @Test
    public void add() throws IOException {
        JobDetail jobDetail = new JobDetail();
        jobDetail.setId(5);
        jobDetail.setArea("Beijing");
        jobDetail.setCmp("CAU");
        jobDetail.setEdu("本科");
        jobDetail.setExp("五年工作经验");
        jobDetail.setTitle("java架构师");
        jobDetail.setJob_type("全职");
        jobDetail.setPv("3000次浏览");
        jobDetail.setJd("Java开发工程师");
        jobDetail.setSalary("60K/月");
        jobDetail.setAge(18);
        jobFullTextService.add(jobDetail);
    }

    @Test
    public void get() throws IOException {
        JobDetail jobDetail = jobFullTextService.findById(3l);
        logger.info(jobDetail.toString());
    }

    @Test
    public void update() throws IOException {
        JobDetail jobDetail = jobFullTextService.findById(3l);
        jobDetail.setTitle("前端研发工程师");
        jobFullTextService.update(jobDetail);
    }

    @Test
    public void delete() throws IOException {
        jobFullTextService.deleteById(3l);
    }

    @Test
    public void search() throws IOException {
//        List<JobDetail> jobDetails = jobFullTextService.searchByKeywords("java架构");
        List<JobDetail> jobDetails = jobFullTextService.searchByKeywords("开发");
        jobDetails.stream().forEach(System.out::println);
    }

    @Test
    public void searchByPage() throws IOException {
        Map<String, Object> resultMap = jobFullTextService.searchByPage("开发", 2, 1);
        System.out.println("一共查询到:" + resultMap.get("total").toString());

        ArrayList<JobDetail> content = (ArrayList<JobDetail>)resultMap.get("content");
        for (JobDetail jobDetail : content) {
            System.out.println(jobDetail);
        }
    }

    @Test
    public void searchByScroll1() throws IOException {
        Map<String, Object> resultMap = jobFullTextService.searchByScrollPage("开发", null, 1);
        System.out.println("scroll_id:" + resultMap.get("scroll_id").toString());
        ArrayList<JobDetail> content = (ArrayList<JobDetail>)resultMap.get("content");
        for (JobDetail jobDetail : content) {
            System.out.println(jobDetail);
        }
    }

    @Test
    public void searchByScroll2() throws IOException {
        Map<String, Object> resultMap = jobFullTextService.searchByScrollPage("开发", "DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAANT8WOGtYNzR3WERTQ1NaU1dRVWh0R2xNQQ==", 1);
        System.out.println("scroll_id:" + resultMap.get("scroll_id").toString());
        ArrayList<JobDetail> content = (ArrayList<JobDetail>)resultMap.get("content");
        for (JobDetail jobDetail : content) {
            System.out.println(jobDetail);
        }
    }

    @AfterTest
    public void after() throws IOException {
        logger.info("close");
        jobFullTextService.close();
    }
}
