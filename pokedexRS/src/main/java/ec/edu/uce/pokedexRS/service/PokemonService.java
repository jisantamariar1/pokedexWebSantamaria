package ec.edu.uce.pokedexRS.service;

// Importación de clases necesarias
import ec.edu.uce.pokedexRS.dto.PokemonDTO;
import ec.edu.uce.pokedexRS.model.Pokemon;
import ec.edu.uce.pokedexRS.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que maneja la lógica de negocio para los Pokémon.
 * Se encarga de interactuar con el repositorio y convertir las entidades a DTOs.
 */
@Service
public class PokemonService {

    // Inyección de dependencias para acceder a la base de datos
    @Autowired
    private PokemonRepository pokemonRepository;

    // Constructor vacío
    public PokemonService() {
    }

    /**
     * Obtiene todos los Pokémon almacenados en la base de datos y los convierte a DTOs.
     * @return Lista de Pokémon en formato DTO.
     */
    public List<PokemonDTO> getAllPokemon() {
        return pokemonRepository.findAll()  // Obtiene todos los Pokémon desde la base de datos
                .stream()  // Convierte la lista en un Stream para procesamiento funcional
                .map(this::convertToDTO)  // Convierte cada Pokémon a DTO
                .collect(Collectors.toList());  // Devuelve la lista convertida
    }

    /**
     * Busca un Pokémon por su nombre (ignorando mayúsculas y minúsculas).
     * @param name Nombre del Pokémon a buscar.
     * @return El Pokémon en formato DTO, o null si no se encuentra.
     */
    public PokemonDTO getPokemonByName(String name) {
        return pokemonRepository.findByNameIgnoreCase(name)  // Busca en la BD sin importar mayúsculas
                .map(this::convertToDTO)  // Convierte el resultado a DTO si se encuentra
                .orElse(null);  // Retorna null si no se encontró el Pokémon
    }

    /**
     * Busca un Pokémon por su ID.
     * @param id ID del Pokémon a buscar.
     * @return El Pokémon en formato DTO, o null si no se encuentra.
     */
    public PokemonDTO getPokemonById(long id) {
        return pokemonRepository.findById(id)  // Busca el Pokémon en la base de datos por ID
                .map(this::convertToDTO)  // Convierte a DTO si se encuentra
                .orElse(null);  // Retorna null si no se encontró
    }

    /**
     * Obtiene los Pokémon que pertenecen a una generación específica.
     * @param generationId ID de la generación.
     * @return Lista de Pokémon de esa generación en formato DTO.
     */
    public List<PokemonDTO> getPokemonByGeneration(Long generationId) {
        List<Pokemon> pokemonList = pokemonRepository.findByGenerationId(generationId);  // Busca en la BD
        return pokemonList.stream().map(this::convertToDTO).collect(Collectors.toList());  // Convierte a DTO
    }

    /**
     * Obtiene los Pokémon que pertenecen a un tipo específico.
     * @param typeName Nombre del tipo (Ej: "fuego", "agua").
     * @return Lista de Pokémon con ese tipo en formato DTO.
     */
    public List<PokemonDTO> getPokemonByType(String typeName) {
        List<Pokemon> pokemonList = pokemonRepository.findByTypes_NameIgnoreCase(typeName.trim());  // Busca en la BD
        return pokemonList.stream().map(this::convertToDTO).collect(Collectors.toList());  // Convierte a DTO
    }

    /**
     * Obtiene los Pokémon que habitan en un hábitat específico.
     * @param habitatId ID del hábitat.
     * @return Lista de Pokémon de ese hábitat en formato DTO.
     */
    public List<PokemonDTO> getPokemonByHabitat(Long habitatId) {
        List<Pokemon> pokemonList = pokemonRepository.findByHabitat_Id(habitatId);  // Busca en la BD
        return pokemonList.stream().map(this::convertToDTO).collect(Collectors.toList());  // Convierte a DTO
    }

    /**
     * Obtiene los Pokémon que pertenecen a una especie específica.
     * @param speciesId ID de la especie.
     * @return Lista de Pokémon de esa especie en formato DTO.
     */
    public List<PokemonDTO> getPokemonBySpecies(Long speciesId) {
        List<Pokemon> pokemonList = pokemonRepository.findBySpecies_Id(speciesId);  // Busca en la BD
        return pokemonList.stream().map(this::convertToDTO).collect(Collectors.toList());  // Convierte a DTO
    }

    /**
     * Convierte un objeto `Pokemon` en un `PokemonDTO` para transferir datos de forma más eficiente.
     * @param pokemon Objeto `Pokemon` de la base de datos.
     * @return Objeto `PokemonDTO` con la información del Pokémon.
     */
    private PokemonDTO convertToDTO(Pokemon pokemon) {
        PokemonDTO dto = new PokemonDTO();

        // Asigna los valores del objeto `Pokemon` al DTO
        dto.setId(pokemon.getId());
        dto.setName(pokemon.getName());
        dto.setHeight(pokemon.getHeight());
        dto.setWeight(pokemon.getWeight());
        dto.setDescription(pokemon.getDescription());
        dto.setSpriteUrl(pokemon.getSpriteUrl());

        // Estadísticas de combate
        dto.setHp(pokemon.getHp());
        dto.setAttack(pokemon.getAttack());
        dto.setDefense(pokemon.getDefense());
        dto.setSpecialAttack(pokemon.getSpecialAttack());
        dto.setSpecialDefense(pokemon.getSpecialDefense());
        dto.setSpeed(pokemon.getSpeed());
        dto.setTotal(pokemon.getTotal());

        // Información adicional sobre el Pokémon
        dto.setGeneration(pokemon.getGeneration() != null ? pokemon.getGeneration().getName() : "Desconocida");
        dto.setHabitat(pokemon.getHabitat() != null ? pokemon.getHabitat().getName() : "Desconocido");
        dto.setSpecies(pokemon.getSpecies() != null ? pokemon.getSpecies().getName() : "Desconocida");

        // Obtiene los tipos del Pokémon como una lista de Strings
        dto.setTypes(
                pokemon.getTypes()
                        .stream()
                        .map(type -> type.getName())  // Extrae el nombre del tipo
                        .collect(Collectors.toSet())  // Lo convierte en un conjunto (evita duplicados)
        );

        // Obtiene las URLs de los iconos de los tipos del Pokémon
        dto.setTypeSpriteUrls(
                pokemon.getTypes()
                        .stream()
                        .map(t -> t.getSpriteUrl())  // Extrae la URL del icono del tipo
                        .collect(Collectors.toSet())  // Lo convierte en un conjunto
        );

        // Obtiene las habilidades del Pokémon como una lista de Strings
        dto.setAbilities(
                pokemon.getAbilities()
                        .stream()
                        .map(ability -> ability.getName())  // Extrae el nombre de la habilidad
                        .collect(Collectors.toSet())  // Lo convierte en un conjunto
        );

        return dto;
    }
}
