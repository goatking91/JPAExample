package io.lfin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Users extends BaseTimeEntity {

    // PK
    // PK 규칙은 고유식별자로 보통 autoIncrement 된다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long age;

    // 유저와 글은 1:N의 관계를 가지고 있다.
    // Cascade (영속성전이)는 등록, 삭제할 때 사용된다.
    // FetchType.LAZY 은 지연로딩을 사용한다.
    // 보통 1:1경우에는 즉시로딩을 사용하고 1:N 경우에는 지연로딩을 사용한다.
    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Posts> postsList = new ArrayList<>();

    // 1:N에 N에 추가시 사용되는 메소드이다.
    public void addPosts(Posts posts) {
        this.postsList.add(posts);
        posts.setUsers(this);
    }

}
