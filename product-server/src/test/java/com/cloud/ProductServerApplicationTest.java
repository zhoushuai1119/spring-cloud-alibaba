package com.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(properties = {"spring.profiles.active=dev"})
@RunWith(SpringRunner.class)
@Slf4j
public class ProductServerApplicationTest {

    @Test
    public void contextLoads() {
        System.out.println("aa");
    }

}

