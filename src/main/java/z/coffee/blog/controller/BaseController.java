package z.coffee.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import z.coffee.blog.comm.annotation.AnonymousAccess;

@RestController
public class BaseController {

    @RequestMapping("/test")
    @AnonymousAccess
    public void test(){
        System.out.println("init ");
    }
}
