package com.example.web.comunityweb.batch;

import com.example.web.comunityweb.domain.enums.UserStatus;
import com.example.web.comunityweb.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InactiveUserTestWithParameter {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 휴면회원_전환_test_with_params() throws Exception{
        // Date 타입은 JobParameter에서 허용하는 파라미터 중 하나이다
        Date nowDate = new Date();

        System.out.println("Inactive user count: " + userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

        //job 실행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                // JobParametersBuilder를 사용하면 간편하게 JobParameters를 생성할 수 있다.
                // JobParameters는 여러개의 JobParameter를 받는 객체이다.
                // JobLauncher를 사용하려면 JobParameters가 필요
                new JobParametersBuilder().addDate("nowDate", nowDate)
                .toJobParameters()
        );

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

    }
}
