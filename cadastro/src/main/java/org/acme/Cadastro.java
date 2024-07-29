package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.FormParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Path("/cadastro")
@Transactional
public class Cadastro {



    @Inject
    @RestClient
    IMessage service;


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message cpfVerify(@FormParam("cpf") String cpf, @FormParam("password") String userPassword) throws NoSuchAlgorithmException {

        Message mensagem = service.confirm(cpf);
        if(mensagem.isStatus()) {
            User resultUser = User.find("cpf", cpf).firstResult();
            //System.out.println(resultUser.getCpf());
            if ( resultUser == null) {

                // TODO hash de senha vinda do formulario de cadastro para armazenamento no banco de dados
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(userPassword.getBytes(), 0, userPassword.length());
                BigInteger passwordHash = new BigInteger(1, md.digest());
                String hashedPassword = String.format("%1$032X", passwordHash);

                //System.out.println(hashedPassword);

                User user = new User(cpf, hashedPassword);
                user.persist();
            }else{
                mensagem.setMsg("Usuário já existe!");
                mensagem.setStatus(false);
            }
        }

        return mensagem;
    }
}
