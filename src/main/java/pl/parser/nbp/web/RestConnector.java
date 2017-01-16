package pl.parser.nbp.web;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestConnector {
    private RestTemplate restTemplate;

    public RestConnector() {
        restTemplate = new RestTemplate();
    }

    public String downloadData(String url){
        return restTemplate.getForObject(url, String.class);
    }
}
