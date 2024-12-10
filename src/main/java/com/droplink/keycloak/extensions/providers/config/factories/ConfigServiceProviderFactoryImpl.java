package com.droplink.keycloak.extensions.providers.config.factories;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.droplink.keycloak.extensions.providers.config.ConfigService;

public class ConfigServiceProviderFactoryImpl implements ConfigServiceProviderFactory {
    private ConfigService instance;

    @Override
    public ConfigService create(KeycloakSession session) {
        return instance;
    }

    @Override
    public void init(Config.Scope scope) {
        instance = new ConfigService(scope);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) { }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "config";
    }
}
