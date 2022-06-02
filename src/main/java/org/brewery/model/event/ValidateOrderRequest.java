package org.brewery.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.brewery.model.BeerOrderDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderRequest {

    private BeerOrderDto beerOrder;
}