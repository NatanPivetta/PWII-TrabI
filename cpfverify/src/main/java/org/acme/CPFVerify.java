package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;



@Path("/hello")
public class CPFVerify {

    //vetor com os pesos para realizar calculo do digito verificador
    private int[] VerifyEntryVector = new int [10];
    private int[] EntryVector = new int[11];
    private int VerificatorDigitOne;
    private int VerificatorDigitTwo;





    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@FormParam("cpf") String cpf) {

        

            
            for (int i=0; i<VerifyEntryVector.length;i++){
                VerifyEntryVector[i] = i;
            }
            
        for (int i=0; i<EntryVector.length;i++){
            EntryVector[i] = Character.getNumericValue(cpf.charAt(i));
        }

        if(verificaEqual()) return "CPF Inválido!";
        if(cpf.length() > 11)return "CPF Muito Grande!";

        this.VerificatorDigitOne = verificadorOne();
        this.VerificatorDigitTwo = verificadorTwo();
        System.out.println(this.VerificatorDigitOne + "" + this.VerificatorDigitTwo);
        return "Hello RESTEasy";
    }

    public boolean verificaEqual(){
        int primeiroDigito = EntryVector[0];
        for (int i=0; i<EntryVector.length;i++){
            if(EntryVector[i] != primeiroDigito){
                return false;
            }
        }

        return true;


    }

    public int verificadorOne(){
        int sum = 0;
        int j = 1;
        for (int i=0; i<9;i++) {
            sum += (VerifyEntryVector[j] * EntryVector[i]);
            j++;
        }
        if(sum %11 == 10) {
            return 0;
        }else{
            return sum %11;
        }
    }

    public int verificadorTwo(){
        int sum = 0;
        int j = 0;
        for (int i=0; i<10;i++) {
            sum += (VerifyEntryVector[j] * EntryVector[i]);
            j++;
        }
        if(sum %11 == 10) {
            return 0;
        }else{
            return sum %11;
        }
    }

    
}
