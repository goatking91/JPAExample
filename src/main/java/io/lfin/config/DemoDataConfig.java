package io.lfin.config;

import io.lfin.model.Users;
import io.lfin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Application 구동 시 Users 더미 데이터 생성 Component
 */
@Component
public class DemoDataConfig implements ApplicationRunner {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Users users1 = new Users();
        users1.setAge(29L);
        users1.setName("염승민");

        Users users2 = new Users();
        users2.setAge(18L);
        users2.setName("홍길동");

        List<Users> usersList = new ArrayList<>();

        usersList.add(users1);
        usersList.add(users2);

        usersRepository.saveAll(usersList);
    }
}
