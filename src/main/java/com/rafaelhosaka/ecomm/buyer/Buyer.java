package com.rafaelhosaka.ecomm.buyer;

import com.rafaelhosaka.ecomm.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "buyer")
public class Buyer extends User {
    private Integer point;

    public Buyer() {
    }

    public Buyer(Long id, String firstName, String lastName, String adress, String phonenumber, LocalDate dateBirth, String email, String password) {
        super(id, firstName, lastName, adress, phonenumber, dateBirth, email, password);
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
