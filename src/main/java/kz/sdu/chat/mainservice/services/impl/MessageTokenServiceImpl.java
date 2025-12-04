package kz.sdu.chat.mainservice.services.impl;

import kz.sdu.chat.mainservice.entities.User;
import kz.sdu.chat.mainservice.entities.UserTokens;
import kz.sdu.chat.mainservice.repositories.UserTokensRepository;
import kz.sdu.chat.mainservice.services.MessageTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static kz.sdu.chat.mainservice.constants.ValueConstants.ChatTokenLimit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageTokenServiceImpl implements MessageTokenService {

    private final UserTokensRepository userTokensRepository;

    @Override
    public boolean CheckMessageToken(User user){
        UserTokens usertokens = userTokensRepository.findAllByUserId(user.getId());
        if(usertokens == null) {
            CreateUserToken(user);
        }
        if(usertokens.getSpentCost() > ChatTokenLimit){
            return false;
        }
        return true;
    }


    @Override
    public void AddtokenToUser(User user, Double cost) {
        UserTokens usertokens = userTokensRepository.findAllByUserId(user.getId());

        if(usertokens == null){
            CreateUserToken(user);
        }

        usertokens.setSpentCost(usertokens.getSpentCost() + cost);
        userTokensRepository.save(usertokens);
    }

    @Override
    public void CreateUserToken(User user) {
        UserTokens newUsertokens = new UserTokens();
        newUsertokens.setUserId(user.getId());
        newUsertokens.setSpentCost(0.0);
        newUsertokens.setUpdateDate(LocalDateTime.now());
        userTokensRepository.save(newUsertokens);
    }

    @Scheduled(fixedRate = 86_400_000)
    @Override
    public void CheckForDateUserToken() {
        List<UserTokens> all = userTokensRepository.findAll();

        for (UserTokens token : all) {

            Duration duration =
                    Duration.between(token.getUpdateDate(), LocalDateTime.now());

            log.info("Duration in days for user {}: {}", token.getUserId(), duration.toDays());

            if(duration.toMinutes() >= 1){
                token.setSpentCost(0.0);
                token.setUpdateDate(LocalDateTime.now());
                userTokensRepository.save(token);
            }
        }
    }


}
