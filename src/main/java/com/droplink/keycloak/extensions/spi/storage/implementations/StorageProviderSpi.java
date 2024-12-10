/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.extensions.spi.storage.implementations;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

import com.droplink.keycloak.extensions.spi.storage.factories.StorageProviderFactory;
import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;


public class StorageProviderSpi implements Spi {
    public static final String ID = "avatar-storage";

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return IStorageProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return StorageProviderFactory.class;
    }
}
