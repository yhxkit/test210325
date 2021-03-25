package com.example.demo21032501.batch;

import com.example.demo21032501.model.LMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class LMSJobConf {

    @Value("${spring.batch.job.name}")
    private String jobName;

    private JobBuilderFactory jobs;
    private StepBuilderFactory steps;

    private LMSReader lmsReader;
    private LMSWriter lmsWriter;
    private LMSProcessor lmsProcessor;


    public LMSJobConf(JobBuilderFactory jobs, StepBuilderFactory steps, LMSWriter lmsWriter, LMSReader lmsReader, LMSProcessor lmsProcessor){
        this.jobs = jobs;
        this.steps = steps;
        this.lmsReader = lmsReader;
        this.lmsWriter = lmsWriter;
        this.lmsProcessor = lmsProcessor;
    }

    @Bean
    public Job job() {
        return jobs.get(jobName)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    protected Step step1() {
        return steps.get(jobName + " >> step 01")
                .<LMS, LMS>chunk(1) // commit interval
                .reader(lmsReader)
                .processor(lmsProcessor)
                .writer(lmsWriter)
                .build();
    }



}
