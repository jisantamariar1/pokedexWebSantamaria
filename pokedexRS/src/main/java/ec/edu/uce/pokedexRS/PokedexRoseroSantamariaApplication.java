package ec.edu.uce.pokedexRS;

import ec.edu.uce.pokedexRS.api.PokemonApiClient;
import ec.edu.uce.pokedexRS.api.PokemonInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.CompletableFuture;

//Segundo deber de programación avanzada 2
//Santamaria Joan
//Fecha:16/02/2025
@SpringBootApplication
public class PokedexRoseroSantamariaApplication {


    public static void main(String[] args) {
        SpringApplication.run(PokedexRoseroSantamariaApplication.class, args);


    }

    @Bean
    public CommandLineRunner run(PokemonApiClient pokemonApiClient, PokemonInfo pokemonInfo) {
        return args -> {
            // Ejecutar la carga de datos en un hilo separado
            CompletableFuture.runAsync(() -> {
                try {
                    pokemonApiClient.fetch(pokemonInfo); // Obtiene y guarda los Pokémon
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        };
    }


}
