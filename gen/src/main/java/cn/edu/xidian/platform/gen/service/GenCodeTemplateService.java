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
import cn.edu.xidian.platform.commons.utils.StringUtils;
import cn.edu.xidian.platform.gen.dao.IGenCodeTemplateDao;
import cn.edu.xidian.platform.gen.entity.GenCodeTemplate;

/**
 * 代码模版Service
 * @author 费玥
 * @version 2017-04-18
 */
@Service
public class GenCodeTemplateService {

    @Autowired
    private IGenCodeTemplateDao iGenCodeTemplateDao;

    public GenCodeTemplate get(GenCodeTemplate genCodeTemplate) {
        return iGenCodeTemplateDao.get(genCodeTemplate);
    }

    public List<GenCodeTemplate> findList(GenCodeTemplate genCodeTemplate) {
        return iGenCodeTemplateDao.findList(genCodeTemplate);
    }

    public List<GenCodeTemplate> findAllList() {
        return iGenCodeTemplateDao.findAllList();
    }

    @Transactional
    public void saveOrUpdate(GenCodeTemplate genCodeTemplate) {
        if (genCodeTemplate.getId() == 0L) {
            iGenCodeTemplateDao.save(genCodeTemplate);
        } else {
            iGenCodeTemplateDao.update(genCodeTemplate);
        }
    }

    @Transactional
    public void delete(GenCodeTemplate genCodeTemplate) {
        iGenCodeTemplateDao.delete(genCodeTemplate);
    }

    public void addCodeTpl(MultipartFile file, GenCodeTemplate genCodeTemplate) throws IOException {
        String filePath = StringUtils.replace(Global.getProjectPath(), "web", "gen") +
                File.separator + "CodeTemplate" + File.separator;

        if (!file.isEmpty()) {
            String fileSuffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filePath + genCodeTemplate.getName() + "." + fileSuffix)));
            stream.write(bytes);
            stream.close();
            genCodeTemplate.setPath("CodeTemplate" + File.separator + genCodeTemplate.getName());
        }
    }
}