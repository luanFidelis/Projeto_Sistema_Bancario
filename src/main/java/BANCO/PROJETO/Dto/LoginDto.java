package BANCO.PROJETO.Dto;
import jakarta.validation.constraints.NotBlank;
public record LoginDto(

        @NotBlank
        String cpf,
        @NotBlank
        String senha) {
}
