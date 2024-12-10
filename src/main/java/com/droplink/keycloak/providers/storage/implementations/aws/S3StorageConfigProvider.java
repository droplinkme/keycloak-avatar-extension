/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.storage.implementations.aws;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

public class S3StorageConfigProvider {
    private final String AWS_ACCESS_KEY_ID = System.getenv("AWS_ACCESS_KEY_ID");
    private final String AWS_SECRET_ACCESS_KEY = System.getenv("AWS_SECRET_ACCESS_KEY");
    protected final Region AWS_REGION = Region.of(System.getenv("AWS_REGION"));
    protected final String AWS_BUCKET_ACL = System.getenv("AWS_BUCKET_ACL");
    protected final String AWS_BUCKET_NAME = System.getenv("AWS_BUCKET_NAME") != null ? System.getenv("AWS_BUCKET_NAME") : "s3-droplink-core-assets";
    protected final String Environment = System.getenv("ENVIRONMENT") != null ? System.getenv("ENVIRONMENT") : "Development";


    protected final AwsBasicCredentials credentials;

    public S3StorageConfigProvider() {
      this.credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);
    }
}
