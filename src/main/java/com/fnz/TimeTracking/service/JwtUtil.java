package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

  private static final String SECRET_KEY = "your_secret_key"; //Json secret Key used as token signature

  public static String generateToken( Utilisateur userDetails )
  {
    // this method will generate the token for the user, it will add the user email as a subject and sets the token life
    // span to 1 hour
    // docs: io.jsonwebtoken.Jwts
    return null;
  }
}