/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 */
package cn.edu.xidian.platform.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

import cn.edu.xidian.platform.commons.entity.Page;


/**
 * 文档模版Entity
 * @author 费玥
 * @version 2017-04-06
 */
public class GenDoc extends Page<GenDoc> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String docName;        // doc_name
    private Long docSize;        // doc_size
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;        // upload_date
    private String docPath;        // doc_path
    private String realName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Long getDocSize() {
        return docSize;
    }

    public void setDocSize(Long docSize) {
        this.docSize = docSize;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}