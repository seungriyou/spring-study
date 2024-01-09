package hello.core.singleton;

public class StatefulService {
    /* 이렇게 공유 필드를 이용하여 stateful 하게 설계하면 안 된다! */

    private int price;  // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 필드를 변경한다!!! x_x
    }

    public int getPrice() {
        return price;
    }
}
