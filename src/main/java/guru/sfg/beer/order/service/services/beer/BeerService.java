package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.services.beer.model.BeerDto;

public interface BeerService {
    BeerDto getBeerByUpc(String upc);
}
