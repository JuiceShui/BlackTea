package com.yeeyuntech.framework.data.retrofit;

import com.yeeyuntech.framework.utils.crypto.AES;
import com.yeeyuntech.framework.utils.crypto.RSA;

/**
 * Created by adu on 2017/5/26.
 */

public class CipherManager {

    private static class InstanceHolder {
        private static final CipherManager instance = new CipherManager();
    }

    public static CipherManager getInstance() {
        return CipherManager.InstanceHolder.instance;
    }

    private CipherManager() {
    }

    private IRSAManager mRSAManager;
    private IAESManager mAESManager;

    public void setRSAManager(IRSAManager RSAManager) {
        mRSAManager = RSAManager;
    }

    public void setAESManager(IAESManager AESManager) {
        mAESManager = AESManager;
    }

    public String decryptAES(String s) {
        return AES.decrypt(s, mAESManager.getKey());
    }

    public String encryptAES(String s) {
        return AES.encrypt(s, mAESManager.getKey());
    }

    public String decryptRSA(String s) {
        return RSA.decrypt(s, mRSAManager.getPriKey());
    }

    public String encryptRSA(String s) {
        return RSA.encrypt(s, mRSAManager.getPubKey());
    }
}
