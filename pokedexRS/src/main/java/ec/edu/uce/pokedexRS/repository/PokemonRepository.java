package ec.edu.uce.pokedexRS.repository;

import ec.edu.uce.pokedexRS.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByName(String name);
    Optional<Pokemon> findById(long id);
    Optional<Pokemon> findByNameIgnoreCase(String name);
    List<Pokemon> findByGenerationId(Long generationId);  // Método para buscar Pokémon por generación
    List<Pokemon> findByTypes_NameIgnoreCase(String typeName);
    List<Pokemon> findByHabitat_Id(Long habitatId);  // Modificar para recibir ID en lugar de nombre
    List<Pokemon> findBySpecies_Id(Long speciesId);  // Nuevo método para buscar Pokémon por ID de especie





}
