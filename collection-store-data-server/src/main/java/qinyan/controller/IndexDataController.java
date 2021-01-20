package qinyan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import qinyan.pojo.IndexData;
import qinyan.service.IndexDataService;

import java.util.List;

@RestController
public class IndexDataController {
    @Autowired
    IndexDataService indexDataService;

    @GetMapping("/getIndexData/{code}")
    public List<IndexData> get(@PathVariable("code") String code){
        return indexDataService.get(code);
    }

    @GetMapping("/freshIndexData/{code}")
    public List<IndexData> fresh(@PathVariable("code") String code){
        return indexDataService.fresh(code);
    }

    @GetMapping("/removeIndexData/{code}")
    public String remove(@PathVariable("code") String code) throws Exception {
        indexDataService.remove(code);
        return "remove index data successfully";
    }
}
