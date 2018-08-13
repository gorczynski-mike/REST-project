package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.hamcrest.Matchers;
import org.hibernate.validator.constraints.Email;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;

    @Test
    public void shouldSendEmailWhenNewCardCreated() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_name", "test_description", "test_pos", "test_listId");

        Mockito.when(trelloClient.createNewCard(trelloCardDto)).thenReturn(new CreatedTrelloCardDto("test_id", "test_name", "test_url"));

        //When
        trelloService.createTrelloCard(trelloCardDto);

        //Then
        Mockito.verify(simpleEmailService, Mockito.times(1)).send(Mockito.any());
    }

    @Test
    public void shouldNotSendEmailWhenNewCardNotCreated() {
        //Given
        Mockito.when(trelloClient.createNewCard(Mockito.any(TrelloCardDto.class))).thenReturn(null);

        //When
        trelloService.createTrelloCard(new TrelloCardDto("test", "test", "test", "test"));

        //Then
        Mockito.verify(simpleEmailService, Mockito.times(0)).send(Mockito.any());
    }

}
