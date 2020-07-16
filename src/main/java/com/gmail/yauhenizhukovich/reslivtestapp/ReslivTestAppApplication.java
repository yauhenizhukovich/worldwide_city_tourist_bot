package com.gmail.yauhenizhukovich.reslivtestapp;

import com.gmail.yauhenizhukovich.reslivtestapp.service.impl.CityBotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@SpringBootApplication(scanBasePackages = "com.gmail.yauhenizhukovich.reslivtestapp")
public class ReslivTestAppApplication {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CityBotService cityBot;

    public ReslivTestAppApplication(CityBotService cityBot) {
        this.cityBot = cityBot;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ReslivTestAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(cityBot);
        } catch (TelegramApiException e) {
            logger.info("There is something wrong with registering bot: " + cityBot.getBotUsername());
            logger.info(e.getMessage());
        }
    }

}
