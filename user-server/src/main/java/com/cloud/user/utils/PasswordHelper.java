package com.cloud.user.utils;

import com.cloud.user.domain.dto.UserRegisterDTO;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author zhoushuai
 */
@Component
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "MD5";
    private int hashIterations = 2;


    public  void encryptPassword(UserRegisterDTO userRegister) {

        userRegister.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(algorithmName, userRegister.getPassword(),
                ByteSource.Util.bytes(userRegister
                .getCredentialsSalt()), hashIterations).toHex();

        userRegister.setPassword(newPassword);
    }
}
