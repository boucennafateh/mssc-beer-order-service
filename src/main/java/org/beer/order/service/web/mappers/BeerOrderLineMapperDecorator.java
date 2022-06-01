package org.beer.order.service.web.mappers;

import org.beer.order.service.domain.BeerOrderLine;
import org.beer.order.service.services.beer.BeerService;
import org.beer.order.service.services.beer.model.BeerDto;
import org.brewery.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper  {

    private BeerOrderLineMapper beerOrderLineMapper;
    private BeerService beerService;

    @Autowired
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine) {
        BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(beerOrderLine);
        BeerDto beerDto = beerService.getBeerByUpc(beerOrderLine.getUpc());
        if(beerDto == null) return beerOrderLineDto;
        beerOrderLineDto.setBeerName(beerDto.getBeerName());
        beerOrderLineDto.setBeerId(beerDto.getId());
        beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());
        beerOrderLineDto.setPrice(beerDto.getPrice());
        return beerOrderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
        return beerOrderLineMapper.dtoToBeerOrderLine(beerOrderLineDto);
    }


}
