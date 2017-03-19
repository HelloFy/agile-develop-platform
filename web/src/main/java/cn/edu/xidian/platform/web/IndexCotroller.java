package cn.edu.xidian.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import cn.edu.xidian.platform.gen.dao.IGenTableDao;

/**
 * Created by 费玥 on 2017-3-15.
 */
@RestController
public class IndexCotroller {

    @Autowired
    private IGenTableDao iGenTableDao;

    @RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
    public ModelAndView index(Map<String, Object> model){
        return new ModelAndView("index");
    }
}
