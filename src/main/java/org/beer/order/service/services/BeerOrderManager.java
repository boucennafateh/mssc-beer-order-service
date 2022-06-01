package org.beer.order.service.services;

import org.beer.order.service.domain.BeerOrder;

public  interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
