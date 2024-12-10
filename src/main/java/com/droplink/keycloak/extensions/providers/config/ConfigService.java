package com.droplink.keycloak.extensions.providers.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;

import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;

import lombok.Getter;

@Getter
public class ConfigService implements Provider {
    public static Map<String, Integer> sizeList = new HashMap<String, Integer>() {
        {
            put("xs", 32);
            put("sm", 48);
            put("md", 64);
            put("lg", 128);
            put("xl", 256);
            put("xxl", 512);
        }
    };

    private static final int MAX_AGE = 1800;

    private final int maxAge;
    private final String defaultAvatar;
    private final boolean alwaysRedirect;
    private final String defaultSize;

    private final String storageProviderName;

    public ConfigService(Config.Scope scope) {
        this.maxAge = scope.getInt("maxAge", MAX_AGE);
        this.defaultAvatar = scope.get("defaultAvatar", "/");
        this.alwaysRedirect = scope.getBoolean("alwaysRedirect", false);
        this.defaultSize = scope.get("defaultSize", "lg");
        this.storageProviderName = Objects.requireNonNullElse(System.getenv("STORAGE_PROVIDER"), "local");
    }

    public IStorageProvider getStorageService(KeycloakSession session) {

      IStorageProvider storageProvider = session.getProvider(IStorageProvider.class, storageProviderName);

      if(storageProvider == null) {
        throw new RuntimeException("ConfigService: Storage provider not found");
      }

      return storageProvider;
    }

    @Override
    public void close() {

    }
}
