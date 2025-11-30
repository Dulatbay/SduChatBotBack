package kz.sdu.chat.mainservice.services.impl;

import kz.sdu.chat.mainservice.entities.User;
import kz.sdu.chat.mainservice.entities.UserTokens;
import kz.sdu.chat.mainservice.repositories.UserTokensRepository;
import kz.sdu.chat.mainservice.services.MessageTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageTokenServiceImpl implements MessageTokenService {

    private final UserTokensRepository userTokensRepository;

    @Override
    public boolean CheckMessageToken(User user){
        UserTokens usertokens = userTokensRepository.findAllByUserId(user.getId());
        CheckForDateUserToken(user);
        if(usertokens == null) {
            CreateUserToken(user);
        }
        if(usertokens.getSpentCost() > 2){
            return false;
        }
        return true;
    }


    @Override
    public void AddtokenToUser(User user) {
        UserTokens usertokens = userTokensRepository.findAllByUserId(user.getId());

        if(usertokens == null){
            CreateUserToken(user);
        }

        usertokens.setSpentCost(usertokens.getSpentCost() + 1);
        userTokensRepository.save(usertokens);
    }

    @Override
    public void CreateUserToken(User user) {
        UserTokens newUsertokens = new UserTokens();
        newUsertokens.setUserId(user.getId());
        newUsertokens.setSpentCost(Long.valueOf(0));
        newUsertokens.setUpdateDate(LocalDateTime.now());
        userTokensRepository.save(newUsertokens);
    }

    @Override
    public void CheckForDateUserToken(User user) {
        UserTokens usertokens = userTokensRepository.findAllByUserId(user.getId());

        if(usertokens == null){
            CreateUserToken(user);
        }

        Duration duration = Duration.between(usertokens.getUpdateDate(), LocalDateTime.now());
        log.info("Duration in days: {}", duration.toDays());
        if(duration.toDays() >= 30){
            usertokens.setSpentCost(Long.valueOf(0));
            userTokensRepository.save(usertokens);
        }
    }

}
