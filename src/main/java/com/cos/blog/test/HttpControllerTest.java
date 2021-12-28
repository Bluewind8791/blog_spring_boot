package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
    
    // internet brower request only apply "get" request
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "Get request : " + m.getId() +"/"+ m.getUsername() +"/"+ m.getPassword() +"/"+ m.getEmail();
    }

    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) {
        return "Post request : " + m.getId() +"/"+ m.getUsername() +"/"+ m.getPassword() +"/"+ m.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "Put request" + m.getId() +"/"+ m.getUsername() +"/"+ m.getPassword() +"/"+ m.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "Delete request";
    }
}
