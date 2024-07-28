package com.example.imeete.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.imeete.entity.Post;
import com.example.imeete.service.PostService;
import com.example.imeete.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
  @Autowired private UserService userService;
  @Autowired private PostService postService;

  @GetMapping
  public JSONObject getUserInfo(String id) {
    JSONObject res = new JSONObject();
    JSONObject data = userService.toJson(id);
    if (data == null) {
      res.put("ok", false);
      res.put("message", "用户不存在");
    } else {
      res.put("ok", true);
      res.put("message", "获取用户信息成功");
      res.put("data", data);
    }
    return res;
  }

  @GetMapping("/self")
  public JSONObject getSelfInfo(@CookieValue("userId") String userId) {
    return userService.toJson(userId);
  }

  @GetMapping("/post")
  public JSONObject getUserPost(String id, @CookieValue("userId") String userId) {
    JSONObject res = new JSONObject();
    List<Post> posts = userService.getPost(id);
    if (posts == null) {
      res.put("ok", false);
      res.put("message", "用户不存在");
    } else {
      res.put("ok", true);
      res.put("message", "获取用户动态成功");
      JSONArray data = new JSONArray();
      for (Post post : posts) data.add(postService.toJson(post, userId));
      res.put("data", data);
    }
    return res;
  }

  @GetMapping("/self/post")
  public JSONArray getSelfPost(@CookieValue("userId") String userId) {
    List<Post> posts = userService.getPost(userId);
    if (posts == null) return null;
    JSONArray res = new JSONArray();
    for (Post post : posts) res.add(postService.toJson(post, userId));
    return res;
  }

  @GetMapping("/collect")
  public JSONObject getUserCollect(String id, @CookieValue("userId") String userId) {
    JSONObject res = new JSONObject();
    List<Post> posts = userService.getCollect(id);
    if (posts == null) {
      res.put("ok", false);
      res.put("message", "用户不存在");
    } else {
      res.put("ok", true);
      res.put("message", "获取用户收藏成功");
      JSONArray data = new JSONArray();
      for (Post post : posts) data.add(postService.toJson(post, userId));
      res.put("data", data);
    }
    return res;
  }

  @GetMapping("/self/collect")
  public JSONArray getSelfCollect(@CookieValue("userId") String userId) {
    List<Post> posts = userService.getCollect(userId);
    if (posts == null) return null;
    JSONArray res = new JSONArray();
    for (Post post : posts) res.add(postService.toJson(post, userId));
    return res;
  }
}
