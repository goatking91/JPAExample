<!-- $theme: gaia -->
# JPA 대해 알아보자

---

# JPA(Java Persistence API)란?

*  자바 플랫폼 SE와 자바 플랫폼 EE를 사용하는 응용프로그램에서 관계형 데이터베이스의 관리를 표현하는 자바 API
* 기존에 EJB(Enterprise JavaBeans)에서 제공되던 엔터티 빈(Entity Bean)을 대체하는 기술
* Java ORM 표준 기술
* Hibernate, OpenJPA, EclipseLink, TopLink Essentials 등이  JPA를 구현한 기술

---

# Object-relational Mapping

* 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑하는 기술
	* 객체 지향 프로그래밍은 클래스를 사용하고, 관계형 데이터베이스는 테이블을 사용
	* 객체 모델과 관계형 모델 간에 불일치가 존재하므로 
	* ORM 을 통해 객체 간의 관계를 바탕으로 SQL을 자동으로 생성하여 불일치를 해결
	* ORM과 다른 기술로는 SQL Mapper가 존재

---

# SQL Mapper

* SQL 문장으로 직접 데이터베이스 데이터를 다루는 기술
* XML 서술자나 애너테이션(annotation)을 사용하여 저장 프로시저나 SQL 문으로 객체들을 연결
* SQL Mapper에 대표적인 예로 iBatis, MyBatis 등이 존재 
* 예시
```
<select id="getUsers" parameterType="int" 
resultType="hashmap">
  SELECT * FROM Users WHERE ID = #{id}
</select>
```

---

# JPA의 장점

* 객체지향적으로 데이터를 관리할 수 있기 때문에 비즈니스 로직에 집중 할 수 있으며, 객체지향 개발이 가능
* JPA를 잘 이해하고 있는 경우 테이블 생성, 변경, 관리가 편함
* 재사용 및 유지보수가 용이
* 빠른 개발이 가능
* RDBMS 종류와 무관한 코딩이 가능

---

# JPA의 단점

* 사용하기는 편하지만 설계는 매우 신중하게 해야함
* 프로젝트의 복잡성이 커질경우 난이도 또한 올라갈 수 있음
* 잘못 구현된 경우에 속도 저하 및 심각할 경우 일관성이 무너지는 문제점이 생길 수 있음
* 러닝 커브가 큼

---

# JPA와 RDBMS의 비교

* Java에서의 객체 참조(Object References)
	* 방향성이 있음 
	* Java에서 양방향 관계가 필요한 경우 연관을 두 번 정의해야 함
	* 즉, 서로 Reference를 가지고 있어야 함
* RDBMS의 외래키(Foreign Key)
	* FK와 테이블 Join은 관계형 데이터베이스 연결을 자연스럽게 만듬
	* 방향성이 없음

---

# JPA 관계 설정

* JPA에서 관계 설정을 하게 되면 알아서 객체에서는 참조를 사용해서 관계를 맺고 테이블은 외래 키를 통해 관계를 맺은 후 서로 매핑을 함
* SQL 문들을 신경 쓸 필요 없이 객체 간의 관계만 신경 쓰면 되기 때문에 비즈니스 로직에 집중할 수 있음
* 관계 설정은 단방향, 양방향으로 관계를 맺을 있음
* 연관관계의 주인은 보통
* 보통 외래 키를 관리하는 엔터티가 연관 관계의 주인
* 관계 설정은 1:1, 1:N, N:1, N:M 관계가 있음 (N:M 관계는 보통 Mapping Table을 둬 1:N 관계를 2개 가짐)

---

# Persistence Context 

* persistence context는 엔티티의 @Id 필드를 이용하여 엔티티를 식별
* persistence context에는 쓰기 지연 기능이 존재
* 즉 값을 변경하자마자 바로 DB에 반영하는 것이 아니라 엔티티 매니저가 commit() 메서드를 호출할 때 DB에 반영.
* 그 밖에 1차 캐시, 동일성 보장, 변경 감지, 지연 로딩을 지원함

--- 

# 참고
* https://blog.woniper.net/255
* https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_%ED%8D%BC%EC%8B%9C%EC%8A%A4%ED%84%B4%EC%8A%A4_API
* https://ko.wikipedia.org/wiki/%EA%B0%9D%EC%B2%B4_%EA%B4%80%EA%B3%84_%EB%A7%A4%ED%95%91
* https://trends.google.com/trends/explore?q=mybatis,hibernate
* 자바 ORM 표준 JPA 프로그래밍 (지은이 김영한)

--- 

# 소스 파일
* Spring boot 2.1.6.RELEASE
* DB: H2

---
# 끝
