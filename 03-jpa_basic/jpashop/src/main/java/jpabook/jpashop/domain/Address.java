package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    /*
    1. @Embeddable 붙이기
    2. getter & setter 생성 후, setter는 지우거나 private (불변 객체)
    3. equals(), hashCode() 재정의
    4. 기본 생성자(?)
    5. 유용한 메서드 생성
    6. 필드에 제약 조건 추가(ex. length) → 공통 적용 가능
     */

    @Column(length = 10)
    private String city;

    @Column(length = 20)
    private String street;

    @Column(length = 5)
    private String zipcode;

    // 유용한 메서드 생성 가능
    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }

    public boolean isValid() {
        return true;
    }

    // getter & setter
    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    // equals() & hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
