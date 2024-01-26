package study.datajpa.repository;

public interface UsernameOnly {

    // @Value("#{target.username + ' ' + target.age}") -> open projections
    String getUsername();
}
