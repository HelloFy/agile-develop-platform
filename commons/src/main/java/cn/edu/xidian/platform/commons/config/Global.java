/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.edu.xidian.platform.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.edu.xidian.platform.commons.utils.StringUtils;


/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */

@PropertySource("classpath:platform.properties")
@Configuration
public class Global {

    private static String frontPath;

    private static String urlSuffix;

    private static String demoMode;

    private static String userfilesBaseDir;

    private static String projectPath;

    private static String authorName;

    public static String getFrontPath() {
        return frontPath;
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return urlSuffix;
    }

    /**
     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
     */
    public static Boolean isDemoMode() {
        return "true".equals(demoMode) || "1".equals(demoMode);
    }

    /**
     * 页面获取常量
     * @see ${fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }

    /**
     * 获取上传文件的根目录
     * @return
     */
    public static String getUserfilesBaseDir(HttpServletRequest request) {
        if (StringUtils.isBlank(userfilesBaseDir)){
            try {
                userfilesBaseDir = request.getServletContext().getRealPath("/");
            } catch (Exception e) {
                return "";
            }
        }
        if(!userfilesBaseDir.endsWith("/")) {
            userfilesBaseDir += "/";
        }
//		System.out.println("userfiles.basedir: " + dir);
        return userfilesBaseDir;
    }

    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
        // 如果配置了工程路径，则直接返回，否则自动获取。
        if (StringUtils.isEmpty(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null){
                while(true){
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()){
                        break;
                    }
                    if (file.getParentFile() != null){
                        file = file.getParentFile();
                    }else{
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

    @Value("${userfiles.basedir}")
    public void setUserfilesBaseDir(String userfilesBaseDir) {
        Global.userfilesBaseDir = userfilesBaseDir;
    }

    @Value("${urlSuffix}")
    public void setUrlSuffix(String urlSuffix) {
        Global.urlSuffix = urlSuffix;
    }

    @Value("${frontPath}")
    public void setFrontPath(String frontPath) {
        Global.frontPath = frontPath;
    }

    @Value("${demoMode}")
    public void setDemoMode(String demoMode) {
        Global.demoMode = demoMode;
    }

    @Value("${projectPath}")
    public void setProjectPath(String projectPath) {
        Global.projectPath = projectPath;
    }

    public static String getAuthorName(){
        return authorName;
    }

    @Value("${authorName}")
    public void setAuthorName(String authorName) {
        Global.authorName = authorName;
    }
}

