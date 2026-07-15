package BANCO.PROJETO.Conta.Controller;

import BANCO.PROJETO.Conta.Service.ContaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService){

        this.contaService = contaService;
    }
}
