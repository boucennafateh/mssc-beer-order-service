package guru.sfg.beer.order.service.services.beerService;

import guru.sfg.beer.order.service.services.beerService.model.BeerDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeerServiceRestTemplateImpl implements BeerService{

    public static final String BEER_SERVICE_PATH = "/api/v1/beer/upc/{upc}";
    private final RestTemplate restTemplate;
    private final String beerServiceHost;

    public BeerServiceRestTemplateImpl(Environment env, RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.beerServiceHost = env.getProperty("beer.host");
    }

    @Override
    public BeerDto getBeerByUpc(String upc) {
        ResponseEntity<BeerDto> response = restTemplate.exchange(
                beerServiceHost + BEER_SERVICE_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                upc
        );

        return response.hasBody()?response.getBody():null;
    }
}
