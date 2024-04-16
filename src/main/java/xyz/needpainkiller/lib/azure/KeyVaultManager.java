package xyz.needpainkiller.lib.azure;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeyVaultManager {
    private static final String keyVaultUri = "https://timber-key.vault.azure.net";

    public static String getSecretValue(String secretName) {
        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        KeyVaultSecret retrievedSecret = secretClient.getSecret(secretName);
        return retrievedSecret.getValue();
    }
}


