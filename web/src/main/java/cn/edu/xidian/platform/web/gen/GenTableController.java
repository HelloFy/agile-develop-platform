package cn.edu.xidian.platform.web.gen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
import cn.edu.xidian.platform.gen.service.GenTableSchemaService;
import cn.edu.xidian.platform.gen.service.GenTableService;

/**
 * Created by 李婧 on 2017-3-17.
 */

@RestController
@RequestMapping("/gen")
public class GenTableController {

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private GenTableSchemaService genTableSchemaService;

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

    @RequestMapping(value = "tableForm",method = RequestMethod.GET)
    public ModelAndView genTableFormView(Model model){
        return new ModelAndView("gen/genTableForm");
    }

    @RequestMapping(value = "getPhysicalTables",method = RequestMethod.GET)
    public Message getPhysicalTables(){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableService.findAllTableList());
        return message;
    }

    @RequestMapping(value = "getBusinessTables",method = RequestMethod.GET)
    public Message getBusinessTables(GenTable genTable){
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        if (genTable.getPage() != 0 ) { //分页
            PageHelper.startPage(genTable.getPage(), genTable.getPageSize());
            List<GenTable> genTablePage = genTableService.findPagedBusinessTableList(genTable);
            PageInfo<GenTable> pageInfo = new PageInfo<>(genTablePage);
            message.setMessage(pageInfo);
            logger.info(pageInfo);
            return message ;
        }
        List<GenTable> allBusinessTbList = genTableService.findAllBusinessTableList();
        message.setMessage(allBusinessTbList);
        return message;
    }

    @RequestMapping(value = "getTableInfo",method = RequestMethod.GET)
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

    @RequestMapping(value = "modifyGenTable", method = RequestMethod.GET)
    public Message modifyGenTable(GenTable genTable) {
        Message message = new Message();
        GenTable genTableInfo = genTableService.findUnique(genTable);
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableInfo);
        return message;
    }

    @RequestMapping(value = "genCodeByTable", method = RequestMethod.PUT)
    public Message genCodeByTable(GenScheme genScheme) {
        Message message = new Message();
        message.setResult(Message.MessageResult.SUCCESS);
        message.setMessage(genTableSchemaService.saveOrUpdateAndGen(genScheme));
        return message;
    }

    @RequestMapping(value = "saveGenTable",method = RequestMethod.PUT)
    public Message saveGenTable(GenTable genTable){
        Message message = new Message();
        int save = genTableService.saveOrUpdate(genTable);
        switch (save) {
            case 1994: {
                //Save
                message.setMessage("SAVE");
                break;
            }
            case 1995: {
                //Update
                message.setMessage("UPDATE");
                break;
            }
        }
        message.setResult(Message.MessageResult.SUCCESS);
        return message;
    }

    @RequestMapping(value = "deleteGenTable", method = RequestMethod.DELETE)
    public Message deleteGenTable(GenTable genTable) {
        Message message = new Message();
        genTableService.delete(genTable);
        message.setResult(Message.MessageResult.SUCCESS);
        return message;
    }

}
