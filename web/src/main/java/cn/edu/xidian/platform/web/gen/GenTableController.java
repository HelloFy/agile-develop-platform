package cn.edu.xidian.platform.web.gen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import cn.edu.xidian.platform.gen.entity.GenTable;
import cn.edu.xidian.platform.gen.service.GenTableService;

/**
 * Created by 费玥 on 2017-3-17.
 */

@RestController
@RequestMapping("/gen")
public class GenTableController {

    @Autowired
    private GenTableService genTableService;

    @RequestMapping("tableForm")
    public ModelAndView genTableFormView(Model model){
        model.addAttribute("tables",genTableService.findAllTableList());
        return new ModelAndView("gen/genTableForm");
    }

    @RequestMapping("getAllTables")
    public void getAllTables(){
        List<GenTable> genTables = genTableService.findAllTableList();
    }
}
