package cn.edu.xidian.platform.gen.entity.uml;

/**
 * Created by 李婧 on 2017-4-21.
 */
public class UMLFile {

    private String fileJsonStr;

    private String fileName;

    private ParseType parseType;

    public ParseType getParseType() {
        return parseType;
    }

    public void setParseType(ParseType parseType) {
        this.parseType = parseType;
    }

    public enum ParseType {
        PROJECT, MODEL
    }

    public String getFileJsonStr() {
        return fileJsonStr;
    }

    public void setFileJsonStr(String fileJsonStr) {
        this.fileJsonStr = fileJsonStr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
