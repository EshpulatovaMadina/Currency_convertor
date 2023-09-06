package uz.pdp.currencyconvertor.service;

import uz.pdp.currencyconvertor.model.User;
import uz.pdp.currencyconvertor.model.UserState;

import java.util.ArrayList;
import java.util.Objects;

public class UserService {
    ArrayList<User> users = new ArrayList<>();


    public User getByChatId(Long chatId) {
        for (User user : users) {
            if(Objects.equals(user.getChatId(), chatId)) {
                return user;
            }
        }
        return null;
    }

    public void add(User user) {
        users.add(user);
    }

    public void updateState(Long chatId, UserState userState) {
        for (User user : users) {
            if(Objects.equals(user.getChatId(), chatId)) {
                user.setState(userState);
                return;
            }
        }
    }
}
