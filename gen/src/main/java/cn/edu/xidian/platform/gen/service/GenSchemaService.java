package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.IGenSchemaDao;
import cn.edu.xidian.platform.gen.entity.GenScheme;

/**
 * Created by 费玥 on 2017-3-20.
 */
@Service
public class GenSchemaService {

    @Autowired
    private IGenSchemaDao iGenSchemaDao;

    @Transactional
    public void saveOrUpdate(GenScheme genScheme) {
        if (genScheme.getId() == 0L) {
            iGenSchemaDao.save(genScheme);
        } else {
            iGenSchemaDao.update(genScheme);
        }
    }

    @Transactional
    public void delete(GenScheme genScheme) {
        iGenSchemaDao.delete(genScheme);
    }

    public List<GenScheme> findList(GenScheme genScheme) {
        return iGenSchemaDao.findList(genScheme);
    }
}
