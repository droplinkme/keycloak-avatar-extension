/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.web.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.tika.Tika;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public final class AvatarRequestBody {
    private final Tika tika = new Tika();
    private static final int MAX_FILE_SIZE_BYTES = 1 * 1024 * 1024; // 1 MB
    
    @FormParam("file")
    @PartType(MediaType.MULTIPART_FORM_DATA)
    private InputStream file;

    @FormParam("file")
    @PartType(MediaType.MULTIPART_FORM_DATA)
    private InputStream fileValidate;

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
      this.file = file;
    }

    public void setFileValidate(InputStream fileValidate) {
      this.fileValidate = fileValidate;
    }

    public void validate() throws IOException{
      if(file == null){
        throw new BadRequestException("File not provided");
      }

      int size = this.file.available();

      if(size > MAX_FILE_SIZE_BYTES){
        throw new BadRequestException("Limit max 1mb");
      }

      this.mimeTypeValidate("image");
    }

    public String mimeTypeValidate(String type) throws IOException {
      String mimeType = this.tika.detect(fileValidate);

      if (mimeType == null || !mimeType.startsWith(type)) {
        throw new BadRequestException("Only image files are allowed");
      }

      return mimeType;
    }

    protected InputStream cloneFile() throws IOException {
        InputStream is = getFile();
        PipedInputStream in = new PipedInputStream();
        TeeInputStream tee = new TeeInputStream(is, new PipedOutputStream(in));
        setFile(file);
        return tee; 
    }
}
