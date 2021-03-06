package org.beer.order.service.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beer.order.service.domain.Customer;
import org.beer.order.service.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 2019-06-06.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class BeerOrderBootStrap implements CommandLineRunner {
    public static final String TASTING_ROOM = "Tasting Room";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        loadCustomerData();
    }

    private void loadCustomerData() {
        UUID uuid = UUID.randomUUID();
        if (customerRepository.count() == 0) {
            Customer customer = customerRepository.save(Customer.builder()
                    .customerName(TASTING_ROOM)
                    .apiKey(uuid)
                    .build());
            log.info("Customer id = " + customer.getId());
        }
    }
}
