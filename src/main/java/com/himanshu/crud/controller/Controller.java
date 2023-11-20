package com.himanshu.crud.controller;

import com.himanshu.crud.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping(value = "/")
    public String getInfo(){
        return "Hello";
    }




}
