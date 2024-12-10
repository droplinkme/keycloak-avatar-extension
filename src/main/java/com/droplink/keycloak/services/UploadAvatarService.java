/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.enums.ImageFileExtensionEnum;
import com.droplink.keycloak.enums.ImageFileMimeTypeEnum;
import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;
import com.droplink.keycloak.utils.hash.UtilAvatarHashKey;
import com.droplink.keycloak.utils.image.UtilAvatarCrop;
import com.droplink.keycloak.utils.image.UtilImageConvert;

public class UploadAvatarService {
  private static final String AVATAR_PREFIX = "user-avatar";
  private final IStorageProvider storage;

  public UploadAvatarService(IStorageProvider storage){
    this.storage = storage;
  }
  
  public String exec(UserModel user, RealmModel realm, InputStream file) throws IOException {
    ByteArrayInputStream imageByteArrayIn = UtilImageConvert.convertImage(file, ImageFileExtensionEnum.PNG);
    file = UtilAvatarCrop.crop(imageByteArrayIn, null);
    String hash = UtilAvatarHashKey.hash(realm.getId(), user.getId());
    String fileName = AVATAR_PREFIX
      .concat("-")
      .concat(hash)
      .concat(".")
      .concat(ImageFileExtensionEnum.PNG.name().toLowerCase());

    user.setAttribute("avatar", List.of(fileName));

    this.storage.upload(file, fileName, ImageFileMimeTypeEnum.fromExtension(ImageFileExtensionEnum.PNG.getMimeType()).getValue(), realm.getName());
    storage.closeClient();
    return fileName;
  }
}
