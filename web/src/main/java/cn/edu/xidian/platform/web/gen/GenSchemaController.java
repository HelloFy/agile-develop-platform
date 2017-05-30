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
import cn.edu.xidian.platform.gen.service.GenTableSchemaService;

/**
 * Created by 李婧 on 2017-3-20.
 */
@RestController
@RequestMapping("/gen")
public class GenSchemaController {

    private static Logger logger = Logger.getLogger(GenSchemaController.class);

    @Autowired
    private GenTableSchemaService genTableSchemaService;

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

    @RequestMapping(value = "getSchema", method = RequestMethod.GET)
    public Message getSchema(GenScheme genScheme) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableSchemaService.get(genScheme));
        return message;
    }

    @RequestMapping(value = "getSchemaByRefId", method = RequestMethod.GET)
    public Message getSchemaByRefId(GenScheme genScheme) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableSchemaService.getByRefId(genScheme.getRefId(), genScheme.getRefType()));
        return message;
    }

    @RequestMapping(value = "getSchemeList",method = RequestMethod.GET)
    public Message getSchemeList(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        PageHelper.startPage(genScheme.getPage(), genScheme.getPageSize());
        List<GenScheme> genSchemePageList = genTableSchemaService.findList(genScheme);
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
        genTableSchemaService.saveOrUpdate(genScheme);
        return message;
    }

    @RequestMapping(value = "saveAndGenCode",method = RequestMethod.PUT)
    public Message saveAndGenCode(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableSchemaService.saveOrUpdateAndGen(genScheme));
        return message;
    }

    @RequestMapping(value = "deleteScheme",method = RequestMethod.DELETE)
    public Message deleteScheme(GenScheme genScheme){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genTableSchemaService.delete(genScheme);
        return message;
    }
}
