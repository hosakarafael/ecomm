package com.rafaelhosaka.ecomm.order;

import com.rafaelhosaka.ecomm.cart.Cart;
import com.rafaelhosaka.ecomm.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderedProductRepository orderedProductRepository,
                        PaymentRepository paymentRepository){
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.paymentRepository = paymentRepository;
    }

    public void save(Cart sessionCart, String paymentMethod, String address) {
        Order order = new Order();
        order.setDate(LocalDate.now());

        Payment payment = Payment.builder()
                .paymentMethod(PaymentMethod.findByName(paymentMethod))
                .shippingAddress(address)
                .build();

        paymentRepository.save(payment);

        order.setPayment(payment);

        List<OrderedProduct> orderedProductList = new ArrayList<OrderedProduct>();
        for (Product product : sessionCart.getProductsQuantityMap().keySet() ) {
            OrderedProduct orderedProduct = OrderedProduct.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .image(product.getMainImage())
                    .price(product.getPrice())
                    .quantity(sessionCart.getProductsQuantityMap().get(product))
                    .build();

            orderedProductRepository.save(orderedProduct);
            orderedProductList.add(orderedProduct);
        }

        order.setOrderedProducts(orderedProductList);

        orderRepository.save(order);
    }
}
