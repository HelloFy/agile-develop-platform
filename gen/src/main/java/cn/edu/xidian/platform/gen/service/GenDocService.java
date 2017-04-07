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
import java.util.Date;
import java.util.List;

import cn.edu.xidian.platform.commons.config.Global;
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenDocDao;
import cn.edu.xidian.platform.gen.entity.GenDoc;

/**
 * 文档模版Service
 * @author 费玥
 * @version 2017-04-06
 */
@Service
public class GenDocService {

    @Autowired
    private IGenDocDao iGenDocDao;

    public GenDoc get(GenDoc genDoc) {
        return iGenDocDao.get(genDoc);
    }

    public List<GenDoc> findList(GenDoc genDoc) {
        return iGenDocDao.findList(genDoc);
    }

    public List<GenDoc> findAllList() {
        return iGenDocDao.findAllList();
    }

    @Transactional
    public void saveOrUpdate(GenDoc genDoc) {
        if (genDoc.getId() == 0L) {
            iGenDocDao.save(genDoc);
        } else {
            iGenDocDao.update(genDoc);
        }
    }

    @Transactional
    public void delete(GenDoc genDoc) {
        iGenDocDao.delete(genDoc);
    }

    @Transactional
    public void addTpl(MultipartFile file, GenDoc genDoc) throws IOException {
        String filePath = StringUtils.replace(Global.getProjectPath(), "web", "gen") +
                File.separator + "DocTemplate" + File.separator;
        if (!file.isEmpty()) {
            String fileSuffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filePath + genDoc.getDocName() + "." + fileSuffix)));
            stream.write(bytes);
            stream.close();
            genDoc.setDocName(genDoc.getDocName() + "." + fileSuffix);
            genDoc.setUploadDate(new Date());
            genDoc.setDocSize(file.getSize() / 1024L);
            genDoc.setDocPath("DocTemplate" + File.separator + genDoc.getDocName());
            iGenDocDao.save(genDoc);
        }
    }

}