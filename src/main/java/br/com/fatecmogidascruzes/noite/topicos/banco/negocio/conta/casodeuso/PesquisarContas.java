package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Conta;
import java.util.List;

public class PesquisarContas {
    private final Contas contas;
    public PesquisarContas(Contas contas){
        this.contas = contas;
    };
    
    public List<Conta> executar(){
        List<Conta> contas = this.contas.listarTodas();
        return contas;
    }

    public Conta executar(String numeroConta){
        Conta conta = this.contas.consultarPorNumero(numeroConta);
        
        return conta;
    }
}