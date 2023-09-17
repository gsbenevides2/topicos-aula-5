package br.com.fatecmogidascruzes.noite.topicos.banco.comum;

import br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.persistencia.ContaRepositorioJPA;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldo;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.politica.PoliticaValidacaoSaldoComum;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.Contas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.AbrirConta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.Depositar;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.PesquisarContas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.PesquisarTransacoes;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.RealizarPix;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.Sacar;

public class LocalizadorServico {

    public static PoliticaValidacaoSaldo politicaAtualizacaoSaldo() {
        return new PoliticaValidacaoSaldoComum();
    }

    public static Contas contaRepositorio() {
        EntityManagerFactory fabricaDAOGenerico = Persistence.createEntityManagerFactory("AplicacaoBancariaPU");
        ContaRepositorioJPA contaRepositorio = new ContaRepositorioJPA(fabricaDAOGenerico.createEntityManager());
        return contaRepositorio;
    }

    public static RealizarPix realizarPix() {
        return new RealizarPix(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static Depositar depositar() {
        return new Depositar(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static Sacar sacar() {
        return new Sacar(contaRepositorio(), politicaAtualizacaoSaldo());
    }

    public static AbrirConta abrirConta() {
        return new AbrirConta(contaRepositorio());
    }

    public static PesquisarContas pesquisarContas() {
        return new PesquisarContas(contaRepositorio());
    }
    public static PesquisarTransacoes pesquisarTransacoes() {
        return new PesquisarTransacoes(contaRepositorio());
    }
}
