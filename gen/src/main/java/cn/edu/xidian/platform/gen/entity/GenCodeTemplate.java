/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.entity;


import cn.edu.xidian.platform.commons.entity.Page;


/**
 * 代码模版Entity
 * @author 李婧
 * @version 2017-04-18
 */
public class GenCodeTemplate extends Page<GenCodeTemplate> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;        // 名称
    private String function;        // 功能
    private String comments;        // 说明
    private String path;        // path
    private String realName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}