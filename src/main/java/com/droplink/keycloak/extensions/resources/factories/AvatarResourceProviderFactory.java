package com.droplink.keycloak.extensions.resources.factories;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

import com.droplink.keycloak.extensions.providers.config.ConfigService;
import com.droplink.keycloak.extensions.resources.implementations.AvatarResourceProvider;

public class AvatarResourceProviderFactory implements RealmResourceProviderFactory {
    public static final String ID = "avatar";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
      ConfigService config = session.getProvider(ConfigService.class);

      return new AvatarResourceProvider(session, config);
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
      return ID;
    }
}
