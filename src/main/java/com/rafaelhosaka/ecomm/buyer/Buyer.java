package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.user.User;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Buyer extends User {
    private Integer point;

    public Buyer() {
    }

    public Buyer(Long id, String name, String adress, String phonenumber, LocalDate dateBirth, String email) {
        super(id, name, adress, phonenumber, dateBirth, email);
        this.point = 0;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "point=" + point +
                '}';
    }

}
