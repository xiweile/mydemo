package com.weiller.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import java.text.MessageFormat;

@Slf4j
public class SignUtils {

    /**
     * 签名验证
     * @return signedText
     */
    public static boolean checkSign(String signKey,String appId,String sessionId,String requestId,String timestamp,String sign,String data){
        if (data == null) {
            data = "";
        }
        String signedText = null;
        if ( signKey !=null && sign!=null) {
            String messageText = MessageFormat.format("{0}{1}{2},{3},{4},{5},{6}{7}", data, "{",  appId,signKey,  sessionId, requestId,  timestamp , "}");
            MessageDigestPasswordEncoder mde = new MessageDigestPasswordEncoder("SHA-256", true);
            signedText = (new Md5PasswordEncoder()).encodePassword(mde.encodePassword(messageText, timestamp), (Object)null).toUpperCase();
        }
        if(signedText!=null) {
            return signedText.equals(sign);
        }else{
            return false;
        }
    }
}
