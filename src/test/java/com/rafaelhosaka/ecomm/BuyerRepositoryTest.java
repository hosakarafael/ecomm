package com.rafaelhosaka.ecomm;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerDao;
import com.rafaelhosaka.ecomm.buyer.BuyerService;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.Month;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BuyerRepositoryTest {

	@Autowired
	private BuyerDao buyerDao;

	@Test
	public void addNewBuyer() {
		Buyer buyer = new Buyer(99L,"Heimer","dinger" ,"Piltover", "88888888",
				LocalDate.of(2000, Month.JANUARY,1),"heimer@lol",
				"????");

		Buyer savedBuyer = buyerDao.save(buyer);

		Assertions.assertThat(savedBuyer).isNotNull();
		Assertions.assertThat(savedBuyer.getId()).isGreaterThan(0);
	}

}
