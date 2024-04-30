package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.FormParam;
import org.acme.IMessage;
import org.acme.Message;

import org.eclipse.microprofile.rest.client.inject.RestClient;




@Path("/hello")
public class Cadastro {

    @Inject
    @RestClient
    IMessage service;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message hello(@FormParam("cpf") String cpf) {
        return service.confirm(cpf);
    }
}
