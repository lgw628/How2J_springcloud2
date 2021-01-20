package qinyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import qinyan.pojo.Index;
import qinyan.service.IndexService;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/getIndexs")
    public List<Index> getIndex(){
        return indexService.get();
    }

    @GetMapping("/freshIndexs")
    public List<Index> freshIndex(){
        return indexService.fresh();
    }


    @GetMapping("/remove")
    public String removeIndex(){
        indexService.remove();
        return "redis中数据已经被删除";
    }

}
