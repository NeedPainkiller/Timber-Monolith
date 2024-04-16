package xyz.needpainkiller.lib.security.secret;

import javax.crypto.SecretKey;

public interface JsonWebTokenSecretKeyManager {
    SecretKey getSecretKey();
}
