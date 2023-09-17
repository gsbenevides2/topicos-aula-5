package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;

public class ContaDTO {
    private String numero;
    private String codigoPix;
    private double saldo;
    
    private ContaDTO() {}

    public static ContaDTO deConta(Conta conta) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.numero = conta.getNumero();
        contaDTO.codigoPix = conta.getCodigoPix();
        contaDTO.saldo = conta.getSaldo();
        return contaDTO;
    }
    
    public String getNumero(){
        return this.numero;
    }
    
    public String getCodigoPix(){
        return this.codigoPix;
    }

    public double getSaldo() {
        return saldo;
    }
}

