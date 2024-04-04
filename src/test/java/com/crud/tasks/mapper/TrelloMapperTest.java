package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrelloMapperTest {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void trelloCardMapperTest() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Karta 1", "Opis 1", "Pozycja górna", "Lista 1");
        TrelloCard trelloCard = new TrelloCard("Karta 2", "Opis 2", "Pozycja górna", "Lista 2");
        //when
        TrelloCard fromDto = trelloMapper.mapToCard(trelloCardDto);
        TrelloCardDto fromDomain = trelloMapper.mapToCardDto(trelloCard);
        //then
        Assertions.assertEquals("Karta 1", fromDto.getName());
        Assertions.assertEquals("Opis 1", fromDto.getDescription());
        Assertions.assertEquals("Pozycja górna", fromDto.getPos());
        Assertions.assertEquals("Lista 1", fromDto.getListId());
        Assertions.assertEquals("Karta 2", fromDomain.getName());
        Assertions.assertEquals("Opis 2", fromDomain.getDescription());
        Assertions.assertEquals("Pozycja górna", fromDomain.getPos());
        Assertions.assertEquals("Lista 2", fromDomain.getListId());
    }

    @Test
    void trelloListMapperTest() {
        //given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("Lista 1", "Nazwa 1", false));
        trelloListDtos.add(new TrelloListDto("Lista 2", "Nazwa 2", false));
        trelloListDtos.add(new TrelloListDto("Lista 3", "Nazwa 3", true));
        trelloListDtos.add(new TrelloListDto("Lista 4", "Nazwa 4", false));

        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("Lista 5", "Nazwa 5", false));
        trelloLists.add(new TrelloList("Lista 6", "Nazwa 6", false));
        trelloLists.add(new TrelloList("Lista 7", "Nazwa 7", true));
        trelloLists.add(new TrelloList("Lista 8", "Nazwa 8", false));
        //when
        List<TrelloList> fromDto = trelloMapper.mapToList(trelloListDtos);
        List<TrelloListDto> fromDomain = trelloMapper.mapToListDto(trelloLists);
        //then
        Assertions.assertEquals(4, fromDto.size());
        Assertions.assertEquals(4, fromDomain.size());
        Assertions.assertEquals("Lista 6", fromDomain.get(1).getId());
        Assertions.assertEquals("Nazwa 3", fromDto.get(2).getName());

    }

    @Test
    void trelloBoardMapperTest() {
        //given
        TrelloListDto trelloListDto1 = new TrelloListDto("Lista 1", "Nazwa 1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("Lista 2", "Nazwa 2", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("Lista 3", "Nazwa 3", true);
        TrelloListDto trelloListDto4 = new TrelloListDto("Lista 4", "Nazwa 4", false);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("Tablica 1", "Nazwa tablicy 1", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("Tablica 2", "Nazwa tablicy 2", new ArrayList<>());
        trelloBoardDto1.getLists().add(trelloListDto1);
        trelloBoardDto1.getLists().add(trelloListDto2);
        trelloBoardDto2.getLists().add(trelloListDto3);
        trelloBoardDto2.getLists().add(trelloListDto4);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);

        TrelloList trelloList1 = new TrelloList("Lista 5", "Nazwa 5", false);
        TrelloList trelloList2 = new TrelloList("Lista 6", "Nazwa 6", false);
        TrelloList trelloList3 = new TrelloList("Lista 7", "Nazwa 7", true);
        TrelloList trelloList4 = new TrelloList("Lista 8", "Nazwa 8", false);
        TrelloBoard trelloBoard1 = new TrelloBoard("Tablica 3", "Nazwa tablicy 3", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("Tablica 4", "Nazwa tablicy 4", new ArrayList<>());
        trelloBoard1.getLists().add(trelloList1);
        trelloBoard1.getLists().add(trelloList2);
        trelloBoard2.getLists().add(trelloList3);
        trelloBoard2.getLists().add(trelloList4);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        //when
        List<TrelloBoard> fromDto = trelloMapper.mapToBoards(trelloBoardDtos);
        List<TrelloBoardDto> fromDomain = trelloMapper.mapToBoardsDto(trelloBoards);
        //then
        Assertions.assertEquals(2,fromDto.size());
        Assertions.assertEquals(2,fromDomain.size());
        Assertions.assertEquals("Nazwa 6",fromDomain.get(0).getLists().get(1).getName());
        Assertions.assertTrue(fromDto.get(1).getLists().get(0).isClosed());

    }

}
