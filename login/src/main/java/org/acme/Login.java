package org.acme;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.eclipse.microprofile.rest.client.inject.RestClient;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
@Transactional
public class Login {

    @Inject
    @RestClient
    IMessage service;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message login(@FormParam("cpf") String cpf, @FormParam("password") String userPassword)
            throws NoSuchAlgorithmException {

        Message mensagem = service.confirm(cpf);

        if (mensagem.isStatus()) {

            User resultUser = User.find("cpf", cpf).firstResult();
            if (resultUser == null) {
                mensagem.setMsg("Usuário não existe!");
                mensagem.setStatus(false);
            } else {

                // Produz hash para comparação com o banco de dados
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(userPassword.getBytes(), 0, userPassword.length());
                BigInteger passwordHash = new BigInteger(1, md.digest());
                String hashedPassword = String.format("%1$032X", passwordHash);

                // System.out.println(hashedPassword);

                User user = new User(cpf, hashedPassword);

                if (resultUser.getPassword().equals(user.getPassword())) {
                    mensagem.setMsg("Usuário Logado!");

                } else {

                    mensagem.setStatus(false);
                    mensagem.setMsg("Credenciais Inválidas!");
                }

            }

        }

        return mensagem;
    }
}
