/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.utils.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilAvatarHashKey {
    public static String hash(String realmId, String userId) {
      String hash = md5(realmId + userId);
      if (hash == null) {
          return userId;
      } else {
          return String.format("%s-%s-%s-%s",
                  hash.substring(0, 8),hash.substring(8, 16), hash.substring(16, 24), hash.substring(24, 32));
      }
  }

  public static String md5(String input){
    try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes(StandardCharsets.UTF_8));
        return bin2hex(md.digest());
    } catch(NoSuchAlgorithmException e){
        return null;
    } 
  }

  public static String bin2hex(byte[] input){
      BigInteger bigInt = new BigInteger(1, input);
      StringBuilder hashText = new StringBuilder(bigInt.toString(16));
      while(hashText.length() < 32){
          hashText.insert(0, "0");
      }
      return hashText.toString();
  }
}
