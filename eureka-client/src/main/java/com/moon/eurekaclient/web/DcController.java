package com.moon.eurekaclient.web;

import com.moon.eurekaclient.dao.UserRepository;
import com.moon.eurekaclient.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }
    @GetMapping("/save")
    public User save() {
        User user = new User();
        user.setName("test-"+Thread.currentThread().getName());

        return  userRepository.save(user);
    }
}