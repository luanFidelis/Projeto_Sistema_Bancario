package BANCO.PROJETO.Controller;

import BANCO.PROJETO.Dto.TransacaoDto;
import BANCO.PROJETO.Model.Transacao;
import BANCO.PROJETO.Service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Transacao> TransacaoPix(@RequestBody TransacaoDto transacaoDto){

        try {
            transacaoService.fazerTransacao(transacaoDto);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {

            throw new RuntimeException(e);
        }



    }
}
