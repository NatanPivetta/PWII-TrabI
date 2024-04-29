package org.acme;

import org.acme.Message;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



@Path("/cpfverify")
public class CPFVerify {

    // vetor com os pesos para realizar calculo do digito verificador
    private int[] VerifyEntryVector = new int[10];
    private int[] EntryVector = new int[11];
    private int VerificatorDigitOne;
    private int VerificatorDigitTwo;
    private String regexStr = "[0-9]+";
    private Message mensagem = new Message(false, "");

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message hello(@FormParam("cpf") String cpf) {

        for (int i = 0; i < VerifyEntryVector.length; i++) {
            VerifyEntryVector[i] = i;
        }

        if (!verificaEstrutura(cpf)) {
            System.out.println("Invalido");
        } else {

            for (int i = 0; i < EntryVector.length; i++) {
                this.EntryVector[i] = Character.getNumericValue(cpf.charAt(i));
            }

            if (!verificaEqual()) {

                this.VerificatorDigitOne = verificadorOne();
                this.VerificatorDigitTwo = verificadorTwo();
                System.out.println(this.verificadorOne() + "" + this.verificadorTwo());
                if (this.EntryVector[9] == this.VerificatorDigitOne
                        && this.EntryVector[10] == this.VerificatorDigitTwo) {
                    this.mensagem.setMsg("CPF Válido");
                    this.mensagem.setStatus(true);
                }
            } else {
                this.mensagem.setMsg("Digitos Identicos");
                this.mensagem.setStatus(false);
            }
        }

        // System.out.println(this.VerificatorDigitOne + "" + this.VerificatorDigitTwo);
        return this.mensagem;
    }

    public boolean verificaEstrutura(String cpf) {

        if (!cpf.matches("[0-9]+")) {
            this.mensagem.setMsg("Apenas numeros!");
            this.mensagem.setStatus(false);
            return false;
        } else if (cpf.length() < 11) {
            this.mensagem.setMsg("CPF Muito Pequeno!");
            this.mensagem.setStatus(false);
            return false;
        } else if (cpf.length() > 11) {

            this.mensagem.setMsg("CPF Muito Grande!");
            this.mensagem.setStatus(false);
            return false;
        }

        return true;
    }

    public boolean verificaEqual() {
        int primeiroDigito = this.EntryVector[0];
        for (int i = 0; i < EntryVector.length; i++) {
            if (this.EntryVector[i] != primeiroDigito) {
                return false;
            }
        }

        return true;

    }

    public int verificadorOne() {
        int sum = 0;
        int j = 1;
        for (int i = 0; i < 9; i++) {
            sum += (VerifyEntryVector[j] * this.EntryVector[i]);
            j++;
        }
        if (sum % 11 == 10) {
            return 0;
        } else {
            return sum % 11;
        }
    }

    public int verificadorTwo() {
        int sum = 0;
        int j = 0;
        for (int i = 0; i < 10; i++) {
            sum += (VerifyEntryVector[j] * this.EntryVector[i]);
            j++;
        }
        if (sum % 11 == 10) {
            return 0;
        } else {
            return sum % 11;
        }
    }

}
