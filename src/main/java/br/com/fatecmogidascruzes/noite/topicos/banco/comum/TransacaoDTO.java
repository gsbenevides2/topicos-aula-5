package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

import java.util.Date;

import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Transacao;

public class TransacaoDTO {
    private Long id;
    private double valor;
    private Date dataHora;
    private String descricao;
    
    private TransacaoDTO() {}


    public static TransacaoDTO deTransacao(Transacao transacao){
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.id = transacao.getId();
        transacaoDTO.valor = transacao.getValor();
        transacaoDTO.dataHora = transacao.getDataHora();
        transacaoDTO.descricao = transacao.getDescricao();
        return transacaoDTO;
    }
    /* 
    public Transacao paraTransacao(){
        Transacao transacao = new Transacao(this.valor, this.descricao);
        transacao.setId(this.id);
        transacao.setDataHora(this.dataHora);
        return transacao;
    }
    */

    public Long getId(){
        return this.id;
    }
    public double getValor(){
        return this.valor;
    }
    public Date getDataHora(){
        return this.dataHora;
    }
    public String getDescricao(){
        return this.descricao;
    }
    
}

