package BANCO.PROJETO.Exception;

import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Exception.Excepitons.SaldoInsuficienteException;
import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContaNaoEncontradaExcepiton.class)

        public ResponseEntity<String> contaNaoEncontrada(ContaNaoEncontradaExcepiton excepiton) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excepiton.getMessage());


        }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> saldoInsuficiente(SaldoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ValorInvalidoException.class)

    public ResponseEntity<String> valorInvalido(ValorInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    }



