package cn.edu.xidian.platform.web.gen;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 费玥 on 2017-3-20.
 */
@RestController
@RequestMapping("/gen")
public class GenSchemaController {

    @RequestMapping("schemaForm")
    public ModelAndView genSchemaForm(){
        return new ModelAndView("gen/genSchemaForm");
    }

}
