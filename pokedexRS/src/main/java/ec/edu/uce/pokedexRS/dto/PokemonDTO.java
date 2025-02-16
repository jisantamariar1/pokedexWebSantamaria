package ec.edu.uce.pokedexRS.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PokemonDTO {
    //Un DTO (Data Transfer Object) se usa para enviar datos desde el backend a la vista
    // sin exponer directamente la entidad del modelo.
    private Long id;
    private String name;
    private Double height;
    private Double weight;
    private String description;
    private String spriteUrl;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int total;
    private String generation;
    private String habitat;
    private String species;
    private Set<String> types;
    private Set<String> typeSpriteUrls;
    private Set<String> abilities;

}