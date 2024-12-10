/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.utils.image; 

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.droplink.keycloak.enums.ImageFileExtensionEnum;

import jakarta.ws.rs.InternalServerErrorException;

public class UtilImageConvert {
    public static ByteArrayInputStream convertImage(InputStream inputStream, ImageFileExtensionEnum imageType) {
      try {
          BufferedImage bufferedImage = ImageIO.read(inputStream);
          ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
          ImageIO.write(bufferedImage, imageType.getMimeType(), byteArrayOut);
          return new ByteArrayInputStream(byteArrayOut.toByteArray());
      } catch (IOException e) {
          throw new InternalServerErrorException(e);
      }
  }
}