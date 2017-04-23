/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.utils.FileUtils;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenSchemaDao;
import cn.edu.xidian.platform.gen.dao.IGenUmlClassDiagramDao;
import cn.edu.xidian.platform.gen.entity.GenScheme;
import cn.edu.xidian.platform.gen.entity.GenUmlClassDiagram;

/**
 * 阿斯达斯的Service
 * @author 费玥
 * @version 2017-04-17
 */
@Service
public class GenUmlClassDiagramService {

    @Autowired
    private IGenUmlClassDiagramDao iGenUmlClassDiagramDao;

    @Autowired
    private GenUmlSchemaService genUmlSchemaService;

    @Autowired
    private IGenSchemaDao iGenSchemaDao;

    public GenUmlClassDiagram get(GenUmlClassDiagram genUmlClassDiagram) {
        return iGenUmlClassDiagramDao.get(genUmlClassDiagram);
    }

    public List<GenUmlClassDiagram> findList(GenUmlClassDiagram genUmlClassDiagram) {
        return iGenUmlClassDiagramDao.findList(genUmlClassDiagram);
    }

    public List<GenUmlClassDiagram> findAllList() {
        return iGenUmlClassDiagramDao.findAllList();
    }

    @Transactional
    public void saveOrUpdate(GenUmlClassDiagram genUmlClassDiagram) {
        if (genUmlClassDiagram.getId() == 0L) {
            iGenUmlClassDiagramDao.save(genUmlClassDiagram);
        } else {
            iGenUmlClassDiagramDao.update(genUmlClassDiagram);
        }
        GenScheme genScheme = genUmlSchemaService.initDefaultGenScheme(genUmlClassDiagram.getClassDiagramName());
        genScheme.setRefId(genUmlClassDiagram.getId());
        iGenSchemaDao.save(genScheme);
    }

    @Transactional
    public void delete(GenUmlClassDiagram genUmlClassDiagram) {
        iGenUmlClassDiagramDao.delete(genUmlClassDiagram);
    }

    public void addClassDiagram(MultipartFile file, GenUmlClassDiagram genUmlClassDiagram) throws IOException {
        String filePath = StringUtils.replace(Global.getProjectPath(), "web", "gen") +
                File.separator + "UMLClassDiagram" + File.separator;
        if (!file.isEmpty()) {
            String fileSuffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filePath + genUmlClassDiagram.getClassDiagramName() + "." + fileSuffix)));
            stream.write(bytes);
            stream.close();
            String origialJsonStr = FileUtils.readFileToString(new File(filePath + genUmlClassDiagram.getClassDiagramName() + "." + fileSuffix));
            String formatJsonStr = StringUtils.replace(origialJsonStr, "$ref", "pId");
            genUmlClassDiagram.setClassDiagramName(genUmlClassDiagram.getClassDiagramName() + "." + fileSuffix);
            genUmlClassDiagram.setComments(genUmlClassDiagram.getComments());
            genUmlClassDiagram.setPath("UMLClassDiagram" + File.separator + genUmlClassDiagram.getClassDiagramName());
            genUmlClassDiagram.setFormatJsonStr(formatJsonStr);
            saveOrUpdate(genUmlClassDiagram);
        }
    }
}