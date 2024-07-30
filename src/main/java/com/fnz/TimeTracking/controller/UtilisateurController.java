package com.fnz.TimeTracking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.service.UtilisateurService;
import com.fnz.TimeTracking.utils.JwtAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    @Value("${flask.api.url}")
    private String flaskApiUrl;

    @Autowired
    private UtilisateurService utilisateurService;

    private static final String UPLOAD_DIR = "static/images/";

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(
            @RequestPart("utilisateur") String utilisateurJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Utilisateur utilisateur = objectMapper.readValue(utilisateurJson, Utilisateur.class);

        if (photo != null && !photo.isEmpty()) {

            //String fileName = savePhoto(photo);
            String FlaskfileName = uploadPhotoToFlask(photo);
            utilisateur.setPhoto(FlaskfileName);
        }
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        return ResponseEntity.ok(savedUtilisateur);
    }

    @JwtAuth(roles = {"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestPart("utilisateur") String utilisateurJson,
                                                         @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Utilisateur updatedUtilisateur = objectMapper.readValue(utilisateurJson, Utilisateur.class);
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurById(id);
        if (existingUtilisateur == null) {
            return ResponseEntity.notFound().build();
        }
        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setPrenom(updatedUtilisateur.getPrenom());
        existingUtilisateur.setEmail(updatedUtilisateur.getEmail());
        existingUtilisateur.setTelephone(updatedUtilisateur.getTelephone());
        existingUtilisateur.setTitre(updatedUtilisateur.getTitre());
        existingUtilisateur.setSexe(updatedUtilisateur.getSexe());
        existingUtilisateur.setRole(updatedUtilisateur.getRole());
        existingUtilisateur.setDépartement(updatedUtilisateur.getDépartement());

        if (photo != null && !photo.isEmpty()) {
            // Delete old photo if exists
            if (existingUtilisateur.getPhoto() != null) {
                deletePhoto(existingUtilisateur.getPhoto());
            }
            //String fileName = savePhoto(photo);
            String FlaskfileName = uploadPhotoToFlask(photo);

            existingUtilisateur.setPhoto(FlaskfileName);
        }

        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(existingUtilisateur);
        return ResponseEntity.ok(savedUtilisateur);
    }





    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur.getPhoto() != null) {
            deletePhoto(utilisateur.getPhoto());
        }
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok().build();
    }

    private String savePhoto(MultipartFile photo) {
        try {

            String fileName =   photo.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(photo.getInputStream(), uploadPath.resolve(fileName));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + photo.getOriginalFilename(), e);
        }
    }

    private void deletePhoto(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + fileName, e);
        }
    }
    private String uploadPhotoToFlask(MultipartFile photo) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("photo", new InputStreamResource(photo.getInputStream()) {
            @Override
            public String getFilename() {
                return photo.getOriginalFilename();
            }

            @Override
            public long contentLength() {
                try {
                    return photo.getSize();
                } catch (Exception e) {
                    return -1;
                }
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                flaskApiUrl + "/upload_photo",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                return (String) responseBody.get("filename");
            }
        }
        throw new RuntimeException("Failed to upload photo");
    }

}