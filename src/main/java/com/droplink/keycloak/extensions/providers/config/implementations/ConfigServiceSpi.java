package com.droplink.keycloak.extensions.providers.config.implementations;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

import com.droplink.keycloak.extensions.providers.config.ConfigService;
import com.droplink.keycloak.extensions.providers.config.factories.ConfigServiceProviderFactory;

public class ConfigServiceSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "avatar-provider";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ConfigService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ConfigServiceProviderFactory.class;
    }
}
