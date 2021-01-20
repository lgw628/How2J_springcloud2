package qinyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import qinyan.config.IpConfig;
import qinyan.pojo.Index;
import qinyan.service.IndexService;

import java.util.List;

@RestController
@CrossOrigin //允许跨域
public class IndexController {
    @Autowired
    IndexService indexService;

    @Autowired
    IpConfig ipConfig;

    @GetMapping("/codes")
    public List<Index> get(){
        System.out.println("当前的端口号："+ipConfig.getPort());
        return indexService.get();
    }
}
