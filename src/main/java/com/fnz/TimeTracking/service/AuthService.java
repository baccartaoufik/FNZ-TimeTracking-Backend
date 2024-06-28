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

    //build a RestTemplate instance
    AuthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //Face Validation Method
    public Utilisateur validateFace(String image) {
        String url = pythonMicroserviceUrl + "/validate-face";
        Utilisateur user = restTemplate.postForObject(url, image, Utilisateur.class);

        //Response Validation
        if (user != null && user.isValid()) { // Assuming the Python service returns a Utilisateur object with a valid flag
            return user;
        } else {
            return null;
        }

    }
}