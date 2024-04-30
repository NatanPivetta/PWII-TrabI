package org.acme;

import java.beans.ConstructorProperties;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@RegisterRestClient(baseUri = "http://localhost:8080")
public interface IMessage {
    



    @POST
    @Path("/cpfverify")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Message confirm(
        @FormParam("cpf") String cpf);
}
