package com.moon.eurekaclient;

import com.moon.api.service.RMIExService;
import com.moon.eurekaclient.dao.UserRepository;
import com.moon.eurekaclient.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaClientApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Qualifier("rmiService")
    private RmiProxyFactoryBean factoryBean;
    @Test
    public  void test(){
        RMIExService service=(RMIExService)factoryBean.getObject();
        System.out.println(service.invokingRemoteService());
    }

    @Test
    public void add() throws InterruptedException {
        int count = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        CountDownLatch finshC = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {

            new Thread(() -> {
                User user = new User();
                user.setName("test-" + Thread.currentThread().getName());
                userRepository.save(user);
                finshC.countDown();
            }).start();
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().join();
        finshC.await();
        Thread.currentThread().notify();

    }

    @Test
    public void query() throws InterruptedException {
        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                List<User> users = userRepository.findAll();
                users.forEach(e -> {
                    System.out.println(Thread.currentThread().getName() + e.getId());
                    System.out.println(e.getName());
                });
            }).start();
            countDownLatch.countDown();
        }
        countDownLatch.await();
        Thread.currentThread().join();

    }

    @Test
    public void contextLoads() {
        System.out.println(jdbcTemplate.getDataSource());
        jdbcTemplate.execute("select *from tb_emp");
        jdbcTemplate.query("select *from tb_emp", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                System.out.println(resultSet.getObject(i + 1));
                return null;
            }
        });
    }

}
