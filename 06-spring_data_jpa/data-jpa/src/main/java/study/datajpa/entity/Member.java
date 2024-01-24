package study.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    // JPA를 사용하므로 엔티티에는 기본 생성자가 있어야 한다. (public, protected 가능)
    protected Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
