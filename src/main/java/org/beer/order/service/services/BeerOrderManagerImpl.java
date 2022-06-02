package org.beer.order.service.services;

import lombok.AllArgsConstructor;
import org.beer.order.service.domain.BeerOrder;
import org.beer.order.service.domain.BeerOrderEventEnum;
import org.beer.order.service.domain.BeerOrderStatusEnum;
import org.beer.order.service.repositories.BeerOrderRepository;
import org.beer.order.service.sm.BeerOrderStateChangeInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BeerOrderManagerImpl implements BeerOrderManager {

    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> factory;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor;

    @Override
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {
        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATE_ORDER);

        return savedBeerOrder;
    }

    public void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum event){
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = build(beerOrder);

        Message message = MessageBuilder
                .withPayload(event)
                .setHeader(ORDER_ID_HEADER, beerOrder.getId().toString())
                .build();

        sm.sendEvent(Mono.just(message)).subscribe();
    }

    public StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder){

        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm =
                factory.getStateMachine(beerOrder.getId());

        sm.stopReactively().block();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(beerOrderStateChangeInterceptor);
                    sma.resetStateMachineReactively(new DefaultStateMachineContext<BeerOrderStatusEnum, BeerOrderEventEnum>(
                            beerOrder.getOrderStatus(), null, null, null
                    )).subscribe();
                });

        sm.startReactively().subscribe();

        return sm;
    }
}
