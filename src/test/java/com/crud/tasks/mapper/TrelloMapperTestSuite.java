package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    private static TrelloCard trelloCard;
    private static TrelloCardDto trelloCardDto;
    private static TrelloList trelloList;
    private static TrelloListDto trelloListDto;
    private static TrelloBoard trelloBoard;
    private static TrelloBoardDto trelloBoardDto;

    @BeforeClass
    public static void setUp() {
        //Given
        trelloCard = new TrelloCard("test_name", "test_description", "test_pos", "test_listId");
        trelloCardDto = new TrelloCardDto("test_name_dto", "test_description_dto", "test_pos_dto",
                "test_listId_dto");
        trelloList = new TrelloList("test_id", "test_name", true);
        trelloListDto = new TrelloListDto("test_id_dto", "test_name_dto", true);
        trelloBoard = new TrelloBoard("test_id", "test_name",
                new ArrayList<>(Arrays.asList(trelloList)));
        trelloBoardDto = new TrelloBoardDto("test_name_dto", "test_id_dto",
                new ArrayList<>(Arrays.asList(trelloListDto)));
    }

    @Test
    public void testMapToBoard() {
        //When
        List<TrelloBoard> mappingResultTrelloBoard = trelloMapper.mapToBoard(Arrays.asList(trelloBoardDto));
        //Then
        assertNotNull(mappingResultTrelloBoard);
        assertEquals(1, mappingResultTrelloBoard.size());
        assertEquals("test_id_dto", mappingResultTrelloBoard.get(0).getId());
        assertEquals("test_name_dto", mappingResultTrelloBoard.get(0).getName());
        assertEquals(1, mappingResultTrelloBoard.get(0).getLists().size());
        assertEquals(TrelloList.class, mappingResultTrelloBoard.get(0).getLists().get(0).getClass());
    }

    @Test
    public void testMapToBoardDto() {
        //When
        List<TrelloBoardDto> mappingResultTrelloBoardDto = trelloMapper.mapToBoardDto(Arrays.asList(trelloBoard));
        //Then
        assertNotNull(mappingResultTrelloBoardDto);
        assertEquals(1, mappingResultTrelloBoardDto.size());
        assertEquals("test_id", mappingResultTrelloBoardDto.get(0).getId());
        assertEquals("test_name", mappingResultTrelloBoardDto.get(0).getName());
        assertEquals(1, mappingResultTrelloBoardDto.get(0).getLists().size());
        assertEquals(TrelloListDto.class, mappingResultTrelloBoardDto.get(0).getLists().get(0).getClass());
    }

    @Test
    public void testMapToList() {
        //When
        List<TrelloList> mappingResultList = trelloMapper.mapToList(Arrays.asList(trelloListDto));
        //Then
        assertNotNull(mappingResultList);
        assertEquals(1, mappingResultList.size());
        assertEquals("test_id_dto", mappingResultList.get(0).getId());
        assertEquals("test_name_dto", mappingResultList.get(0).getName());
        assertTrue(mappingResultList.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //When
        List<TrelloListDto> mappingResultListDto = trelloMapper.mapToListDto(Arrays.asList(trelloList));
        //Then
        assertNotNull(mappingResultListDto);
        assertEquals(1, mappingResultListDto.size());
        assertEquals("test_id", mappingResultListDto.get(0).getId());
        assertEquals("test_name", mappingResultListDto.get(0).getName());
        assertTrue(mappingResultListDto.get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        //When
        TrelloCard mappingResultCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertNotNull(mappingResultCard);
        assertEquals("test_name_dto", mappingResultCard.getName());
        assertEquals("test_description_dto", mappingResultCard.getDescription());
        assertEquals("test_pos_dto", mappingResultCard.getPos());
        assertEquals("test_listId_dto", mappingResultCard.getListId());
        assertEquals(TrelloCard.class, mappingResultCard.getClass());
    }

    @Test
    public void testMapToCardDto() {
        //When
        TrelloCardDto mappingResultCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertNotNull(mappingResultCardDto);
        assertEquals("test_name", mappingResultCardDto.getName());
        assertEquals("test_description", mappingResultCardDto.getDescription());
        assertEquals("test_pos", mappingResultCardDto.getPos());
        assertEquals("test_listId", mappingResultCardDto.getListId());
        assertEquals(TrelloCardDto.class, mappingResultCardDto.getClass());
    }
}