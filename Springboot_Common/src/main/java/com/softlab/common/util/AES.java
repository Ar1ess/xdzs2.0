package com.softlab.common.util;

import com.softlab.common.exception.AppException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;


/**
 * 微信 数据解密<br/>
 * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
 * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
 * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
 * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
 */
/**
 * @author LiXiwen
 * @date 2019/11/8 14:14
 */
public class AES {

    public static boolean initialized = false;

    /**
     * 算法名
     */
    public static final String KEY_NAME = "AES";

    /**
     * 加解密算法/模式/填充方式
     * ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个iv
     */
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            // todo : 初始化
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));
            byte[] result = cipher.doFinal(content);

            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (BadPaddingException e) {
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            throw new AppException(1, "解密失败" + e.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new AppException(1, "解密失败" + e.getMessage());
        }
    }

    public static void initialize() {
        if (initialized) { return; }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    /**
     * 生成iv
     * @param iv
     * @return
     * @throws Exception
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }


}
