package com.shui.blacktea.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * Description:获取SHA1
 * Created by Juice_ on 2017/5/31.
 */

public class SHA1 {
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName()
                    , PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] signatures = md.digest(cert);
            StringBuffer sha1 = new StringBuffer();
            int i = 0;
            for (byte key : signatures) {
                String appendString = Integer.toHexString(0xFF & key).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    sha1.append("0");
                sha1.append(appendString);
                if (signatures.length - 1 == i)
                    break;
                sha1.append(":");
                i++;
            }
            System.out.println("---SHA1:" + sha1.toString());
            return sha1.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
