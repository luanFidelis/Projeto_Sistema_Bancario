package BANCO.PROJETO.Controller;

import BANCO.PROJETO.Service.ContaService;
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
