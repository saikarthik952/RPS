package com.multiplayer.RPS.util;

import java.util.Random;

public  class RandomIdGenerator
{
    private static char[] _base62chars =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                    .toCharArray();

    private static Random _random = new Random();

    public static String GetBase62(int length)
    {
        var sb = new StringBuilder(length);

        for (int i=0; i<length; i++)
            sb.append(_base62chars[_random.nextInt(62)]);

        return sb.toString();
    }

}
