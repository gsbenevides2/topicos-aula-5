package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

public class AberturaContaDTO {
    private String numeroDaConta;
    private String codigoPix;

    private AberturaContaDTO() {}

    public String getNumeroDaConta() {
        return this.numeroDaConta;
    }

    public String getCodigoPix() {
        return this.codigoPix;
    }
}
