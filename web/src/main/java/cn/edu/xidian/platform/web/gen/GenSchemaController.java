package cn.edu.xidian.platform.web.gen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import cn.edu.xidian.platform.commons.entity.Message;
import cn.edu.xidian.platform.commons.utils.Exceptions;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenTable;
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

    @RequestMapping(value = "schemaForm",method = RequestMethod.GET)
    public ModelAndView genSchemaForm(){
        return new ModelAndView("gen/genSchemaForm");
    }

    @RequestMapping(value = "getSchemeList",method = RequestMethod.GET)
    public Message getSchemeList(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        PageHelper.startPage(genScheme.getPage(), genScheme.getPageSize());
        List<GenScheme> genSchemePageList = genSchemaService.findList(genScheme);
        PageInfo<GenScheme> pageInfo = new PageInfo<>(genSchemePageList);
        message.setMessage(pageInfo);
        logger.info(pageInfo);
        return message ;
    }


    @RequestMapping(value = "saveScheme",method = RequestMethod.PUT)
    public Message saveScheme(GenScheme genScheme) {
        logger.info("saving genscheme:"+ToStringBuilder.reflectionToString(genScheme));
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genSchemaService.saveOrUpdate(genScheme);
        return message;
    }

    @RequestMapping(value = "saveAndGenCode",method = RequestMethod.PUT)
    public Message saveAndGenCode(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genSchemaService.saveOrUpdateAndGen(genScheme);
        return message;
    }

    @RequestMapping(value = "deleteScheme",method = RequestMethod.DELETE)
    public Message deleteScheme(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genSchemaService.delete(genScheme);
        return message;
    }
}