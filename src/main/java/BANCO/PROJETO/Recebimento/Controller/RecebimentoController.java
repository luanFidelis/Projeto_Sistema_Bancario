package BANCO.PROJETO.Recebimento.Controller;

import BANCO.PROJETO.Recebimento.Dto.ComprovanteResponse;
import BANCO.PROJETO.Recebimento.Dto.PagamentoCartaoDto;
import BANCO.PROJETO.Recebimento.Service.RecebimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint que RECEBE pagamento por cartão (crédito e débito) e credita o
 * beneficiário.
 *
 *   POST /recebimento/cartao  -> credita a conta que recebe e devolve o comprovante
 */
@RestController
@RequestMapping("/recebimento")
public class RecebimentoController {

    private final RecebimentoService recebimentoService;

    public RecebimentoController(RecebimentoService recebimentoService) {
        this.recebimentoService = recebimentoService;
    }

    @PostMapping("/cartao")
    public ResponseEntity<ComprovanteResponse> receberCartao(@Valid @RequestBody PagamentoCartaoDto dto) {
        ComprovanteResponse comprovante = recebimentoService.receber(dto);
        return ResponseEntity.status(HttpStatus.OK).body(comprovante);
    }
}
