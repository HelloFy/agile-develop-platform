package cn.edu.xidian.platform.commons.entity;

import java.io.Serializable;

/**
 * Created by 费玥 on 2017/3/18.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 8970813727914845893L;

    private MessageResult result;

    private Object message;

    private String errorMsg;

    public Message() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(MessageResult result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public enum MessageResult{
        SUCCESS(true),FAIL(false);
        private boolean res ;
        MessageResult(boolean res){
            this.res = res;
        }
        @Override
        public String toString() {
            return Boolean.valueOf(res).toString();
        }
    }
}