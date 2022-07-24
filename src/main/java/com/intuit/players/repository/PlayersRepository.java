package com.intuit.players.repository;

import com.intuit.players.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Component
public interface PlayersRepository extends CrudRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findAll();

    Optional<PlayerEntity> findById(Integer id);
}
