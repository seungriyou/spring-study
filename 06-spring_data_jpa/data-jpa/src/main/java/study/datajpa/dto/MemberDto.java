package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data   // DTO에는 @Data 써도 된다. 엔티티에는 X!!
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    // DTO는 엔티티를 봐도 된다.
    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
