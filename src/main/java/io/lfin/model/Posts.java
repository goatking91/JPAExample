package io.lfin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Posts extends BaseTimeEntity {

    // PK
    // PK 규칙은 고유식별자로 보통 autoIncrement 된다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    // userId컬럼을 불러올 수 있다. 하지만 등록 또는 변경시에는 사용 불가
    @Column(insertable = false, updatable = false)
    private Long userId;

    // 글과 유저에는 N:1의 관계를 가지고 있다.
    // 조인 컬럼은 userId로 명명한다.
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

}
