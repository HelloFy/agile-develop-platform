/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.entity;


import cn.edu.xidian.platform.commons.entity.Page;


/**
 * 阿斯达斯的Entity
 * @author 费玥
 * @version 2017-04-17
 */
public class GenUmlClassDiagram extends Page<GenUmlClassDiagram> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String classDiagramName;        // 类图名称
    private String comments;        // 说明
    private String path;        // 服务器中存储路径

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassDiagramName() {
        return classDiagramName;
    }

    public void setClassDiagramName(String classDiagramName) {
        this.classDiagramName = classDiagramName;
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
}