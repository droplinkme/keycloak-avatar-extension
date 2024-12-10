/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.utils.image;

public class UtilAvatarCropParams {
    public int x;
    public int y;
    public int size;

    public UtilAvatarCropParams() { }

    public UtilAvatarCropParams(String paramStr) {
        String[] paramStrList = paramStr.split(",");
        if (paramStrList.length >= 3){
            this.x = Integer.parseInt(paramStrList[0]);
            this.y = Integer.parseInt(paramStrList[1]);
            this.size = Integer.parseInt(paramStrList[2]);
        }
    }
}
