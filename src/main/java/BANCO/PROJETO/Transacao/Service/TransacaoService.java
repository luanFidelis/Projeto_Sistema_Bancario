package BANCO.PROJETO.Transacao.Service;

import BANCO.PROJETO.Transacao.Dto.TransacaoDto;
import BANCO.PROJETO.Exception.Excepitons.AcaoNaoRealizada;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Conta.Entity.Conta;


import BANCO.PROJETO.Conta.Repository.ContaRepository;
import BANCO.PROJETO.Transacao.Repository.HistoricoMovimentacaoRepository;

import BANCO.PROJETO.Transacao.Pix.Service.TransacaopixService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;



@Service
public class TransacaoService {


    private final ContaRepository contaRepository;

    private final HistoricoMovimentacaoRepository historicoMovimentacaoRepository;

    public TransacaoService(
                            ContaRepository contaRepository,
                            HistoricoMovimentacaoRepository historicoMovimentacaoRepository) {


        this.contaRepository = contaRepository;

        this.historicoMovimentacaoRepository = historicoMovimentacaoRepository;
    }




    @Transactional
    public TransacaoDto fazerTransacao(TransacaoDto transacaoDto) {

        TransacaopixService transacaopixService = new TransacaopixService();
        String numeroGerado = gerarNumeroProcesso();

        Conta contaQueVaiEnviar = contaRepository.findByNumeroConta(transacaoDto.numeroContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));

        Conta contaQueVaiReceber = contaRepository.findByNumeroConta(transacaoDto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));

        return switch (transacaoDto.tipoTransacao()) {

            case PIX -> transacaopixService.fazerTransacaoPix(
                    contaQueVaiEnviar,
                    contaQueVaiReceber,
                    transacaoDto.valor(),
                    numeroGerado,
                    transacaoDto.tipoTransacao()
            );

            default -> throw new AcaoNaoRealizada("Não foi possível realizar a ação");
        };
    }






    private String gerarNumeroProcesso() {

        String numeroProcesso;
        do {
            numeroProcesso = "NP-" + System.currentTimeMillis();

        } while (historicoMovimentacaoRepository.existsByNumeroProcesso(numeroProcesso));


        return numeroProcesso;
    }

}
