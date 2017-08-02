package com.shui.blacktea.utils;

import java.util.Random;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class RandomNumberUtil {
    public static int getRandom(int min, int max) {
        int range = max - min;
        Random rand = new Random();
        int num = rand.nextInt(range) + min;
        return num;

    }
}
