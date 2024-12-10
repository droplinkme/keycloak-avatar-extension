/*
 * Extension for upload and get avatar.
 * @author Allan Vieira (allancnfx.vieira@gmail.com)
 * @version 1.0
 */
package com.droplink.keycloak.web.cors;

import java.io.IOException;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.cors.Cors;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {

  @Context
  private KeycloakSession session;
  
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    if (requestContext.getUriInfo().getPath().endsWith("avatar")) {
        Cors cors = session.getProvider(Cors.class);
        Response.ResponseBuilder response = Response.ok();
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.header("Access-Control-Allow-Headers", requestContext.getHeaderString("Access-Control-Request-Headers"));
        cors.add(response);
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
          requestContext.abortWith(response.build());
        }
      }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (requestContext.getUriInfo().getPath().endsWith("avatar")) {
          if (!requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", requestContext.getHeaderString("Access-Control-Request-Headers"));
          }
        }
    }
}
