/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.shui.blacktea.App;
import com.shui.blacktea.config.PrefKey;
import com.yeeyuntech.framework.utils.LogUtils;
import com.yeeyuntech.framework.utils.PrefUtils;
import com.yeeyuntech.framework.utils.TxUtils;
import com.yeeyuntech.framework.utils.crypto.Digest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by adu on 17-3-25.
 * <p>
 * Description:需要自己的业务实现加解密
 * Modify by lsy on 2017/7/7 下午2:49
 */

public class AuthManager implements APIPath {

    private final String TAG = getClass().getSimpleName();

    private App mApp = App.getInstance();

    private static class InstanceHolder {
        private static final AuthManager instance = new AuthManager();
    }

    private AuthManager() {
    }

    public static AuthManager getInstance() {
        return InstanceHolder.instance;
    }

    public Map<String, String> getLoginParams(String phone, String smsCode) {
        Map<String, String> params = new HashMap<>();
        params.put("phoneNum", phone);
        params.put("smsCode", smsCode);
        String randomKey = UUID.randomUUID().toString();
        params.put("key", randomKey);
        AESManager.getInstance().setKeyClientPart(randomKey);
        // 处理业务参数
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        String encryptData = RSAManager.getInstance().encrypt(JSON.toJSONString(params));
        String randomStr = UUID.randomUUID().toString();
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("timestamp", timestamp);
        reqParams.put("data", encryptData);
        reqParams.put("str", randomStr);
        String sign = Digest.md5(TxUtils.sortValues(reqParams) + Digest.md5(timestamp));
        reqParams.put("sign", sign);
        return reqParams;
    }

    public Map<String, String> getRefreshTokenParams() {
        final Map<String, String> params = new HashMap<>();
        String randomKey = UUID.randomUUID().toString();
        String oldKey = PrefUtils.getString(PrefKey.AES_KEY, "");
        params.put("newKey", randomKey);
        params.put("oldKey", oldKey);
        AESManager.getInstance().setKeyClientPart(randomKey);
        // 处理业务参数
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        String encryptData = RSAManager.getInstance().encrypt(JSON.toJSONString(params));
        String randomStr = UUID.randomUUID().toString();
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("timestamp", timestamp);
        reqParams.put("data", encryptData);
        reqParams.put("str", randomStr);
        String sign = Digest.md5(TxUtils.sortValues(reqParams) + Digest.md5(timestamp));
        reqParams.put("token", App.getInstance().getToken());
        reqParams.put("sign", sign);
        return reqParams;
    }

   /* public void saveAuthInfo(UserEntity userEntity) {
        // 设置本地存储 保存请求下来的key
        userEntity.setToken(mApp.getToken());
        userEntity.setUserId(mApp.getUserId());
        mApp.getUser().setUserInfo(userEntity);
        // 将user的信息 保存在本地
        PrefUtils.changeUser(mApp.getUserId());
        PrefUtils.put(PrefKey.USER_INFO, mApp.getUser().toString());
        String key = AESManager.getInstance().getKey();
        LogUtils.i(TAG, "the saved AES key is " + key);
        PrefUtils.put(PrefKey.AES_KEY, AESManager.getInstance().getKey());
    }*/


    public boolean checkLogin(final Activity context) {
        if (App.getInstance().isLogin()) {
            return true;
        } else {
//            LoginActivity.launch(context);
            return false;
        }
    }
}
