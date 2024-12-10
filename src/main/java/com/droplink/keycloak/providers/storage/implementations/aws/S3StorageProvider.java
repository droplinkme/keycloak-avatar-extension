/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.providers.storage.implementations.aws;

import java.io.IOException;
import java.io.InputStream;

import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3StorageProvider extends S3StorageConfigProvider implements IStorageProvider {
    private final S3Client client;
    public S3StorageProvider() {
      super();

      if ("Development".equals(this.Environment)) {
        this.client = S3Client.builder()
          .region(this.AWS_REGION)
          .credentialsProvider(StaticCredentialsProvider.create(credentials))
          .build();
      }else{
        this.client = S3Client.builder()
          .region(this.AWS_REGION)
          .build();
      }
    }

    @Override
    public String upload(InputStream file, String fileName, String mimeType, String folder) throws IOException {
        try {
            String key = this.Environment.concat("/").concat(folder).concat("/").concat(fileName);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(this.AWS_BUCKET_NAME)
                    .key(key)
                    .acl(this.AWS_BUCKET_ACL)
                    .contentType(mimeType)           
                    .build();

            PutObjectResponse putObjectResponse = client.putObject(request,
                    software.amazon.awssdk.core.sync.RequestBody.fromInputStream(file, file.available()));

            return putObjectResponse.eTag();
        } catch (S3Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file to S3", e);
        } catch (IOException ex) {
          throw ex;
        }
    }

    @Override
    public InputStream getFile(String fileName, String folder, boolean noError) throws IOException {
        String key = this.Environment.concat("/").concat(folder).concat("/").concat(fileName);

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(AWS_BUCKET_NAME)
                    .key(key)
                    .build();


            return this.client.getObject(request);
          } catch (S3Exception e) {
            if (noError) {
              return null;
            }
            e.printStackTrace();
            throw new IOException("Error retrieving file from S3", e);
        }
    }

    @Override
    public void close() {}

    @Override
    public void closeClient(){
      client.close();
    }
}
