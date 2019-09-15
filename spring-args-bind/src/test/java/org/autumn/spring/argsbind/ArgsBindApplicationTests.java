package org.autumn.spring.argsbind;

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
}
