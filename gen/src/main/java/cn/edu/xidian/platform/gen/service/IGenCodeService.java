package cn.edu.xidian.platform.gen.service;

import cn.edu.xidian.platform.gen.entity.GenScheme;

/**
 * @author 费玥
 * @since 2017/4/22 18:15
 */
public interface IGenCodeService {

    GenScheme initDefaultGenScheme(String name);

    String generateCode(GenScheme genScheme);
}
