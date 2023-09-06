package uz.pdp.currencyconvertor.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.currencyconvertor.model.User;
import uz.pdp.currencyconvertor.model.UserState;
import uz.pdp.currencyconvertor.service.UserService;


public class CurrencyConvertorBot extends TelegramLongPollingBot {

    BotService botService = new BotService();
    UserService userService = new UserService();

    @Override
    public String getBotUsername() {
        return "g23_first_bot";
    }

    @Override
    public String getBotToken() {
        return "6382556658:AAFGMNpkZyyj5Xvz1kfOwe6xXcWkU0GhIiw";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        User currentUser = userService.getByChatId(chatId);
        UserState userState = UserState.START;

        if (currentUser != null) {
            userState = currentUser.getState();
            switch (userState) {
                case REGISTERED -> {
                    switch (text) {
                        case "EUR -> UZS" -> {
                            userService.updateState(chatId, UserState.EUR_UZS);
                            userState = UserState.EUR_UZS;
                        }
                        case "USD -> UZS" -> {
                            userService.updateState(chatId, UserState.USD_UZS);
                            userState = UserState.USD_UZS;
                        }
                        case "UZS -> USD" -> {
                            userService.updateState(chatId, UserState.UZS_USD);
                            userState = UserState.UZS_USD;
                        }
                        case "UZS -> EUR" -> {
                            userService.updateState(chatId, UserState.UZS_EUR);
                            userState = UserState.UZS_EUR;
                        }
                    }
                }
                case EUR_UZS, USD_UZS, UZS_USD, UZS_EUR -> {
                    try {
                        Double amount = Double.valueOf(text);
                        execute(botService.convertCurrency(chatId.toString(), userState, amount));
                        userService.updateState(chatId, UserState.MENU);

                    } catch (NumberFormatException e) {
                        execute(new SendMessage(chatId.toString(), "please enter valid number"));
                    }
                    return;
                }
            }
        }
        else if (message.hasContact()) {
                Contact contact = message.getContact();
                User user = User.builder()
                        .chatId(chatId)
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .phoneNumber(contact.getPhoneNumber())
                        .state(UserState.REGISTERED)
                        .build();
                userService.add(user);
                userState = UserState.REGISTERED;
            }
            switch (userState) {
                case START -> {
                    execute(botService.register(chatId.toString()));
                }
                case REGISTERED -> {
                    execute(botService.menu(chatId.toString()));
                }
                case EUR_UZS, USD_UZS, UZS_EUR, UZS_USD -> {
                    execute(botService.enterAmount(chatId.toString()));
                }

            }
        }
    }
