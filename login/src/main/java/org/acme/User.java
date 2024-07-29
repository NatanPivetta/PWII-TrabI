package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.io.Serializable;

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
}
