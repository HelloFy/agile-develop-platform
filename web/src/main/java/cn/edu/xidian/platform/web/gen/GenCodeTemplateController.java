/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.web.gen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.gen.entity.GenCodeTemplate;
import cn.edu.xidian.platform.gen.service.GenCodeTemplateService;

/**
 * 代码模版Controller
 * @author 李婧
 * @version 2017-04-18
 */
@RestController
@RequestMapping(value = "gen")
public class GenCodeTemplateController {

    @Autowired
    private GenCodeTemplateService genCodeTemplateService;


    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception ex) {
        Message message = new Message();
        message.setResult(Message.MessageResult.FAIL);
        message.setMessage("服务器繁忙");
        message.setErrorMsg("服务器繁忙");
        return message;
    }

    @RequestMapping(value = "getGenCodeTemplateList", method = RequestMethod.GET)
    public Message getGenCodeTemplateList(GenCodeTemplate genCodeTemplate) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        PageHelper.startPage(genCodeTemplate.getPage(), genCodeTemplate.getPageSize());
        List<GenCodeTemplate> genCodeTemplatePageList = genCodeTemplateService.findList(genCodeTemplate);
        PageInfo<GenCodeTemplate> pageInfo = new PageInfo<>(genCodeTemplatePageList);
        message.setMessage(pageInfo);
        return message;
    }

    @RequestMapping(value = "genCodeTemplateForm", method = RequestMethod.GET)
    public ModelAndView form() {
        return new ModelAndView("gen/genCodeTemplateForm");
    }

    @RequestMapping(value = "saveGenCodeTemplate", method = RequestMethod.PUT)
    public Message save(GenCodeTemplate genCodeTemplate) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genCodeTemplateService.saveOrUpdate(genCodeTemplate);
        return message;
    }

    @RequestMapping(value = "deleteGenCodeTemplate", method = RequestMethod.DELETE)
    public Message delete(GenCodeTemplate genCodeTemplate) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genCodeTemplateService.delete(genCodeTemplate);
        return message;
    }

    @RequestMapping(value = "uploadCode", method = RequestMethod.PUT)
    public Message uploadCode(@RequestParam("fileupload") MultipartFile file, GenCodeTemplate genCodeTemplate) throws IOException {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genCodeTemplateService.addCodeTpl(file, genCodeTemplate);
        return message;
    }

    @RequestMapping(value = "downloadCode", method = RequestMethod.GET)
    public void downloadCode(GenCodeTemplate genCodeTemplate, HttpServletRequest request, HttpServletResponse response) {
        GenCodeTemplate genCodeDown = genCodeTemplateService.get(genCodeTemplate);
        String fileName = Global.getCodeTplPath() + genCodeDown.getRealName();
        FileUtils.downFile(new File(fileName), request, response, genCodeDown.getName());
    }

}