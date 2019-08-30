package com.cw.faquir.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lao SiCheng
 * @version 0.1
 */
@RestController
public class TestController {

    @GetMapping("/testWeb1")
    public String testWeb1(){
        int i = 1 / 0;
        return "ok";
    }

}
