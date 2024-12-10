package com.droplink.keycloak.enums;

public enum ImageFileMimeTypeEnum {
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/bmp"),
    TIFF("image/tiff");

    private final String value;

    ImageFileMimeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ImageFileMimeTypeEnum fromExtension(String extension) {
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

    public static ImageFileMimeTypeEnum fromMimeType(String mimeType) {
        for (ImageFileMimeTypeEnum type : ImageFileMimeTypeEnum.values()) {
            if (type.getValue().equals(mimeType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown MIME type: " + mimeType);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
