package main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/home")
    public String sayHello() {
        return "HOME PAGE";
    } 

    @GetMapping("/admin/dashboard")
    public String admin() {
        return "ADMIN PAGE";
    } 
}
