package BANCO.PROJETO.Exception.Excepitons;

public class AcaoNaoRealizada extends RuntimeException {
    public AcaoNaoRealizada(String message) {
        super(message);
    }
}
