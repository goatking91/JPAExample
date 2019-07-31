package io.lfin.controller;

import io.lfin.dto.PostsDto;
import io.lfin.dto.UsersDto;
import io.lfin.model.Posts;
import io.lfin.model.Users;
import io.lfin.repository.PostsRepository;
import io.lfin.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 글에 대한 Controller
 */
@Slf4j
@RequestMapping("/posts")
@RestController
public class PostsController {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UsersRepository usersRepository;

    /**
     * 등록된 글 리스트 전체를 불러오는 API
     * @return
     */
    @GetMapping("")
    public List<PostsDto.Post> getPostsList() {

        log.info("[Get Posts List Start]");

        List<PostsDto.Post> postList = postsRepository.findAll().stream().map(posts -> {
            PostsDto.Post post = new PostsDto.Post();
            post.setTitle(posts.getTitle());
            post.setContent(posts.getContent());
            post.setUserId(posts.getUserId());
            post.setUserName(posts.getUsers().getName());
            return post;
        }).collect(Collectors.toList());

        log.info("[Get Posts List End]");

        return postList;
    }

    /**
     * 하나의 글을 불러오는 API
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public PostsDto.Post getPosts(@PathVariable Long postId) {

        log.info("[Get Posts Start]");
        log.info("[postId info] {}", postId);

        Posts posts = null;

        PostsDto.Post post = new PostsDto.Post();

        try {
            posts = postsRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException());

            post.setTitle(posts.getTitle());
            post.setContent(posts.getContent());
            post.setUserId(posts.getUserId());
            post.setUserName(posts.getUsers().getName());

        } catch (EntityNotFoundException enfe) {
            log.error("[Get Posts Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Get Posts End]");

        return post;
    }

    /**
     * 글을 등록하는 API
     * @param post
     */
    @Transactional
    @PostMapping("")
    public void addPosts(@Valid @RequestBody PostsDto.Post post) {

        log.info("[Add Posts Start]");
        log.info("[post info] {}", post.toString());

        Users users = null;

        try {
            // 유저를 불러온 후 유저의 글 목록에 추가하는 방식
            // JPA의 자동 변경 감지로 저장 할 수 있다.
            users = usersRepository.findById(post.getUserId()).orElseThrow(() -> new EntityNotFoundException());
            Posts posts = new Posts();
            posts.setTitle(post.getTitle());
            posts.setContent(post.getContent());

            // 불러온 유저에 글 리스트에 추가한다.
            users.addPosts(posts);
        } catch (EntityNotFoundException enfe) {
            log.error("[Add Posts Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Add Posts End]");
    }

    /**
     * 글을 등록하는 API(다른 방법)
     * @param post
     */
    @PostMapping("another")
    public void addAnotherWayPosts(@Valid @RequestBody PostsDto.Post post) {

        log.info("[Add Another Way Method Posts Start]");
        log.info("[post info] {}", post.toString());

        try {
            // 유저를 불러온 후 글 Repository에 저장하는 방식
            // 서로 다른 Repository를 두번 호출하기 때문에 좋은 방식은 아니다.
            Users users = usersRepository.findById(post.getUserId()).orElseThrow(() -> new EntityNotFoundException());
            Posts posts = new Posts();
            posts.setTitle(post.getTitle());
            posts.setContent(post.getContent());
            posts.setUsers(users);

            postsRepository.save(posts);

        } catch (EntityNotFoundException enfe) {
            log.error("[Add Posts Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Add Posts End]");
    }

    /**
     * 글을 수정하는 API
     * @param postId
     * @param post
     */
    @Transactional
    @PutMapping("/{postId}")
    public void updatePosts(@PathVariable Long postId,
                            @Valid @RequestBody PostsDto.Post post) {

        log.info("[Update Posts Start]");
        log.info("[postId info] {}", postId);
        log.info("[post info] {}", post.toString());

        Posts posts = null;

        try {
            posts = postsRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException());
            posts.setTitle(post.getTitle());
            posts.setContent(post.getContent());

        } catch (EntityNotFoundException enfe) {
            log.error("[Update Posts Error]");
            log.error(enfe.getMessage());
        }

        log.info("[Update Posts End]");
    }

    /**
     * 글을 삭제하는 API
     * @param postId
     */
    @DeleteMapping("/{postId}")
    public void deleteUsers(@PathVariable Long postId) {

        log.info("[Delete Posts Start]");
        log.info("[postId info] {}", postId);

        postsRepository.deleteById(postId);

        log.info("[Delete Posts End]");
    }
}
