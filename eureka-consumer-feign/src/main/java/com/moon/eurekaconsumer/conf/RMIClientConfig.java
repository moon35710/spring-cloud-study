package com.moon.eurekaconsumer.conf;

import com.moon.api.service.RMIExService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class RMIClientConfig {
	@Bean(name = "rmiService")
	public RmiProxyFactoryBean initRmiProxyFactoryBean() {
		RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
		factoryBean.setServiceUrl("rmi://127.0.0.1:1099/rmiService");
		factoryBean.setServiceInterface(RMIExService.class);
		return factoryBean;
	}
}