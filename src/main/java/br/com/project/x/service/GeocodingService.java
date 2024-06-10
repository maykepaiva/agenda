package br.com.project.x.service;

import br.com.project.x.domain.dto.ContactRequest;
import br.com.project.x.domain.dto.GeocodingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ContactRequest getLatLongFromCep(ContactRequest request) {
        String url ="https://maps.googleapis.com/maps/api/geocode/json?address=" +
                        request.getCpf()+"&key="+apiKey;

        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        if (response != null && !response.getResults().isEmpty()) {
            var results = response.getResults().get(0);
            request.setLatitude(results.getGeometry().getLocation().getLat());
            request.setLongitude(results.getGeometry().getLocation().getLng());

            return request;
        }

        throw new RuntimeException("Não foi possível obter a latitude e longitude para o CEP: " + request.getCpf());
    }
}
