package com.multiplayer.RPS.util;

import java.util.Random;

public  class RandomIdGenerator
{
    final public static String letters =  "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static char[] _base62chars = letters.toCharArray();

    private static Random _random = new Random();

    public static String GetBase62(int length)
    {
        var sb = new StringBuilder(length);

        for (int i=0; i<length; i++)
            sb.append(_base62chars[_random.nextInt(62)]);

        return sb.toString();
    }

}
