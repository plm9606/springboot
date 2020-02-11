package com.example.web.comunityweb.batch;

import com.example.web.comunityweb.domain.enums.UserStatus;
import com.example.web.comunityweb.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InactiveUserTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 휴면회원_전환_test() throws Exception{
        System.out.println("Inactive user count: " + userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

        //job 실행
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());

    }
}
