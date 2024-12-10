/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.spi.storage.factories;


import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import com.droplink.keycloak.providers.storage.implementations.local.LocalStorageConfigProvider;
import com.droplink.keycloak.providers.storage.implementations.local.LocalStorageProvider;
import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;

public class LocalStorageProviderFactory implements StorageProviderFactory {

    private LocalStorageConfigProvider config;
    private static final String ID = "local";

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public IStorageProvider create(KeycloakSession session) {
        try {
            return new LocalStorageProvider(session, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void init(Config.Scope scope) {
        try {
            this.config = new LocalStorageConfigProvider(scope);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
        return ID;
    }
}
