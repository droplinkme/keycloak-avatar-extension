/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.storage.implementations.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.keycloak.models.KeycloakSession;

import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;

public class LocalStorageProvider implements IStorageProvider {

    public final KeycloakSession session;
    public final LocalStorageConfigProvider config;
    protected final String Environment = System.getenv("ENVIRONMENT") != null ? System.getenv("ENVIRONMENT") : "Development";

    public LocalStorageProvider(KeycloakSession session, LocalStorageConfigProvider config){
        this.session = session;
        this.config = config;
    }

    @Override
    public String upload(InputStream file, String fileName, String mimeType, String folder) throws IOException {
        String key = this.Environment.concat("/").concat(folder).concat("/").concat(fileName);
        String path = config.STORAGE_PATH.concat(key);
        File avatarFile = new File(path);

        File avatarFolderPath = avatarFile.getParentFile();
        if(avatarFolderPath != null && !avatarFolderPath.exists()){
            if(!avatarFolderPath.mkdirs()){
              throw new InternalServerErrorException("Error while try create memory folder");
            }
        }
        try {
            try (FileOutputStream avatarFileStream = new FileOutputStream(avatarFile)) {
                byte[] buffer = new byte[10240];
                int bufferLen;
                while((bufferLen = file.read(buffer)) > 0){
                    avatarFileStream.write(buffer, 0, bufferLen);
                }
            }
            return path;
        } catch(IOException e){
            throw e;
        }
    }


    @Override
    public InputStream getFile(String fileName, String folder, boolean noError) throws IOException {
        String key = this.Environment.concat("/").concat(folder).concat("/").concat(fileName);
        String path = config.STORAGE_PATH.concat(key);

        File file = new File(path);

        if (!file.exists()) {
          if (noError) {
            return null;
          }
          throw new NotFoundException("Image not found");
        }

        return new FileInputStream(file);
    }


    @Override
    public void close() {}

    @Override
    public void closeClient(){}
  
}
