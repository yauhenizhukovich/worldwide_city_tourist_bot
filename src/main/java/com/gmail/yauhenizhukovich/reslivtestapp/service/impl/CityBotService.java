package com.gmail.yauhenizhukovich.reslivtestapp.service.impl;

import com.gmail.yauhenizhukovich.reslivtestapp.service.CityService;
import com.gmail.yauhenizhukovich.reslivtestapp.service.exception.CityExistenceException;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.CityDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.lang.invoke.MethodHandles;

@Service
public class CityBotService extends TelegramLongPollingBot {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String WELCOME_MESSAGE =
            "Hi, dear user! You can view information about the city of interest by entering its name.";
    private static final String BOT_USERNAME = "worldwide_city_tourist_bot";
    private static final String BOT_TOKEN = "1342554794:AAEkCRptkRpqqtU5fTMfT4t8hBcXZaHth-k";
    private static final String NONEXISTENT_CITY_MESSAGE = "Sorry, but we have no information about this city:(";
    private final CityService cityService;

    public CityBotService(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();
        if (messageText.equals("/start")) {
            sendMessage(message, WELCOME_MESSAGE);
        } else {
            try {
                CityDTO city = cityService.getCityByName(messageText);
                if (city.getInfo() == null) {
                    sendMessage(message, NONEXISTENT_CITY_MESSAGE);
                } else {
                    String info = city.getInfo();
                    sendMessage(message, info);
                }
            } catch (CityExistenceException e) {
                sendMessage(message, NONEXISTENT_CITY_MESSAGE);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void sendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            logger.info("There is something wrong with responding to char with ID: " + sendMessage.getChatId());
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
