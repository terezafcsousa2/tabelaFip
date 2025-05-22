package br.com.alura.valorCarro;

import br.com.alura.valorCarro.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValorCarroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ValorCarroApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();

    }

}
