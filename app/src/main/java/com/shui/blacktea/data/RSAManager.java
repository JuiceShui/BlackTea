/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data;

import com.alibaba.fastjson.JSON;
import com.yeeyuntech.framework.data.retrofit.CipherManager;
import com.yeeyuntech.framework.data.retrofit.IRSAManager;
import com.yeeyuntech.framework.utils.crypto.Digest;
import com.yeeyuntech.framework.utils.crypto.RSA;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by adu on 2017/4/12.
 * <p>
 * Description:   转化命令: openssl pkcs8 -topk8 -inform PEM -in ./client_private.pem -outform pem -nocrypt -out ~/Desktop/pkcs8.pem
 * Modify by lsy on 2017/7/24 下午9:31
 */

public class RSAManager implements IRSAManager {

    private static final String KEY_PUB = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA6hg3Ap8OJWx4f2uW0C0S\n" +
            "pgfzRe/+J5k+3vd9C0hWw70CpOKveM+q/FNuZH1g520qI5zfI0vOe3hd2hgUPs+L\n" +
            "nVKZlpjYg/XWBpqfa3eYqQSMM4yYXsvEXGxcZ3w7eOgxrbo9IxnfvaQnGD338Twn\n" +
            "vHwbtMSpqbpryqSqywQQ8iCkkM3U2Wc9t9et++T3zVk86AniRI3gfJLVeTQ9buTH\n" +
            "0Nm/c7RUvZIgULbVoXh2oscPhN0qNKmE1nuYYUQcub0h4cOmzv9WgnMhTsbDnbMT\n" +
            "Z6kVuacN4DVgjza8+2eGbdqCEjyxGYmI9jvjgIg5g+AkvHjfp3ywvKfFRvoUwcR6\n" +
            "Zx+iS+gNht6pi+sGbIUuSXTadVHcGnaAsrvnR38dAYHN9emvvneXtINHssjiGVfP\n" +
            "/G06u8yIB7zMNhyult+HutFSCcB6ShZsLQQl9xOxsmy1p/nyybPp527Ceeq5pz0s\n" +
            "Cl3egeyeIx/2mLSWpriDSVdzTnZy0hsqaZGXf2S1cKbd705Z2ld729+kXuKsnLnk\n" +
            "jolmTCMBfAcFX3UdrgbhYbAjxA+tqGDuTAD7TZXgnK2IWgjkNMFn3r879SdcxNJJ\n" +
            "H+ySTW8xAx2ok20+R6ZCoCmF+2JsBYyybJYB1ONdsC1IAOvk6xIg44EeXYNqldbQ\n" +
            "yuVMXwPm2L4pXGO9j75A9bECAwEAAQ==";

