package kz.sdu.chat.mainservice.services;

import kz.sdu.chat.mainservice.entities.User;
import org.springframework.scheduling.annotation.Scheduled;

public interface MessageTokenService {
    boolean CheckMessageToken(User user);

    void AddtokenToUser(User user, Double cost);

    void CreateUserToken(User user);

    void CheckForDateUserToken();
}
