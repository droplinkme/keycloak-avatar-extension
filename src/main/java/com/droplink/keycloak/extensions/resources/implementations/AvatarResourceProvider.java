package com.droplink.keycloak.extensions.resources.implementations;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import com.droplink.keycloak.extensions.providers.config.ConfigService;
import com.droplink.keycloak.web.controllers.AvatarController;


public class AvatarResourceProvider implements RealmResourceProvider {

    private final KeycloakSession keycloakSession;
    private final ConfigService config;

    public AvatarResourceProvider(KeycloakSession keycloakSession, ConfigService config) {
        this.keycloakSession = keycloakSession;
        this.config = config;
    }

    @Override
    public Object getResource() {
        return new AvatarController(this.keycloakSession, this.config);
    }

    @Override
    public void close() {}
}
