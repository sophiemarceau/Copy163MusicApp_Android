package com.sophiemarceauqu.lib_network.okhttp.exception;

/*
 * 自定义异常类,返回ecode,emsg到业务层
 */
public class OkHttpException extends Exception {
    private static final long serialVersionUID = 1L;

    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
