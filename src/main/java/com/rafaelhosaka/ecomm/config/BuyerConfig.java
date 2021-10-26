package com.rafaelhosaka.ecomm.config;

import com.rafaelhosaka.ecomm.buyer.Buyer;
import com.rafaelhosaka.ecomm.buyer.BuyerDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import  java.time.Month;
import java.util.List;

@Configuration
public class BuyerConfig{
    @Bean
    CommandLineRunner commandLineRunner(BuyerDao buyerDao){
        return args -> {
            Buyer ahri = new Buyer(1L,"Ahri","Vastaya" , "Ionia", "1111111",
                    LocalDate.of(2000, Month.JANUARY,1),"ahri@lol",
                    "????");
            Buyer braum = new Buyer(2L,"Braum","Bro","Freljorld", "22222222",
                    LocalDate.of(2000, Month.JANUARY,1),"braum@lol",
                    "????");

            buyerDao.saveAll(List.of(ahri,braum));
        };
    }
}
