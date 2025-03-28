package com.team.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/get-players")
    public String getPlayers(Model model){
         model.addAttribute("players",playerService.getPlayers());
        return "Players";
    }

    @GetMapping("/add")
    public String createPlayerForm(Model model){
        PlayerEntity player = new PlayerEntity();
        model.addAttribute("player", player);
        return "AddPlayer";
    }

    @GetMapping("/edit/{id}")
    public String editPlayerForm(@PathVariable("id") Long id, Model model){
        PlayerEntity player = playerService.getById(id);
        model.addAttribute("player", player);
        return "EditPlayer";
    }

    @PostMapping("/create")
    public String createPlayer(@ModelAttribute("task") PlayerEntity player ){
        player.setCreatedOn(LocalDateTime.now());
        PlayerEntity taskEntity = playerService.create(player);
        return "redirect:/player/get-players";
    }

    @PostMapping("/update/{id}")
    public String createPlayer(@PathVariable("id") Long id, @ModelAttribute("player") PlayerEntity player ){
        PlayerEntity existingPlayer = playerService.getById(id);
        existingPlayer.setFirstName(player.getFirstName());
        existingPlayer.setLastName(player.getLastName());
        existingPlayer.setEmail(player.getEmail());
        existingPlayer.setRank(player.getRank());
        existingPlayer.setType(player.getType());
        existingPlayer.setUpdatedOn(LocalDateTime.now());
        PlayerEntity playerEntity = playerService.create(existingPlayer);
        return "redirect:/player/get-players";
    }


}
