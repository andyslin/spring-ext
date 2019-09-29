package org.autumn.spring.argsbind;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.autumn.spring.argsbind.rsa.RSAUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.autumn.spring.argsbind.rsa.RSAUtils.RSA_PAIR;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArgsBindApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/test")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(200, response.getStatus());

        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals("beforeBindPropertyValue", json.getString("beforeBindProperty"));
        Assert.assertEquals("afterBindPropertyValue", json.getString("afterBindProperty"));
    }

    @Test
    public void baseform() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/baseform")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(200, response.getStatus());

        JSONObject json = new JSONObject(response.getContentAsString());
        JSONObject base = json.getJSONObject("base");

        Assert.assertEquals("admin", base.getString("userId"));
        Assert.assertEquals("0000", base.getString("orgId"));
    }

    @Test
    public void configProperty() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/configProperty")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(200, response.getStatus());

        JSONObject json = new JSONObject(response.getContentAsString());

        Assert.assertEquals("configNameValue", json.getString("configProperty"));
    }

    @Test
    public void rsa() throws Exception {
        String src = "abadewew";//原始值
        // 模拟客户端使用RSA加密
        String encrypt = RSAUtils.encryptByPublicKey(src, RSA_PAIR[0]);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/rsaDecrypt").param("rsa", encrypt)).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(200, response.getStatus());

        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals(src, json.getString("rsa"));
    }

    @Test
    public void dateField() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/dateField").param("date2", "20190929")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(200, response.getStatus());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");//默认格式
        LocalDateTime dateTime = LocalDateTime.now();
        String yesterday = dateTime.minusDays(1).format(dateTimeFormatter);
        String theDayOfLastMonth = dateTime.minusMonths(1).format(dateTimeFormatter);

        JSONObject json = new JSONObject(response.getContentAsString());
        Assert.assertEquals("null", json.getString("date1"));
        Assert.assertEquals("2019-09-29", json.getString("date2"));
        Assert.assertEquals(yesterday, json.getString("date3"));
        Assert.assertEquals(theDayOfLastMonth, json.getString("date4"));
    }
}
