package com.yeeyuntech.framework.data.retrofit.converter;


import com.yeeyuntech.framework.data.retrofit.CipherManager;

/**
 * Created by adu on 2017/4/27.
 * 服务器返回的数据格式
 */

class Response {

    private int code;
    private String msg;
    private String data;

    Response() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // AES decrypt data
    public String getAESDecryptData() {
        if (data == null) {
            return null;
        }
        return CipherManager.getInstance().decryptAES(data);
    }

    // RSA decrypt data
    public String getRSADecryptData() {
        if (data == null) {
            return null;
        }
        return CipherManager.getInstance().decryptRSA(data);
    }
}
