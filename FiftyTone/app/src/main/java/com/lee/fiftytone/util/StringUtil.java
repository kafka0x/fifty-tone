package com.lee.fiftytone.util;

import java.util.Random;

/**
 * Created by LeeChen on 2018/11/2 0002.
 */

public class StringUtil {
    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
