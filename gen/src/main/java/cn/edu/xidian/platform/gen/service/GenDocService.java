/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}