package com.cw.faquir.core.controller;


import com.cw.faquir.core.domain.pojo.database.DataBaseSlave;
import com.cw.faquir.core.domain.pojo.entity.TSingle;
import com.cw.faquir.core.service.SingleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * CRUD 测试控制器
 * @author Lao SiCheng
 * @version 0.1
 */

@RestController
//@RequestMapping("/test")
public class SingleController {

    @Autowired
    private SingleService singleService;

    @Autowired
    private DataBaseSlave entity;
    @GetMapping("/{id}/single")
    public String getSingle(@PathVariable("id") Integer id){
        // todo check data
        TSingle single = singleService.getSingle(id);
        System.out.println(entity.getDatabase());

        return single.getField();
    }

    @GetMapping("/{id}/{field}/single")
    public int updateSingle(@PathVariable("id") Integer id, @PathVariable String field){
        TSingle single = new TSingle();
        single.setId(id);
        single.setField(field);
        return singleService.updateSingle(single);
    }

    @RequestMapping(value = "/get",method = RequestMethod.POST, consumes = "text/plain")
//    @ApiImplicitParam(name="student", value = "查询", paramType = "Student", dataType = "body")
    public Student get(Student student){


        return student;
    }
}

@Data
class Student{
    Integer id;
    String name;
        }