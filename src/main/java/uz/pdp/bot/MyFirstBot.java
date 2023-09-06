package uz.pdp.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFirstBot extends TelegramLongPollingBot {
    Logger logger = Logger.getLogger("Tg Logger");

    @Override
    public String getBotUsername() {
        return "g23_first_bot";
    }

    @Override
    public String getBotToken() {
        return "5508251089:AAHcjR3FGGk1xTip_nEEyDVAcQBLrGvRl7k";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage(chatId.toString(), "Buttons");
        sendMessage.setReplyMarkup(replyKeyboardMarkup());
        execute(sendMessage);
    }

    public ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();

        keyboardRow.add("button 1");
        keyboardRow.add("button 2");
        keyboardRow.add(1, "button 3");

        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();

        keyboardRow.add("row 2 B1");
        keyboardRow.add("row 2 B2");
        keyboardRow.add("row 2 B3");

        rows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    //    @SneakyThrows
//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        String text = message.getText();
//        Long chatId = message.getChatId();
//        System.out.println(chatId);
//
//        Chat chat = message.getChat();
//        logger.log(Level.INFO, chat.getFirstName() + " entered, message: " + text);
//
//        System.out.println(chat.getBio());
//        System.out.println(chat.getFirstName());
//        System.out.println(chat.getLastName());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(text);
//        sendMessage.setChatId(chatId.toString());
//        execute(sendMessage);
//        logger.log(Level.INFO, "response send to " + chat.getFirstName());
//    }
}
