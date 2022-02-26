package com.xxl.job.admin.core.util;

import com.xxl.job.admin.core.model.XxlJobUser;
import lombok.Data;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author zhoushuai
 */
@Component
@Data
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "MD5";
    private int hashIterations = 2;

    public void encryptPassword(XxlJobUser xxlJobUser) {

        xxlJobUser.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(algorithmName, xxlJobUser.getPassword(),
                ByteSource.Util.bytes(xxlJobUser.getCredentialsSalt()),
                hashIterations).toHex();

        xxlJobUser.setPassword(newPassword);
    }

}

