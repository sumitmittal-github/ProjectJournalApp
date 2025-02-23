package com.sumit.controller;

/*
    Here we can create different callback controller for login with different 3rd party applications -
    ex - Login with Google      - GoogleAuthController.java
         Login with Facebook    - FacebookAuthController.java
         Login with Github      - GithubAuthController.java
*/

import com.sumit.constant.Roles;
import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import com.sumit.security.a_ss_basic_auth.CustomUserDetailsService;
import com.sumit.security.b_ss_with_JWT.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/google")
@Log4j2
public class GoogleAuthController {

    // NOTE : This end point will be hit by the Google Auth API with the authorizationCode
    //        because FE sent this controller endpoint as the redirectURI when it hit the Google Auth API

    private static final String CLIENT_ID = "CLIENT_ID_WE_GOT_FROM_GOOGLE_WHEN_WE_CREATED_APP_INSIDE_GOOGLE";
    private static final String CLIENT_SECRET = "CLIENT_SECRET_WE_GOT_FROM_GOOGLE_WHEN_WE_CREATED_APP_INSIDE_GOOGLE";
    private static final String GOOGLE_ACCESS_TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USER_DETAILS_ENDPOINT = "https://oauth2.googleapis.com/tokeninfo?id_token=";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String authorizationCode){
        try{

            //STEP-1 : get authorizationCode by access token

                // create header
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                // create parameters
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("client_id", CLIENT_ID);
                params.add("client_secret", CLIENT_SECRET);
                params.add("code", authorizationCode);
                params.add("grant_type", "authorization_code");
                params.add("redirect_uri", "localhost:8080/auth/google/callback");

                // create request object
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

                // post hit to google token API to get the ACCESS_TOKEN
                ResponseEntity<Map> tokenApiResponse = restTemplate.postForEntity(GOOGLE_ACCESS_TOKEN_ENDPOINT, request, Map.class);
                String accessToken = (String) tokenApiResponse.getBody().get("id_token");



            //STEP-2 : get user details by access token

                ResponseEntity<Map> userInfoApiResponse = restTemplate.getForEntity(GOOGLE_USER_DETAILS_ENDPOINT+accessToken, Map.class);
                Map<String, Object> userInfo = userInfoApiResponse.getBody();



            //STEP-3 :  if user in not in the DB, then create the user and then generate the JWT token and return this token to FE
            //          if user is already in the DB, then generate the JWT token and return this token to FE

                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;
                try {
                    userDetails = customUserDetailsService.loadUserByUsername(email); // surrounded with try-catch because our this method throws exception when user does not exists in DB and we want to create the user in DB if it does not exists already
                } catch (Exception _) {
                    User newUser = new User(email, UUID.randomUUID().toString(), email, true, List.of(Roles.USER.toString()));
                    userRepository.save(newUser);

                    // fetch the same user from DB again so that we can set this user in context
                    userDetails = customUserDetailsService.loadUserByUsername(email);
                }

                // return JWT token to the FE
                String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
                return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        } catch(Exception e){
            log.error("Exception occurred when tried to login with 3rd party API as Google : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}