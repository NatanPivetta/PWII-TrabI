package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;


@Path("/login")
@Transactional
public class Login {



    @Inject
    @RestClient
    IMessage service;


    



    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message cpfVerify(@FormParam("cpf") String cpf, @FormParam("password") String userPassword) throws NoSuchAlgorithmException {

        Message mensagem = service.confirm(cpf);

        if(mensagem.isStatus()){

            User resultUser = User.find("cpf", cpf).firstResult();


           // Produz hash para comparação com o banco de dados
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(userPassword.getBytes(),0,userPassword.length());
            BigInteger passwordHash = new BigInteger(1, md.digest());
            String hashedPassword = String.format("%1$032X", passwordHash);

            //System.out.println(hashedPassword);

            User user = new User(cpf, hashedPassword);

            if(resultUser.getPassword().equals(user.getPassword())){
                mensagem.setMsg("Login Successful");
            }else{

                mensagem.setStatus(false);
                mensagem.setMsg("Credenciais Inválidas!");
            }

            System.out.println(mensagem.isStatus());


        }

        return mensagem;
    }
}
