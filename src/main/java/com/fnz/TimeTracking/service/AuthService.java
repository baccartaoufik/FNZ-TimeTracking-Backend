package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Utilisateur;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {


  private RestTemplate restTemplate; //To be used for sending Rest requests


  AuthService( RestTemplateBuilder restTemplateBuilder ) {
    this.restTemplate = restTemplateBuilder.build();
  }

  private String pythonMicroserviceUrl; //python url

  public Utilisateur validateFace( String image ) {
    //this method has to send the image to python service to validate it and then return The user details
    // if the image is valid otherwise null
    return null;
  }
}
