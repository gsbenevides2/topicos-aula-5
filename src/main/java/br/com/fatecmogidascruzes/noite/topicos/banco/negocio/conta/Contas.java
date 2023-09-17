package br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta;

import java.util.List;

public interface Contas {

    void abrir(Conta conta);

    void atualizar(Conta conta);

    Conta consultarPorNumero(String numero);
    
    Conta consultarPorPix(String codigoPix);

    List<Conta> listarTodas();
    
    void iniciarTransacao();
    
    void confirmarTransacao();
    
    void cancelarTransacao();
    
}
