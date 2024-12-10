/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.storage.implementations.local;

import org.keycloak.Config;

import lombok.Getter;

@Getter
public class LocalStorageConfigProvider {
    public final String STORAGE_PATH = "/opt/storage/";

    public final String pathFormat;
    public final String urlRoot;
    public final String defaultAvatar;
    
    public LocalStorageConfigProvider(Config.Scope scope){
        this.pathFormat = scope.get("route", "/{realm}/avatar/{avatar_id}/user-avatar-{hash}.png");
        this.urlRoot = scope.get("baseurl", "/realms/");
        this.defaultAvatar = scope.get("default-avatar", "/{realm}/avatar/default.png");
    }

    public String getPath(String realm, String userId, String avatarId, String size, String tpl) {
        StringBuilder path = new StringBuilder();
        String urlRoot = STORAGE_PATH;
        if ("/".equals(urlRoot.substring(urlRoot.length() - 1))) {
            urlRoot = urlRoot.substring(0, urlRoot.length() - 1);
        }
        path.append(urlRoot);

        path.append(tpl.replace("{realm}", realm).replace("{user_id}", userId)
                .replace("{avatar_id}", avatarId).replace("{hash}", size));
        return path.toString();
    }
}
