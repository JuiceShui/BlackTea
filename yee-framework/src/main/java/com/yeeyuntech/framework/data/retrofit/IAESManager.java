package com.yeeyuntech.framework.data.retrofit;

import java.util.Map;

/**
 * Created by adu on 2017/5/26.
 */

public interface IAESManager {

    String getKey();

    Map<String, String> encryptParams(Map<String, Object> params);
}
