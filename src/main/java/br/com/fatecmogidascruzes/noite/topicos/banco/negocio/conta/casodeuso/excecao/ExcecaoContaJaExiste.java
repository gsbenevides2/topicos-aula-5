package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.excecao;

public class ExcecaoContaJaExiste extends RuntimeException {

    public ExcecaoContaJaExiste(String mensagem) {
        super(mensagem);
    }
    
}