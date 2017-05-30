package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.IGenSchemaDao;
import cn.edu.xidian.platform.gen.entity.GenScheme;

/**
 * @author 李婧
 * @since 2017/4/22 19:35
 */
public abstract class BaseGenSchemaSevice implements IGenCodeService {
    @Autowired
    private IGenSchemaDao iGenSchemaDao;

    public GenScheme get(GenScheme genScheme) {
        return iGenSchemaDao.get(genScheme);
    }

    public GenScheme getByRefId(long refId, int refType) {
        return iGenSchemaDao.getByRefIdAndRefType(refId, refType);
    }

    public List<GenScheme> findList(GenScheme genScheme) {
        return iGenSchemaDao.findList(genScheme);
    }

    @Transactional
    public void saveOrUpdate(GenScheme genScheme) {
        if (genScheme.getId() == 0L) {
            iGenSchemaDao.save(genScheme);
        } else {
            iGenSchemaDao.update(genScheme);
        }
    }

    @Transactional
    public String saveOrUpdateAndGen(GenScheme genScheme) {
        saveOrUpdate(genScheme);
        return generateCode(genScheme);
    }

    @Transactional
    public void delete(GenScheme genScheme) {
        iGenSchemaDao.delete(genScheme);
    }

    @Override
    public GenScheme initDefaultGenScheme(String name) {
        //need imp
        return null;
    }

    @Override
    public String generateCode(GenScheme genScheme) {
        //need imp
        return null;
    }
}
