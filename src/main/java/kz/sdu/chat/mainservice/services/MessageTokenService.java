package kz.sdu.chat.mainservice.services;

import kz.sdu.chat.mainservice.entities.User;

public interface MessageTokenService {
    boolean CheckMessageToken(User user);

    void AddtokenToUser(User user);

    void CreateUserToken(User user);
    void CheckForDateUserToken(User user);

}
