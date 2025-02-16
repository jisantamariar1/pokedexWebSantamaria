package ec.edu.uce.pokedexRS.controller;

// Importaciones necesarias
import ec.edu.uce.pokedexRS.dto.PokemonDTO;
import ec.edu.uce.pokedexRS.model.Generation;
import ec.edu.uce.pokedexRS.model.Habitat;
import ec.edu.uce.pokedexRS.model.Species; // Importación de Species
import ec.edu.uce.pokedexRS.model.Type;
import ec.edu.uce.pokedexRS.repository.GenerationRepository;
import ec.edu.uce.pokedexRS.repository.HabitatRepository;
import ec.edu.uce.pokedexRS.repository.SpeciesRepository; // Importación de SpeciesRepository
import ec.edu.uce.pokedexRS.repository.TypeRepository;
import ec.edu.uce.pokedexRS.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para manejar las peticiones relacionadas con los Pokémon.
 * Define rutas para mostrar la Pokédex y los detalles de un Pokémon.
 */
@Controller
public class PokemonController {

    // Inyección de dependencias con @Autowired para acceder a los servicios y repositorios
    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private GenerationRepository generationRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private HabitatRepository habitatRepository;

    @Autowired
    private SpeciesRepository speciesRepository; // Inyección del repositorio de Species

    // Constructor vacío requerido por Spring
    public PokemonController() {}

    /**
     * Método para manejar la ruta principal ("/").
     * Permite filtrar Pokémon por nombre, generación, tipo, hábitat o especie.
     * @param search Nombre o ID del Pokémon buscado (opcional).
     * @param generationId ID de la generación para filtrar (opcional).
     * @param type Nombre del tipo de Pokémon para filtrar (opcional).
     * @param habitatId ID del hábitat para filtrar (opcional).
     * @param speciesId ID de la especie para filtrar (opcional).
     * @param model Modelo para enviar datos a la vista.
     * @return La vista "index" con los Pokémon filtrados.
     */
    @GetMapping("/")
    public String showPokedex(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "generation", required = false) Long generationId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "habitat", required = false) Long habitatId,
            @RequestParam(value = "species", required = false) Long speciesId, // Parámetro para filtrar por especie
            Model model) {

        List<PokemonDTO> pokemonList;

        // Si se proporciona un término de búsqueda
        if (search != null && !search.isEmpty()) {
            try {
                // Intenta convertir la búsqueda en un ID numérico
                int id = Integer.parseInt(search);
                PokemonDTO pokemon = pokemonService.getPokemonById(id);
                pokemonList = (pokemon != null) ? List.of(pokemon) : List.of();
            } catch (NumberFormatException e) {
                // Si no es un número, busca por nombre
                PokemonDTO pokemon = pokemonService.getPokemonByName(search);
                pokemonList = (pokemon != null) ? List.of(pokemon) : List.of();
            }
        } else if (generationId != null) {
            // Filtra por generación si se proporciona el ID de la generación
            pokemonList = pokemonService.getPokemonByGeneration(generationId);
        } else if (type != null && !type.isEmpty()) {
            // Filtra por tipo si se proporciona el nombre del tipo
            pokemonList = pokemonService.getPokemonByType(type);
        } else if (habitatId != null) {
            // Filtra por hábitat si se proporciona el ID del hábitat
            pokemonList = pokemonService.getPokemonByHabitat(habitatId);
        } else if (speciesId != null) {
            // Filtra por especie si se proporciona el ID de la especie
            pokemonList = pokemonService.getPokemonBySpecies(speciesId);
        } else {
            // Si no hay filtros, obtiene todos los Pokémon
            pokemonList = pokemonService.getAllPokemon();
        }

        // Obtiene todas las generaciones, tipos, hábitats y especies para mostrarlos en la interfaz
        List<Generation> generations = generationRepository.findAll();
        List<String> types = typeRepository.findAll()
                .stream()
                .map(Type::getName)
                .collect(Collectors.toList());
        List<Habitat> habitats = habitatRepository.findAll();
        List<Species> species = speciesRepository.findAll(); // Obtiene todas las especies

        // Agrega los datos al modelo para enviarlos a la vista
        model.addAttribute("pokemonList", pokemonList);
        model.addAttribute("search", search);
        model.addAttribute("generations", generations);
        model.addAttribute("types", types);
        model.addAttribute("habitats", habitats);
        model.addAttribute("species", species); // Agrega especies al modelo
        model.addAttribute("selectedGeneration", generationId);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedHabitat", habitatId);
        model.addAttribute("selectedSpecies", speciesId); // Agrega la especie seleccionada al modelo

        return "index"; // Devuelve la vista "index.html"
    }

    /**
     * Método para manejar la ruta "/pokemon".
     * Muestra los detalles de un Pokémon específico según su nombre.
     * @param name Nombre del Pokémon.
     * @param model Modelo para enviar datos a la vista.
     * @return La vista "pokemon.html" con los detalles del Pokémon.
     */
    @GetMapping("/pokemon")
    public String showPokemon(@RequestParam("name") String name, Model model) {
        // Busca el Pokémon por su nombre
        PokemonDTO pokemon = pokemonService.getPokemonByName(name);

        // Agrega el Pokémon al modelo para enviarlo a la vista
        model.addAttribute("pokemon", pokemon);

        return "pokemon"; // Devuelve la vista "pokemon.html"
    }
}
