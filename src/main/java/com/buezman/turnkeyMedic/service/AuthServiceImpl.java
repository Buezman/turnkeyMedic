package com.buezman.turnkeyMedic.service;

import com.buezman.turnkeyMedic.entity.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final RestTemplate restTemplate;

    @Override
    public String generateAuthToken() {
        log.info("Calling auth service");
        String uri = "https://sandbox-authservice.priaid.ch/login";
        String apiKey = "chibuezenwajiobi@gmail.com";
        String secretKey = "b8YWd93Pwe2JEr5p4";
        String hash = null;
        try {
            hash = generateHash(uri, secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s:%s", apiKey,hash));
        headers.add(HttpHeaders.HOST, "authservice.priaid.ch");
        HttpEntity<String> httpEntity = new HttpEntity<>("",headers);
        AccessToken accessToken = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AccessToken.class).getBody();

        assert accessToken != null;
        return accessToken.getToken();
    }

    private String generateHash(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacMD5");
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(secretKeySpec);
        byte[] bytes = (mac.doFinal(data.getBytes()));
        return Base64.getEncoder().encodeToString(bytes);
    }
}
