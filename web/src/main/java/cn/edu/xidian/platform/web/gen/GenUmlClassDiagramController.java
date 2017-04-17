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

import java.io.IOException;
import java.util.List;

import cn.edu.xidian.platform.commons.entity.Message;
import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;
import cn.edu.xidian.platform.gen.service.GenUmlClassDiagramService;

/**
 * 阿斯达斯的Controller
 * @author 费玥
 * @version 2017-04-17
 */
@RestController
@RequestMapping(value = "gen")
public class GenUmlClassDiagramController {

    @Autowired
    private GenUmlClassDiagramService genUmlClassDiagramService;


    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception ex) {
        Message message = new Message();
        message.setResult(Message.MessageResult.FAIL);
        message.setMessage("服务器繁忙");
        message.setErrorMsg("服务器繁忙");
        return message;
    }

    @RequestMapping(value = "getGenUmlClassDiagramList", method = RequestMethod.GET)
    public Message getGenUmlClassDiagramList(GenUmlClassDiagram genUmlClassDiagram) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        PageHelper.startPage(genUmlClassDiagram.getPage(), genUmlClassDiagram.getPageSize());
        List<GenUmlClassDiagram> genUmlClassDiagramPageList = genUmlClassDiagramService.findList(genUmlClassDiagram);
        PageInfo<GenUmlClassDiagram> pageInfo = new PageInfo<>(genUmlClassDiagramPageList);
        message.setMessage(pageInfo);
        return message;
    }

    @RequestMapping(value = "genUmlClassDiagramForm", method = RequestMethod.GET)
    public ModelAndView form() {
        return new ModelAndView("gen/genGenUmlClassDiagramForm");
    }

    @RequestMapping(value = "saveGenUmlClassDiagram", method = RequestMethod.PUT)
    public Message save(GenUmlClassDiagram genUmlClassDiagram) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genUmlClassDiagramService.saveOrUpdate(genUmlClassDiagram);
        return message;
    }

    @RequestMapping(value = "deleteGenUmlClassDiagram", method = RequestMethod.DELETE)
    public Message delete(GenUmlClassDiagram genUmlClassDiagram) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genUmlClassDiagramService.delete(genUmlClassDiagram);
        return message;
    }

    @RequestMapping(value = "uploadClassDiagram", method = RequestMethod.PUT)
    public Message uploadClassDiagram(@RequestParam("fileupload") MultipartFile file, GenUmlClassDiagram genUmlClassDiagram) throws IOException {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        genUmlClassDiagramService.addClassDiagram(file, genUmlClassDiagram);
        return message;
    }

}