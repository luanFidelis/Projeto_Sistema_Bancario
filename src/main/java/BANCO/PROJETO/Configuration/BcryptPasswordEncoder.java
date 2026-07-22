package BANCO.PROJETO.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class BcryptPasswordEncoder {


    public static String esconderDados(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }
}