    private static final String KEY_PRI = "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswgg" +
            "knAgEAAoICAQC8bs7JULwimw7DP60vYQA3dqeqxv40MvVv4BUAaJ+lYUB2i8QkYx32AH" +
            "p1B946hsZwt8usntsL3ecMYvmJqirnnMIw/FAIJsHPP+u16w5MN26/cFHAW0SRDHHTwT" +
            "58VU/hnN/vYkZlX3UwhLaR1sfHG/wlIlseThUpLXsDeruck+NArx3Qj776PUHaztSZ59" +
            "yF0ztXkUocmGq5TWggYg8OuSR7doaSlsSyR0d2LbRLMjBLc2i73roRKri5JD6rj+dLXw" +
            "D+Pxs7r3lntMPOdfwpmps+tVN7suhacU3T42VrXeqW/b7fUY1fXSeJOnyfW68Ge2Rq4v" +
            "SJPnY7LPO86WZlcuLWkBtnMRRVfh/8+r9LfzJC954iiq8TNb0/QYwjE/4HOMujfqTAhD" +
            "TSlVOFrtdTDVFO9JAbu9EDEaB0mLRuHcHbinjr5B2n8eQhmc37qu//BvdDCaVcg9Ol0Y" +
            "ZUx79y+Al1Y1Q5/8RqF9YeJRkzZhr/yvUA46HWNw2S7WDjEs0Whj+TzLJ7+ugjoxkW66" +
            "wEbBxWb2PBLyEEq+AOEFm4/C6CZz3HPg6pQ3sYuEjEubmVgOsPGgbymOWNmrOCm9sGCh" +
            "nVydDDC9QLR9/N4eLYXI6DoXDkP3IEWk9s47fdPYFXPw6WKl87XpjIbVirNR9gKcfabX" +
            "fqczjojFMZSDq9tQIDAQABAoICADqkkZ59jc+HSjThoFhaa+LW/p9bhslgAppqtp6F8Ql" +
            "bFuDN5qEcZ7qjs3iwlizIvQFfcferCKjMypdERjzdxLSISLdODuo6hZ6mWgknui9mIQVb" +
            "Q1YGEXTC1rn8LiIrusyQMfEiOJ5b8vDCnL8OhOYmg3ZbFth7nIJQMTSIAdNdAyCiXzkFQ" +
            "35FijU6oVe0nSDY+GLsSJBVrwE0Ye+SFPWUERKLdJ4yZczF+iuBVdcBK56PsEynqGinTw" +
            "3vhcGG64i9bGWz5hnRJotr6nRaq7uW7jG55N1edImRMZN79ZhQhi2vkrOVgdXn++HtXdO" +
            "KiRIPXNUXBNuhoNIRz1k/min3oSFNxpUVJgr+ReuxjPxqS/cTGmEIXDXZSHOOiw3YUNgF" +
            "kEMB/kSGcs4dQnQ7+ByvmUQhADie5w9MjzfCguAVlzPGJsJWQhlZMAinPq77yunNx8ZJ7" +
            "if+8OYEQft/nubjzoJJPvYn2u87iMyjZR3J4cztT98P7VzDXH3I9tY3DGn5TXQSVCw13H" +
            "v1DTFzuUA2jgGgqc7Zt4WK8owK99TrS5T7KyrGljWgQDsCBavUG3NErRS/lyUVhnaycK+" +
            "LlowyuNyuX0y/fXExpI5phwZShrzFf0wZq6iBelm1XccT5pincIGc5EtWM0v9OvbUaDMU" +
            "fNUrxaDFilu4bwhAOHqRAoIBAQDkvavqkNxalnVS0svHL3SvxH+CXtpymwCvaCpb8fYcC7" +
            "x1YXVOuyHqdYFdfyoAKERmMAbtQ6xBgdzR/uM/ETQHbeToGysr4PA4IcIaGTkFV78A4Dk" +
            "QppWAtizyYkyHE4+4SYlPeggkRWqPjTx0XjEMYcOHEGXWfk9V5GXIin8/bhO59FUQ4eHP" +
            "VxEXKv7gwwrqURyehnv/aQPXLo7fZulbs/N6KZQRssJA57SRuE7av7UyqAQvw2opXC5Bxl" +
            "3dJizLIYKCcn8JVKAGbfv+nDZMKXEW/cOo84fCvnGxANblQfiIEQcZZphxShZtzcXME4Kf" +
            "GG0m7tRoKpmUOzuK3uFfAoIBAQDS429xRzsDiZJlbbNxNMqE0Wexed7HIX/0dIzZ+M8XZE" +
            "4yhFSd2PhCZb4qBAj2eVQkN/pU3MHTlE43neYTd4PiUtd9VSlWB2dntJfweheDlN3DGHGg" +
            "YE2mkwrbH9FoWqxXBxaBL2bW6eXld2uahHh/YhfG0eyjczHsCi9VrhW7VvcUGHg43+7xRN" +
            "ZHvGipbSrpfc5XeTdJCwC/6C8LJR5pMhuRjTjTFJt4L1cRnrIYhWgCv29eZDVi/aWGHpJK" +
            "dBXt0u3sMctUdN3812LBhP2ew/huSIhZ8uMMTzjBDiBhagm9pz3dowhPx0Mv52zF/VzBQp" +
            "wcLUCtrw1QnzZ2glVrAoIBAFY6DdGJtBdN+oIzRnAaupD7sNlzakOjnLmCud0ZNB5UakGRcI" +
            "gyUNGyZAbbxfhzNX2UAzhztz6mdpiOzhlbIdLfIVhBzZ9n1wZB64qtEWqrLixlWf2l3lu70" +
            "Ou6FIEi5Z2h0uor5V/O7yGYKz7utuyk+fEzREB8Mun9BEV5uAgwNa12po6Lp/TiLGYgZh5" +
            "SSA4Wb4Ruy9V3P+Rg/6jFgZh5GIJnpBKb4VBYSCBaaxdZBzRGU/WxY5ad0i4nWgDgwCf+x" +
            "NTp1mutA/oIgeLz/h/ty1zQYm6RBw9pfTAtUdTwboDUcmmn3DVwqU3RMQwCGs5ghd0UUOv" +
            "Hi0duoL5z0F8CggEACloRsVzOv8iz//iXk1oFCuoAjgyua9XwIv7JnzWAbOSGliI6yRjKbu" +
            "/xmrxh+D9Sawi4aAwCE7G2+THuBkyak7R/UW6pvchVQhjyUGKd3t6i2B3ODhJMHX4gTUtNiz" +
            "LQPOFjMGo0QR5wI4huJsKT66TUB/VsCUXxUGXblvgZpv2I2VquJBSR9RPs9M6B0LdEIPwE8" +
            "fRIS3SXz9PE42YqDP3hro5hGLNtQkQIoRvKIDwENAZToC2JFVfgFSpRAgiavvbYqPx0v6uQ" +
            "7gVgmqE9ldqGylsTHl821/bXkVpxKnb7DW01Mzj6gngEi0hv5XQfZnkvko6CG0R5gdie4shZ" +
            "CwKCAQARNp/wkx+ykd+lH/TkCgknVzixdowTBGTjG4VNqDdhb31cnA2uPyLUl4QDxd8OeWtH" +
            "iXrI/Wai/wOXwD/ch7nkInRaPKZKajs6sDadJihJaR26ReNK7BRCTBORKFsVkKXVlqmmgvcgn" +
            "NKVgh1aSRbXpp2PqJ73INvkhPdC3iTmxjSPJLBeW0KUuhkqkCYl3vgWohrSZV39lBah/5eWhX5" +
            "ykAom4HetQv7laapWBn7j7Zzbf8j/x++tlQiC2dPwZPuH0NmwJuV7DR2Lo7t3Nt6IsDaRhkj" +
            "6RE7pEsE7NYPJVQTuCDMas9sw2AyrQyXK8Fyf0F097ttxdFaSRU5NCSkm";


    private static class InstanceHolder {
        private static final RSAManager instance = new RSAManager();
    }

    public static RSAManager getInstance() {
        return InstanceHolder.instance;
    }

    private RSAManager() {
        CipherManager.getInstance().setRSAManager(this);
    }

    @Override
    public String getPubKey() {
        return KEY_PUB;
    }

    @Override
    public String getPriKey() {
        return KEY_PRI;
    }

    public Map<String, String> encryptParams() {
        return encryptParams(null);
    }

    public Map<String, String> encryptParams(Map<String, Object> params) {
        if (params == null) params = new HashMap<>();
        // 处理业务参数
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        String encryptData = encrypt(JSON.toJSONString(params));
        String randomStr = UUID.randomUUID().toString();
        String sign = Digest.md5(encryptData + randomStr + timestamp + Digest.md5(timestamp));
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("timestamp", timestamp);
        reqParams.put("data", encryptData);
        reqParams.put("str", randomStr);
        reqParams.put("sign", sign);
        return reqParams;
    }

    public String encrypt(String s) {
        return RSA.encrypt(s, KEY_PUB);
    }

    public String decrypt(String s) {
        return RSA.decrypt(s, KEY_PRI);
    }
}
