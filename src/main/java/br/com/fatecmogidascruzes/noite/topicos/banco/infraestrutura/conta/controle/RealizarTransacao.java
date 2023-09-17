package br.com.fatecmogidascruzes.noite.topicos.banco.infraestrutura.conta.controle;

import br.com.fatecmogidascruzes.noite.topicos.banco.comum.LocalizadorServico;
import br.com.fatecmogidascruzes.noite.topicos.banco.comum.RealizarTransacaoDTO;
import br.com.fatecmogidascruzes.noite.topicos.banco.comum.TransacaoDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.AbrirConta;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.Depositar;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.PesquisarContas;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.PesquisarTransacoes;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.RealizarPix;
import br.com.fatecmogidascruzes.noite.topicos.banco.negocio.conta.casodeuso.Sacar;
import br.com.fatecmogidascruzes.noite.topicos.banco.comum.AberturaContaDTO;
import br.com.fatecmogidascruzes.noite.topicos.banco.comum.ContaDTO;
import com.google.gson.Gson;
import java.util.List;

@WebServlet(name = "RealizarTransacao", urlPatterns = { "/contas/*" })
public class RealizarTransacao extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recurso = request.getRequestURI().substring(request.getContextPath().length());

        ProcessadorURLConta processadorURLConta = new ProcessadorURLConta(recurso);

        if (!processadorURLConta.encontrado()) {
            response.sendError(404);
            return;
        }

        if (processadorURLConta.endpointContas()) {
            PesquisarContas pesquisarContas = LocalizadorServico.pesquisarContas();
            List<ContaDTO> contas = pesquisarContas.executar().stream().map(conta -> ContaDTO.deConta(conta)).toList();

            Gson gson = new Gson();
            String json = gson.toJson(contas);

            response.setContentType("application/json");
            response.getWriter().print(json);
            return;
        } else if (processadorURLConta.endpointContaEspecifica()) {
            PesquisarContas pesquisarContas = LocalizadorServico.pesquisarContas();
            ContaDTO conta = ContaDTO
                    .deConta(pesquisarContas.executar(String.valueOf(processadorURLConta.getIdConta())));

            Gson gson = new Gson();
            String json = gson.toJson(conta);

            response.setContentType("application/json");
            response.getWriter().print(json);
            return;
        } else if (processadorURLConta.endpointTransacoes()) {
            PesquisarTransacoes pesquisarTransacoes = LocalizadorServico.pesquisarTransacoes();
            List<TransacaoDTO> transacoes = pesquisarTransacoes
                    .executar(String.valueOf(processadorURLConta.getIdConta()))
                    .stream()
                    .map(transacao -> TransacaoDTO.deTransacao(transacao))
                    .toList();

            Gson gson = new Gson();
            String json = gson.toJson(transacoes);

            response.setContentType("application/json");
            response.getWriter().print(json);
            return;
        } else {
            PesquisarTransacoes pesquisarTransacoes = LocalizadorServico.pesquisarTransacoes();
            TransacaoDTO transacao = TransacaoDTO.deTransacao(pesquisarTransacoes
                    .executar(String.valueOf(processadorURLConta.getIdConta()),
                            Long.valueOf(processadorURLConta.getIdTransacao())));
            Gson gson = new Gson();
            String json = gson.toJson(transacao);

            response.setContentType("application/json");
            response.getWriter().print(json);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recurso = request.getRequestURI().substring(request.getContextPath().length());

        ProcessadorURLConta processadorURLConta = new ProcessadorURLConta(recurso);

        if (!processadorURLConta.encontrado()) {
            response.sendError(404);
            return;
        }

        if (processadorURLConta.endpointContas()) {
            /*
             * {
             * "numeroConta": "12345",
             * "codigoPix": "abc12345"
             * }
             */
            Gson gson = new Gson();
            AberturaContaDTO contaDTO = gson.fromJson(
                    new BufferedReader(new InputStreamReader(request.getInputStream())), AberturaContaDTO.class);

            AbrirConta abrirConta = LocalizadorServico.abrirConta();
            abrirConta.executar(contaDTO.getNumeroDaConta(), contaDTO.getCodigoPix());
            response.setStatus(HttpServletResponse.SC_CREATED);
            return;
        } else if (processadorURLConta.endpointContaEspecifica()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Não é possivel recriar uma conta");
            return;
        } else if (processadorURLConta.endpointTransacoes()) {
            /*
             * {
             * "tipoTransacao": "SAQUE",
             * "valor": 100.50
             * }
             */
            /*
             * {
             * "tipoTransacao": "DEPOSITO",
             * "valor": 100.50
             * }
             */
            /*
             * {
             * "tipoTransacao": "TRANSFERENCIA",
             * "valor": 100.50
             * "contaDestino": 20
             * }
             */
            Gson gson = new Gson();
            RealizarTransacaoDTO realizarTransacaoDTO = gson.fromJson(
                    new BufferedReader(new InputStreamReader(request.getInputStream())), RealizarTransacaoDTO.class);

            if (realizarTransacaoDTO.eSaque()) {
                Sacar sacar = LocalizadorServico.sacar();
                // Trocar por valores do DTO
                sacar.executar(String.valueOf(processadorURLConta.getIdConta()), realizarTransacaoDTO.getValor());
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else if (realizarTransacaoDTO.eDeposito()) {
                Depositar depositar = LocalizadorServico.depositar();
                // Trocar por valores do DTO
                depositar.executar(String.valueOf(processadorURLConta.getIdConta()), realizarTransacaoDTO.getValor());
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else if (realizarTransacaoDTO.ePix()) {
                RealizarPix realizarPix = LocalizadorServico.realizarPix();
                // Trocar por valores do DTO
                realizarPix.executar(String.valueOf(processadorURLConta.getIdConta()),
                        realizarTransacaoDTO.getCodigoPix(), realizarTransacaoDTO.getValor());
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Não é possivel realizar esse tipo de Operação");
                return;
            }
            return;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Não é possivel realizar esse tipo de Operação");
            return;
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return;
    }

}
