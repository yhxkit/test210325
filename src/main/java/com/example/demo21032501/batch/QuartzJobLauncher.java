package com.example.demo21032501.batch;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
@Data
public class QuartzJobLauncher extends QuartzJobBean {


    @Value("${spring.batch.job.name}")
    private String jobName;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException { //컨텍스트 변경

        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
            JobLocator jobLocator = (JobLocator) applicationContext.getBean(JobLocator.class);
            JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean(JobLauncher.class);


            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();

            Job job = jobLocator.getJob(jobName);
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            log.debug("########### Status: " + jobExecution.getStatus());

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | NoSuchJobException | SchedulerException e) {
            e.printStackTrace();
        }
    }
}


