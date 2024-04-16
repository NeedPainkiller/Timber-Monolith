package xyz.needpainkiller.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import xyz.needpainkiller.lib.azure.KeyVaultManager;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Profile("vault")
@Configuration
@EnableEncryptableProperties
public class EncryptVaultConfig {


    @Bean("stringEncryptor")
    public StringEncryptor propertiesEncryptor() {
        String password = KeyVaultManager.getSecretValue("enc-cipher-string");
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
//        config.setPassword(System.getenv("JASYPT_PASSWORD")); // 환경 변수
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean("fileCipherKeyString")
    public SecretKey fileCipherSecretKey(KeyVaultManager keyVaultManager) {
        String secretKey = keyVaultManager.getSecretValue("enc-cipher-file");
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

}
