package com.dao;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class PasswordEncode {
	
	private String rsa_instance="RSA";
	private String rsa_web_key="_RSA_WEB_Key_";
	PasswordEncoder bpe=new BCryptPasswordEncoder();
	
	public String getRsa_instance() {
		return rsa_instance;
	}
	public void setRsa_instance(String rsa_instance) {
		this.rsa_instance = rsa_instance;
	}
	public String getRsa_web_key() {
		return rsa_web_key;
	}
	public void setRsa_web_key(String rsa_web_key) {
		this.rsa_web_key = rsa_web_key;
	}
	
	//암호화 반환
	public String encode(String pw) {
		return bpe.encode(pw);
	}
	//암호화 비교
	public boolean match(String pw,String encpw) {
		return bpe.matches(pw, encpw);
	}
	
	//폼 전송 평문 데이터 암호화
	//복호화
    public String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(rsa_instance);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }
 
    
     // 16진 문자열을 byte 배열로 변환한다.
    
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }
 
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
 
    
     // rsa 공개키, 개인키 생성
    
    public HashMap<String, String> initRsa(HttpServletRequest request) {
        HttpSession session = request.getSession();
 
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(rsa_instance);
            generator.initialize(1024);
 
            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(rsa_instance);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
 
            session.setAttribute(rsa_web_key, privateKey); // session에 RSA 개인키를 세션에 저장
 
            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
 
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("RSAModulus", publicKeyModulus);
            map.put("RSAExponent", publicKeyExponent);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
