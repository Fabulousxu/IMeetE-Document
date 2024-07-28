package com.example.imeete.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
  @Autowired private MockMvc mockMvc;

  @Test
  public void getUserInfoTest1() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user?id=u1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.getBooleanValue("ok");
    assert res.getJSONObject("data").getString("userId").equals("u1");
    assert res.getJSONObject("data").getString("nickname").equals("user1");
  }

  @Test
  public void getUserInfoTest2() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user?id=u0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert !res.getBooleanValue("ok");
  }

  @Test
  public void getSelfInfoTest1() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user/self").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.getString("userId").equals("u1");
    assert res.getString("nickname").equals("user1");
  }

  @Test
  public void getSelfInfoTest2() throws Exception {
    assert mockMvc
        .perform(get("/user/self").cookie(new MockCookie("userId", "u0")))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(Charset.defaultCharset())
        .isEmpty();
  }

  @Test
  public void getUserPostTest1() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user/post?id=u1").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.getBooleanValue("ok");
    assert res.getJSONArray("data").size() == 5;
  }

  @Test
  public void getUserPostTest2() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user/post?id=u0").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert !res.getBooleanValue("ok");
  }

  @Test
  public void getSelfPostTest1() throws Exception {
    JSONArray res =
        JSONArray.parseArray(
            mockMvc
                .perform(get("/user/self/post").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.size() == 5;
  }

  @Test
  public void getSelfPostTest2() throws Exception {
    assert mockMvc
        .perform(get("/user/self/post").cookie(new MockCookie("userId", "u0")))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(Charset.defaultCharset())
        .isEmpty();
  }

  @Test
  public void getUserCollectTest1() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user/collect?id=u1").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.getBooleanValue("ok");
    assert res.getJSONArray("data").size() == 10;
  }

  @Test
  public void getUserCollectTest2() throws Exception {
    JSONObject res =
        JSONObject.parseObject(
            mockMvc
                .perform(get("/user/collect?id=u0").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert !res.getBooleanValue("ok");
  }

  @Test
  public void getSelfCollectTest1() throws Exception {
    JSONArray res =
        JSONArray.parseArray(
            mockMvc
                .perform(get("/user/self/collect").cookie(new MockCookie("userId", "u1")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()));
    System.out.print(res);
    assert res.size() == 10;
  }

  @Test
  public void getSelfCollectTest2() throws Exception {
    mockMvc
        .perform(get("/user/self/collect").cookie(new MockCookie("userId", "u0")))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(Charset.defaultCharset())
        .isEmpty();
  }
}
