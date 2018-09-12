package com.moon.eurekaclient;

import com.moon.api.service.RMIExService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service(value="rmiExServiceImpl")
public class RMIExServiceImpl implements RMIExService {
 
	@PostConstruct
	public void initMethod(){
		System.out.println("我是初始化方法时调用的");
	}
	@Override
	public String invokingRemoteService() {
		// TODO Auto-generated method stub
		String result="欢迎你调用远程接口";
		return result;
	}
 
}
