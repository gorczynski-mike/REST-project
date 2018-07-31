package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void beforeEveryTest() {
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com/");
        Mockito.when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloAppUsername()).thenReturn("michalgorczynski");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", new ArrayList<>());

        URI uri = new URI("http://test.com/members/michalgorczynski/boards?key=test&token=test&fields=name,id&lists=all");

        Mockito.when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedBoards.size());
        assertEquals("test_id", fetchedBoards.get(0).getId());
        assertEquals("test_board", fetchedBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedBoards.get(0).getLists());
    }

    @Test
    public void testShouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test name", "test description", "top", "test id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=test%20name&desc=test%20description&pos=top&idList=test%20id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "test name",
                "http://test.com",
                null
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("test name", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        URI url = new URI("http://test.com/members/michalgorczynski/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> resultList = trelloClient.getTrelloBoards();

        //Then
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
        assertEquals(new ArrayList<>(), resultList);
    }

}