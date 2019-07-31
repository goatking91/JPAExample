package io.lfin.controller;

import io.lfin.dto.PostsDto;
import io.lfin.dto.UsersDto;
import io.lfin.model.Users;
import io.lfin.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 유저에 대한 Controller
 */
@Slf4j
@RequestMapping("/users")
@RestController
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    /**
     * 등록된 유저들을 불러오는 API
     * @return
     */
    @GetMapping("")
    public List<UsersDto.User> getUsersList() {

        log.info("[Get Users List Start]");

        List<UsersDto.User> userList = usersRepository.findAll().stream().map(users -> {
            UsersDto.User user = new UsersDto.User();
            user.setAge(users.getAge());
            user.setName(users.getName());
            return user;
        }).collect(Collectors.toList());

        log.info("[Get Users List End]");

        return userList;
    }

    /**
     * 특정 유저를 불러오는 API
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public UsersDto.User getUsers(@PathVariable Long userId) {

        log.info("[Get Users Start]");
        log.info("[userId info] {}", userId);

        Users users = null;
        UsersDto.User user = new UsersDto.User();

        try {
            users = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
            user.setAge(users.getAge());
            user.setName(users.getName());
        } catch (EntityNotFoundException enfe) {
            log.error("[Get Users Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Get Users End]");
        return user;
    }

    /**
     * 유저를 등록하는 API
     * @param user
     */
    @PostMapping("")
    public void addUsers(@Valid @RequestBody UsersDto.User user) {

        log.info("[Add Users Start]");
        log.info("[user info] {}", user.toString());

        Users users = new Users();
        users.setAge(user.getAge());
        users.setName(user.getName());
        usersRepository.save(users);

        log.info("[Add Users End]");

    }

    /**
     * 유저 정보를 변경하는 API
     * @param userId
     * @param user
     */
    @Transactional
    @PutMapping("/{userId}")
    public void updateUsers(@PathVariable Long userId,
                            @Valid @RequestBody UsersDto.User user) {

        log.info("[Update Users Start]");
        log.info("[userId info] {}", userId);
        log.info("[user info] {}", user.toString());

        Users users = null;
        try {
            users = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
            users.setName(user.getName());
            users.setAge(user.getAge());
        } catch (EntityNotFoundException enfe) {
            log.error("[Update Users Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Update Users End]");

    }

    /**
     * 유저를 삭제하는 API
     * @param userId
     */
    @DeleteMapping("/{userId}")
    public void deleteUsers(@PathVariable Long userId) {

        log.info("[Delete Users Start]");
        log.info("[userId info] {}", userId);

        usersRepository.deleteById(userId);

        log.info("[Delete Users End]");
    }

    /**
     * 유저가 작성한 글을 보는 API
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/posts")
    public List<PostsDto.Post> getWritePostList(@PathVariable Long userId) {

        log.info("[Get Users Write Post Start]");
        log.info("[userId info] {}", userId);

        List<PostsDto.Post> postList = new ArrayList<>();

        try {
            Users users = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());

            // 유저의 연관된 글의 리스트를 불러온다.
            postList = users.getPostsList().stream().map(posts -> {
                PostsDto.Post post = new PostsDto.Post();
                post.setTitle(posts.getTitle());
                post.setContent(posts.getContent());
                post.setUserId(users.getId());
                post.setUserName(users.getName());
                return post;
            }).collect(Collectors.toList());

        } catch (EntityNotFoundException enfe) {
            log.error("[Get Users Write Users Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Get Users Write Post End]");

        return postList;
    }
}
