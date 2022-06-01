package org.beer.order.service.services.beer;

import org.beer.order.service.services.beer.model.BeerDto;

public interface BeerService {
    BeerDto getBeerByUpc(String upc);
}
