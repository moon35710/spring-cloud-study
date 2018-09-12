package com.moon.eurekaconsumer;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 类说明    :MockMvc 测试web
 * 作者      :liuys
 * 日期      :2017/10/11  10:50
 * 版本号    : V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//开启web上下文测试
@WebAppConfiguration
@SpringBootTest
public class LoginControllerTest {
    //注入webApplicationContext
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    //设置mockMvc
    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void login(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", "liuys26");
            jsonObject.put("userPw", "123");
            jsonObject.put("cityCode", "801000");
            jsonObject.put("userType", "0");
            mockMvc.perform(MockMvcRequestBuilders.post("/api/login/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toJSONString())
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}