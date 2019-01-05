package com.labs.starwars.infrastructure.repositories;

import com.labs.starwars.domain.models.internal.entity.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author - marcelo.fontes
 * @since - 1/5/19
 **/
public interface PlanetRepository extends MongoRepository<Planet, String> {
    List<Planet> findByName(String name);
}
