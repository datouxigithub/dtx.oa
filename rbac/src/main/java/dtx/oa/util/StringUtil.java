/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author datouxi
 */
public class StringUtil {
   public static String getMD5String(String str){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder encoder=new BASE64Encoder();
            return encoder.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return str;
        }
    } 
}
