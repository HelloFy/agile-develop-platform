/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.web.gen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.entity.Message;
import cn.edu.xidian.platform.commons.utils.Exceptions;
import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.entity.GenDoc;
import cn.edu.xidian.platform.gen.service.GenDocService;

/**
 * 文档模版Controller
 *
 * @author 费玥
 * @version 2017-04-06
 */
@RestController
@RequestMapping(value = "gen")
public class GenDocController {

    @Autowired
    private GenDocService genDocService;

    private static Logger logger = Logger.getLogger(GenDocController.class);


    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception ex) {
        Message message = new Message();
        logger.error(Exceptions.getStackTraceAsString(ex));
        message.setResult(Message.MessageResult.FAIL);
        message.setMessage("服务器繁忙");
        message.setErrorMsg("服务器繁忙");
        return message;
    }

    @RequestMapping(value = "getGenDocList", method = RequestMethod.GET)
    public Message getGenDocList(GenDoc genDoc) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        PageHelper.startPage(genDoc.getPage(), genDoc.getPageSize());
        List<GenDoc> genDocPageList = genDocService.findList(genDoc);
        PageInfo<GenDoc> pageInfo = new PageInfo<>(genDocPageList);
        message.setMessage(pageInfo);
        return message;
    }

    @RequestMapping(value = "docTplView", method = RequestMethod.GET)
    public ModelAndView docTplView() {
        return new ModelAndView("gen/genDocList");
    }

    @RequestMapping(value = "saveGenDoc", method = RequestMethod.PUT)
    public Message save(GenDoc genDoc) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genDocService.saveOrUpdate(genDoc);
        return message;
    }

    @RequestMapping(value = "deleteGendoc", method = RequestMethod.DELETE)
    public Message delete(GenDoc genDoc) throws IOException {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        Runtime.getRuntime().exec("dir");
        genDocService.delete(genDoc);
        return message;
    }

    @RequestMapping(value = "downLoadTpl", method = RequestMethod.GET)
    public void downLoadTpl(GenDoc genDoc, HttpServletRequest request, HttpServletResponse response) {
        GenDoc genDocDown = genDocService.get(genDoc);
        String fileName = StringUtils.replace(Global.getProjectPath(), "web", "gen") +
                File.separator + genDocDown.getDocPath();
        FileUtils.downFile(new File(fileName), request, response, genDocDown.getDocName());
    }

    @RequestMapping(value = "uploadDoc", method = RequestMethod.PUT)
    public Message uploadDoc(@RequestParam("fileupload") MultipartFile file, GenDoc genDoc) throws IOException {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genDocService.addTpl(file, genDoc);
        return message;
    }

}