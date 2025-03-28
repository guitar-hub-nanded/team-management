package com.team.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<PlayerEntity> getPlayers(){
        return playerRepository.findAll();
    }

    public PlayerEntity getById(Long id) {
        return playerRepository.findById(id).get();
    }

    public PlayerEntity create(PlayerEntity player) {
        return playerRepository.save(player);
    }
}
