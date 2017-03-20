package cn.edu.xidian.platform.web.gen;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.xidian.platform.commons.entity.Message;
import cn.edu.xidian.platform.commons.utils.Exceptions;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.service.GenSchemaService;

/**
 * Created by 费玥 on 2017-3-20.
 */
@RestController
@RequestMapping("/gen")
public class GenSchemaController {

    private static Logger logger = Logger.getLogger(GenSchemaController.class);

    @Autowired
    private GenSchemaService genSchemaService;

    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception ex) {
        logger.error(Exceptions.getStackTraceAsString(ex));
        Message message = new Message();
        message.setResult(Message.MessageResult.FAIL);
        message.setMessage("服务器繁忙");
        message.setErrorMsg("服务器繁忙");
        return message;
    }

    @RequestMapping("schemaForm")
    public ModelAndView genSchemaForm(){
        return new ModelAndView("gen/genSchemaForm");
    }

    @RequestMapping("saveScheme")
    public Message saveScheme(GenScheme genScheme) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genSchemaService.saveOrUpdate(genScheme);
        return message;
    }

}
