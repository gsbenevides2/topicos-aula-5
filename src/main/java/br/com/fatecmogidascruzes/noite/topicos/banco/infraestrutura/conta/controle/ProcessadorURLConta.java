package br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.controle;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessadorURLConta {

    private final String recurso;
    private boolean encontrado;

    private enum Acoes {
        CONTAS, CONTA_ESPECIFICA, TRANSACOES, TRANSACAO_ESPECIFICA
    };

    private Acoes acao;
    private int idConta;
    private int idTransacao;

    public ProcessadorURLConta(String recurso) {
        this.recurso = Objects.requireNonNull(recurso, "O recurso é obrigatório");
        processarRecurso();
    }

    private void processarRecurso() {
        Pattern regex = Pattern.compile("\\/contas(\\/([0-9]+)(\\/transacoes)?(\\/([0-9]+))?)?");

        Matcher matcher = regex.matcher(recurso);
        encontrado = matcher.find();

        if (!encontrado) {
            return;
        }

        String idContaStr = matcher.group(2);
        String transacoes = matcher.group(3);
        String idTransacaoStr = matcher.group(4);
        if (idTransacaoStr != null) {
            idTransacaoStr = idTransacaoStr.replace("/", "");
        }

        if (idContaStr == null) {
            acao = Acoes.CONTAS;
            return;
        }

        idConta = Integer.valueOf(idContaStr);

        if (transacoes == null) {
            acao = Acoes.CONTA_ESPECIFICA;
        } else if (idTransacaoStr == null) {
            acao = Acoes.TRANSACOES;
        } else {
            acao = Acoes.TRANSACAO_ESPECIFICA;
            idTransacao = Integer.valueOf(idTransacaoStr);
        }
    }

    public boolean encontrado() {
        return encontrado;
    }

    public boolean endpointContas() {
        return acao == Acoes.CONTAS;
    }

    public boolean endpointContaEspecifica() {
        return acao == Acoes.CONTA_ESPECIFICA;
    }

    public boolean endpointTransacoes() {
        return acao == Acoes.TRANSACOES;
    }

    public boolean endpointTransacaoEspecifica() {
        return acao == Acoes.TRANSACAO_ESPECIFICA;
    }

    public int getIdConta() {
        return idConta;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

}
