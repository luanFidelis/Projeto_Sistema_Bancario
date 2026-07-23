package BANCO.PROJETO.Recebimento.Service;

import BANCO.PROJETO.Conta.Entity.Conta;
import BANCO.PROJETO.Conta.Repository.ContaRepository;
import BANCO.PROJETO.Exception.Excepitons.AcaoNaoRealizada;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import BANCO.PROJETO.Recebimento.Dto.ComprovanteResponse;
import BANCO.PROJETO.Recebimento.Dto.PagamentoCartaoDto;
import BANCO.PROJETO.Transacao.Entity.HistoricoMovimentacao;
import BANCO.PROJETO.Transacao.Entity.Transacao;
import BANCO.PROJETO.Transacao.Enum.SituacaoTransacao;
import BANCO.PROJETO.Transacao.Enum.TipoTransacao;
import BANCO.PROJETO.Transacao.Repository.HistoricoMovimentacaoRepository;
import BANCO.PROJETO.Transacao.Repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Recebedor de pagamentos por cartão (crédito e débito).
 *
 * Papel: o pagador é EXTERNO (rede de cartão). O dinheiro apenas ENTRA na conta
 * que recebe (o beneficiário/lojista) — nenhuma conta interna é debitada. Tanto
 * no crédito quanto no débito o valor é creditado na hora, e a movimentação é
 * registrada em Transacao + HistoricoMovimentacao, no mesmo padrão do
 * TransacaoService.transacaoDebito().
 *
 * A autorização/cobrança do cartão em si (Luhn, limite, senha etc.) é a tua
 * parte e acontece antes de chamar este serviço.
 */
@Service
public class RecebimentoService {

    // representa a "rede de cartão" externa na coluna de origem (limite 14 do schema)
    private static final String ORIGEM_EXTERNA = "REDE-CARTAO";
    private static final String CPF_EXTERNO = "EXTERNO";

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final HistoricoMovimentacaoRepository historicoMovimentacaoRepository;

    public RecebimentoService(ContaRepository contaRepository,
                              TransacaoRepository transacaoRepository,
                              HistoricoMovimentacaoRepository historicoMovimentacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.historicoMovimentacaoRepository = historicoMovimentacaoRepository;
    }

    @Transactional
    public ComprovanteResponse receber(PagamentoCartaoDto dto) {

        // 1. só crédito e débito passam por aqui (PIX tem fluxo próprio)
        if (dto.tipo() != TipoTransacao.CREDITO && dto.tipo() != TipoTransacao.DEBITO) {
            throw new AcaoNaoRealizada("Este recebedor aceita apenas CREDITO ou DEBITO.");
        }

        // 2. valor válido
        if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Valor inválido");
        }

        // 3. conta que vai receber
        Conta contaDestino = contaRepository.findByNumeroConta(dto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta que recebe não encontrada"));

        LocalDateTime horaTransacao = LocalDateTime.now();
        String numeroProcesso = UUID.randomUUID().toString();

        // 4. o dinheiro ENTRA na conta que recebe (crédito e débito caem na hora)
        contaDestino.setSaldo(contaDestino.getSaldo().add(dto.valor()));
        contaRepository.save(contaDestino);

        // 5. registro da transação (origem = rede externa de cartão)
        String cpfDestino = "";
        String nomeDestino = "";

        if( contaDestino.getUsuario() != null ){
            cpfDestino = contaDestino.getUsuario().getCpf();
            if (contaDestino.getUsuario() != null) {
                nomeDestino = contaDestino.getUsuario().getCpf();
            }
            else{
                throw new AcaoNaoRealizada("Erro,Erro, Cpf destino não encotrado .");
            }
        }
        else{
            throw new AcaoNaoRealizada("Erro, Cpf de origem não encontrado .");
        }


        Transacao transacao = new Transacao(
                CPF_EXTERNO,
                cortar14(dto.nomePagador()),
                cortar14(cpfDestino),
                cortar14(nomeDestino),
                dto.valor(),
                horaTransacao,
                SituacaoTransacao.APROVADO,
                dto.tipo()
        );
        transacao.setNumeroContaOrigem(mascararCartao(dto.numeroCartao()));
        transacao.setNumeroContaDestino(contaDestino.getNumeroConta());
        transacaoRepository.save(transacao);

        // 6. histórico de movimentação
        HistoricoMovimentacao historico = new HistoricoMovimentacao(
                ORIGEM_EXTERNA,
                contaDestino.getNumeroConta(),
                horaTransacao,
                SituacaoTransacao.APROVADO,
                numeroProcesso
        );
        historicoMovimentacaoRepository.save(historico);

        String mensagem = "";

        if(dto.tipo() == TipoTransacao.DEBITO){
            mensagem = "Débito recebido e creditado na hora.";
        } else if (dto.tipo() == TipoTransacao.CREDITO) {
            mensagem = "Crédito recebido e creditado na hora.";

        }
        else{
            throw new AcaoNaoRealizada("Erro, não foi possivel realizar a ação.");
        }
        return new ComprovanteResponse(
                SituacaoTransacao.APROVADO,
                dto.tipo(),
                dto.valor(),
                contaDestino.getNumeroConta(),
                nomeDestino,
                dto.nomePagador(),
                contaDestino.getSaldo(),
                numeroProcesso,
                horaTransacao,
                mensagem
        );
    }

    /** Colunas de origem/nome no schema têm length 14 — corta para não estourar. */
    private String cortar14(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.length() > 14 ? valor.substring(0, 14) : valor;
    }

    /** Deixa só os 4 últimos dígitos, ex.: ****1234 (cabe no length 14). */
    private String mascararCartao(String numeroCartao) {
        if (numeroCartao == null) {
            return ORIGEM_EXTERNA;
        }
        String so = numeroCartao.replaceAll("\\D", "");
        if (so.length() < 4) {
            return ORIGEM_EXTERNA;
        }
        return "****" + so.substring(so.length() - 4);
    }
}
