package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

enum TipoTransacao {
    DEPOSITO, SAQUE, TRANSFERENCIA, PIX
}

public class RealizarTransacaoDTO {
    private TipoTransacao tipo;
    private double valor;
    private String descricao; 
    private String codigoPix;

    private RealizarTransacaoDTO() {}

    public TipoTransacao getTipo() {
        return this.tipo;
    }
    public double getValor() {
        return this.valor;
    }
    public String getDescricao() {
        return this.descricao;
    }

    public String getCodigoPix() {
        return this.codigoPix;
    }

    public boolean eSaque() {
        return this.tipo == TipoTransacao.SAQUE;
    }
    public boolean eDeposito() {
        return this.tipo == TipoTransacao.DEPOSITO;
    }
    public boolean eTransferencia() {
        return this.tipo == TipoTransacao.TRANSFERENCIA;
    }

    public boolean ePix() {
        return this.tipo == TipoTransacao.PIX;
    }
}
