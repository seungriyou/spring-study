package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 내부의 private static으로 단 하나 가진다. (클래스 레벨)
    private static final SingletonService instance = new SingletonService();

    // instance를 꺼낼 수 있는 방법은 getInstance() 뿐이다.
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자 (밖에서 new 키워드로 생성할 수 없도록)
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
