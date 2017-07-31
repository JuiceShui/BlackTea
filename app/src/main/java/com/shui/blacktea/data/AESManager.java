/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data;

import com.alibaba.fastjson.JSON;
import com.shui.blacktea.App;
import com.yeeyuntech.framework.data.retrofit.CipherManager;
import com.yeeyuntech.framework.data.retrofit.IAESManager;
import com.yeeyuntech.framework.utils.crypto.AES;
import com.yeeyuntech.framework.utils.crypto.Digest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by adu on 17-3-25.
 * <p>
 * Description: AES 加解密
 * Modify by lsy on 2017/7/7 下午4:46
 */

public class AESManager implements IAESManager {

    private String key;
    private String keyClientPart;
    private String keyServerPart;

    private static class InstanceHolder {
        private static final AESManager instance = new AESManager();
    }

    public static AESManager getInstance() {
        return InstanceHolder.instance;
    }

    private AESManager() {
        CipherManager.getInstance().setAESManager(this);
    }

    private void initKey() {
        key = keyClientPart + keyServerPart;
    }

    public void setKeyClientPart(String part) {
        this.keyClientPart = part;
        initKey();
    }

    public void setKeyServerPart(String part) {
        this.keyServerPart = part;
        initKey();
    }

    public void setKey(String key) {
        this.keyClientPart = "";
        this.keyServerPart = "";
        this.key = key;
    }


    @Override
    public String getKey() {
        if (keyClientPart == null || keyServerPart == null) {
            return null;
        } else {
            return key;
        }
    }

    public Map<String, String> encryptParams() {
        return encryptParams(null);
    }

    @Override
    public Map<String, String> encryptParams(Map<String, Object> params) {
        if (params == null) params = new HashMap<>();
        // TODO 处理业务参数
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        String encryptData = encrypt(JSON.toJSONString(params));
        String randomStr = UUID.randomUUID().toString();
        String sign = Digest.md5(encryptData + randomStr + timestamp);
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("timestamp", timestamp);
        reqParams.put("data", encryptData);
        reqParams.put("str", randomStr);
        reqParams.put("sign", sign);
        reqParams.put("token", App.getInstance().getToken());
        return reqParams;
    }

    public String encrypt(String s) {
        return AES.encrypt(s, key);
    }

    public String decrypt(String s) {
        return AES.decrypt(s, key);
    }
}
