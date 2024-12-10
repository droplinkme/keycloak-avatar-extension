/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.storage.interfaces;

import java.io.IOException;
import java.io.InputStream;

import org.keycloak.provider.Provider;

public interface IStorageProvider extends Provider {
  public String upload(InputStream file, String fileName, String mimeType, String folder) throws IOException;
  public InputStream getFile(String fileName, String folder, boolean noError) throws IOException;
  public void closeClient();
}
