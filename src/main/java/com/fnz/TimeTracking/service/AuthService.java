package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Utilisateur;
import lombok.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    final private RestTemplate restTemplate;

    //@Value("${python.microservice.url}")
    private String pythonMicroserviceUrl;

    AuthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Utilisateur validateFace(String image) {
        String url = pythonMicroserviceUrl + "/validate-face";
        Utilisateur user = restTemplate.postForObject(url, image, Utilisateur.class);
        if (user != null && user.isValid()) {
            return user;
        } else {
            return null;
        }

    }
}