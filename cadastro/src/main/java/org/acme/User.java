package org.acme;

import jakarta.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.io.Serializable;
import java.util.List;

@Entity
public class User extends PanacheEntity implements Serializable {

    private String cpf;
    private String password;

    public User() {

    }

    public User(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return this.cpf;
    }


    public String getPassword() {
        return password;
    }

    public List<User> list() {
        // 3 - O método `listAll` recupera todos os objetos da classe User.
        return User.listAll();
    }
}
