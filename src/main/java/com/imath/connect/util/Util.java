package com.imath.connect.util;

import java.security.SecureRandom;

public class Util {

    static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();
    static final int LEN_KEY = 30;
    
    public static String randomString() {
        return randomString(LEN_KEY);
    }
    
    public static String randomString( int len ) {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
            sb.append( ABC.charAt( rnd.nextInt(ABC.length()) ) );
        return sb.toString();
    }
}
