package com.moon.eurekaclient.conf;

import com.moon.api.service.RMIExService;
import com.moon.eurekaclient.RMIExServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RMIConfig {
 
	@Autowired
	@Qualifier("rmiExServiceImpl")
	private RMIExServiceImpl serviceImpl;
	/**    
	 * 方法描述：   
	 * 注意事项：    
	 * @return 
	 * @Exception 异常对象 
	*/
	@Bean
	public RmiServiceExporter initRmiServiceExporter(){
		RmiServiceExporter exporter=new RmiServiceExporter();
		exporter.setServiceInterface(RMIExService.class);
		exporter.setServiceName("rmiService");
		exporter.setService(serviceImpl);
		exporter.setServicePort(6666);
		return exporter;
	}
}