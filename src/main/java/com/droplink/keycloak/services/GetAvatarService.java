/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services;

import java.io.IOException;
import java.io.InputStream;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;

import jakarta.ws.rs.NotFoundException;

public class GetAvatarService {
  private final IStorageProvider storage;

  public GetAvatarService(IStorageProvider storage){
    this.storage = storage;
  }

  private final String AVATAR_DEFAULT = "default.png";
  
  public InputStream exec(UserModel user, RealmModel realm, boolean fallback, boolean noError) throws IOException {
    String attribute = user.getFirstAttribute("avatar");

    if(attribute == null){
      if (fallback) {
        return this.storage.getFile(AVATAR_DEFAULT, realm.getName(), noError);
      }
      if(noError){
        return null;
      }
      throw new NotFoundException("Avatar not found");
    }

    return this.storage.getFile(attribute, realm.getName(), noError);
  }
}
