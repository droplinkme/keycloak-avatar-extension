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
import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

public class UtilAvatarCrop {
      public static Map<String, Integer> sizeList = new HashMap<String, Integer>() {
        {
            put("xs", 32);
            put("sm", 48);
            put("md", 64);
            put("lg", 128);
            put("xl", 256);
            put("xxl", 512);
        }
    };

      public static InputStream crop(InputStream input, UtilAvatarCropParams cropParams) throws IOException {
        try {
            Thumbnails.Builder<? extends InputStream> cropper = Thumbnails.of(input).outputFormat("png");
            if (cropParams != null) {
                cropper.sourceRegion(cropParams.x, cropParams.y, cropParams.size, cropParams.size);
            }
            BufferedImage croppedImage = cropper.scale(1).asBufferedImage();
            InputStream is = null;
            for(Map.Entry<String, Integer> entries: sizeList.entrySet()) {
                Thumbnails.Builder<BufferedImage> resizer = Thumbnails.of(croppedImage)
                        .size(entries.getValue(), entries.getValue());

                
                try (ByteArrayOutputStream os = new ByteArrayOutputStream(10240)) {
                    resizer.outputFormat("png").toOutputStream(os);
                    is = new ByteArrayInputStream(os.toByteArray());
                }
                is.close();
            }
            return is;
        } catch (IOException ex){
            throw ex;
        }
    }
}
