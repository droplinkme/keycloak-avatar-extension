package com.droplink.keycloak.enums;

public enum ImageFileExtensionEnum {
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    TIFF("tiff");

    private final String mimeType;

    ImageFileExtensionEnum(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static ImageFileExtensionEnum fromExtension(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return JPEG;
            case "png":
                return PNG;
            case "gif":
                return GIF;
            case "bmp":
                return BMP;
            case "tiff":
            case "tif":
                return TIFF;
            default:
                throw new IllegalArgumentException("Unknown image file extension: " + extension);
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
