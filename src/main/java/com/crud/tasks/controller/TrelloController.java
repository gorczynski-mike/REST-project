package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin("*")
public class TrelloController {

    @Autowired
    private TrelloService trelloService;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloService.fetchTrelloBoards();
    }

    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard (@RequestBody TrelloCardDto trelloCardDto) {
        return trelloService.createTrelloCard(trelloCardDto);
    }
}
