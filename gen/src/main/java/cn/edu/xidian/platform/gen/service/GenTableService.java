package cn.edu.xidian.platform.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.edu.xidian.platform.gen.dao.IGenTable;
import cn.edu.xidian.platform.gen.entity.GenTable;

/**
 * Created by 费玥 on 2017-3-17.
 */
@Service
public class GenTableService {

    @Autowired
    private IGenTable iGenTable;

    public List<GenTable> findAllTableList(){
        return iGenTable.findAllTableList();
    }
}
