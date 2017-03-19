package cn.edu.xidian.platform.web.gen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import cn.edu.xidian.platform.commons.entity.Message;
import cn.edu.xidian.platform.commons.utils.Exceptions;
import cn.edu.xidian.platform.commons.utils.StringUtils;
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

    private static Logger logger = Logger.getLogger(GenTableController.class);

    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception exception){
        logger.error(Exceptions.getStackTraceAsString(exception));
        Message message = new Message();
        message.setResult(Message.MessageResult.FAIL);
        message.setErrorMsg("服务器繁忙");
        message.setMessage("服务器繁忙");
        return message;
    }

    @RequestMapping("tableForm")
    public ModelAndView genTableFormView(Model model){
        return new ModelAndView("gen/genTableForm");
    }

    @RequestMapping("getPhysicalTables")
    public Message getPhysicalTables(){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableService.findAllTableList());
        return message;
    }

    @RequestMapping("getBusinessTables")
    public Message getBusinessTables(GenTable genTable){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        if (genTable.getIndex() == 0 ) {
            PageHelper.startPage(genTable.getIndex(), genTable.getPageSize());
        }
        List<GenTable> genTablePage = genTableService.findAllBusinessTableList(genTable);
        PageInfo<GenTable> pageInfo = new PageInfo<>(genTablePage);
        message.setMessage(pageInfo);
        logger.info(pageInfo);
        return message;
    }

    @RequestMapping("getTableInfo")
    public Message getTableInfo(GenTable genTable){
        Message message =  new Message();
        if (!genTableService.checkTableName(genTable.getTableName())){
            message.setResult(Message.MessageResult.FAIL);
            message.setMessage("业务表已经存在,下一步失败");
            return message;
        }
        GenTable genTableInfo = genTableService.getTableFromDB(genTable);
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableInfo);
        return message;
    }

    @RequestMapping("saveGenTable")
    public Message saveGenTable(GenTable genTable){
        Message message = new Message();
        genTableService.saveOrUpdate(genTable);
        message.setResult(Message.MessageResult.SUCCESS);
        return message;
    }
}
