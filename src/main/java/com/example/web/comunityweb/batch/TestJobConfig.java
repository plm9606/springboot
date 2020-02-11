package com.example.web.comunityweb.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class TestJobConfig {

    @Bean
    // job 실행에 필요한 joblauncher를 필드값으로 갖는 JobLauncherTestUtils를 빈으로 등록한다.
    public JobLauncherTestUtils jobLauncherTestUtils(){
        return  new JobLauncherTestUtils();
    }
}
