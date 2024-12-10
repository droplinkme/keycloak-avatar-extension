/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.web.controllers;


import java.io.IOException;
import java.io.InputStream;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.droplink.keycloak.enums.ImageFileMimeTypeEnum;
import com.droplink.keycloak.extensions.providers.config.ConfigService;
import com.droplink.keycloak.providers.storage.interfaces.IStorageProvider;
import com.droplink.keycloak.services.GetAvatarService;
import com.droplink.keycloak.services.UploadAvatarService;
import com.droplink.keycloak.utils.hash.UtilAvatarHashKey;
import com.droplink.keycloak.web.annotations.Authenticated;
import com.droplink.keycloak.web.context.UserContext;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/avatar")
public class AvatarController {
  public AvatarController(){}
  public AvatarController(KeycloakSession session, ConfigService config){
    this.session = session;
    IStorageProvider storage = config.getStorageService(session);
    if(storage == null){
      throw new RuntimeException("Storage provider not found");
    }
    this.uploadAvatar = new UploadAvatarService(storage);
    this.getAvatar = new GetAvatarService(storage);
  }

  @Context
  private KeycloakSession session;
  private UploadAvatarService uploadAvatar;
  private GetAvatarService getAvatar;

  @POST
  @Path("/")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  public Response upload(
    @MultipartForm AvatarRequestBody body
  )  {
    try {
          body.validate();
    UserModel user = UserContext.getUser();
    RealmModel realm = this.session.getContext().getRealm();
    InputStream file = body.getFile();
    this.uploadAvatar.exec(user, realm, file);
    return Response.ok().type(MediaType.APPLICATION_JSON)
      .entity("{\"message\": \"Avatar uploaded successfully\"}")
      .status(201)
      .build();
    } catch (IOException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"" + e.getMessage() + "\"}").build();
    }
  }

  @GET
  @Path("/")
  @Produces({MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON})
  @Authenticated
  public Response get(@QueryParam("fallback") boolean fallback, @QueryParam("no_error") boolean noError)
      throws IOException {
    try {
      UserModel user = UserContext.getUser();
      RealmModel realm = this.session.getContext().getRealm();

      InputStream file = this.getAvatar.exec(user, realm, fallback, noError);

      String tag = UtilAvatarHashKey.hash(realm.getId(), user.getId());
      CacheControl cache = new CacheControl();
      cache.setMaxAge(10);
      cache.setNoStore(true);

      if (file == null) {
        if (noError) {
          return Response.ok()
            .type(MediaType.APPLICATION_JSON)
            .entity("{\"message\": \"No file found\"}")
            .build();
        }
      }

      return Response.ok(file, ImageFileMimeTypeEnum.PNG.getValue())
          .cacheControl(cache)
          .tag(new EntityTag(tag, true))
          .build();
    } catch (IOException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"" + e.getMessage() + "\"}")
          .build();
    }
  }
}