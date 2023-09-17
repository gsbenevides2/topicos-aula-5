package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso;

import java.util.List;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Transacao;

public class PesquisarTransacoes {
    private final Contas contas;
    public PesquisarTransacoes(Contas contas){
        this.contas = contas;
    }

    public List<Transacao> executar(String numeroConta){
        List<Transacao> transacoes = this.contas.consultarPorNumero(numeroConta).getTransacoes();
        return transacoes;
    }

    public Transacao executar(String numeroConta, Long idTransacao){
        Transacao transacao = this.contas
            .consultarPorNumero(numeroConta)
            .getTransacoes()
            .stream()
            .filter(t -> t.getId().equals(idTransacao))
            .findFirst()
            .get();
        return transacao;
    }
}
