package com.moon.eurekaconsumer;

import com.moon.api.service.RMIExService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaConsumerFeignApplicationTests {
    @Autowired
    @Qualifier("rmiService")
    private RmiProxyFactoryBean factoryBean;
//    @Autowired
//    private  RMIExService rmiExService;
    @Test
    public  void test(){
        RMIExService service=(RMIExService)factoryBean.getObject();
        System.out.println(service.invokingRemoteService());
//        rmiExService.invokingRemoteService();
    }
    @Test
    public void contextLoads() {
    }

}
