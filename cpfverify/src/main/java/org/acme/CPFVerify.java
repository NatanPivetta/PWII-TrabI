package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;


@Path("/cpfverify")
public class CPFVerify {

    // vetor com os pesos para realizar calculo do digito verificador
    private final int[] WeightDigits = new int[10];
    private int[] EntryVector = new int[11];
    private int VerificatorDigitOne;
    private int VerificatorDigitTwo;
    private String regexStr = "[0-9]+";
    private Message mensagem;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Message entrycpf(@FormParam("cpf") String cpf) {
        for (int i = 0; i < WeightDigits.length; i++) {
            WeightDigits[i] = i;
        }
        if (!VerifyRegexStruct(cpf)) {
            System.out.println("Invalido");
        } else {
                verifyFirstDigit();
                verifySecondDigit();
                if (EntryVector[9] == VerificatorDigitOne
                        && EntryVector[10] == VerificatorDigitTwo) {
                    mensagem = new Message(true, "CPF Válido!");
                }else{
                    mensagem = new Message(false, "CPF Inválido!");
                }
            }
        return this.mensagem;
    }







    public boolean VerifyRegexStruct(String cpf) {

        if (!cpf.matches("[0-9]{11}")) {
            mensagem = new Message(false, "CPF Inválido!");
            return false;
        } else{
            LoadDigitsVector(cpf);
            if(verifyEqualDigits()){
                mensagem = new Message(false, "CPF Inválido!");
                return false;
            }
            mensagem = new Message(false, "CPF Válido!");
            return true;
        }


    }

    public void LoadDigitsVector(String cpf){
        for (int i = 0; i < EntryVector.length; i++) {
            EntryVector[i] = Character.getNumericValue(cpf.charAt(i));
        }
    }


    public boolean verifyEqualDigits() {
        int firstDigit = EntryVector[0];
        for (int i = 0; i < EntryVector.length; i++) {
            if (EntryVector[i] != firstDigit) {
                return false;
            }
        }

        return true;

    }

    public void verifyFirstDigit() {
        int sum = 0;
        int j = 1;
        for (int i = 0; i < 9; i++) {
            sum += (WeightDigits[j] * EntryVector[i]);
            j++;
        }
        if (sum % 11 == 10) {
            VerificatorDigitOne = 0;
        } else {
            VerificatorDigitOne = sum % 11;
        }

    }

    public void verifySecondDigit() {
        int sum = 0;
        int j = 0;
        for (int i = 0; i < 10; i++) {
            sum += (WeightDigits[j] * EntryVector[i]);
            j++;
        }
        if (sum % 11 == 10) {
            VerificatorDigitTwo =  0;
        } else {
            VerificatorDigitTwo = sum % 11;
        }
    }

}
