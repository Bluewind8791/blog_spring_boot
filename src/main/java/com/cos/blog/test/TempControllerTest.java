package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
    
    @GetMapping("/temp/jsp") // http:localhost:8000/blog/temp/home
    public String jspTest() {
        // prefix : /WEB-INF/views/
        // suffix : .jsp
        // fullName : /WEB-INF/views/test.jsp
        return "test";
    }
}
