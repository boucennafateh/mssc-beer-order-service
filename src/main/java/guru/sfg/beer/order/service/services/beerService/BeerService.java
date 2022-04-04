package guru.sfg.beer.order.service.services.beerService;

import guru.sfg.beer.order.service.services.beerService.model.BeerDto;

public interface BeerService {
    BeerDto getBeerByUpc(String upc);
}
