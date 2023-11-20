package com.himanshu.crud.service;


import com.himanshu.crud.config.ApiConfig;
import com.himanshu.crud.exceptions.CredentialsNotValidException;
import com.himanshu.crud.exceptions.RecordNotFoundException;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Service
public class DataService {

    private ApiConfig apiConfig;

    private RestTemplate restTemplate;

    @Autowired
    public DataService(ApiConfig apiConfig, RestTemplate restTemplate) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;

        String username = apiConfig.getApiUsername();
        String password = apiConfig.getApiPassword();

        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new CredentialsNotValidException("Username or Password not valid");
        }
    }

    public String fetchTickets() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://z3nzendeskcodingchallenge9654.zendesk.com/api/v2/tickets")
                .newBuilder()
                .addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(apiConfig.getApiUsername(), apiConfig.getApiPassword()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 404) {
                throw new RecordNotFoundException("Record not found");
            }

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }



    public String fetchTicketById(Long id) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://z3nzendeskcodingchallenge9654.zendesk.com/api/v2/tickets/"+id;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(apiUrl)
                .newBuilder()
                .addQueryParameter("external_id", "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(apiConfig.getApiUsername(), apiConfig.getApiPassword()))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 404) {
                throw new RecordNotFoundException("Record not found");
            }

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    }




    public ResponseEntity<String> fetchAllTickets() {
        try {
            String url = "https://z3nzendeskcodingchallenge9654.zendesk.com/api/v2/tickets";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(apiConfig.getApiUsername(), apiConfig.getApiPassword());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if(response == null || response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RecordNotFoundException("No tickets found");
            }

            return response;

        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteTicketById(Long ticketId) {
        System.out.println("Delete ticket by ID!!!");
        try {
            String auth = apiConfig.getApiUsername() + ":" + apiConfig.getApiPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            System.out.println("This is authheader:..."+authHeader);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Authorization", authHeader);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);


            ResponseEntity<String> response = restTemplate.exchange(
                    "https://z3nzendeskcodingchallenge9654.zendesk.com/api/v2/tickets/" + ticketId,
                    HttpMethod.DELETE, entity, String.class);

            if(response == null || response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RecordNotFoundException("No ticket found with id: " + ticketId);
            }

            return response;

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }


    //pagination implementation...

    public ResponseEntity<String> getData(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "25") int perPage) {
        System.out.println("GetData called!!!");
        try {
            String auth = apiConfig.getApiUsername()+ ":" + apiConfig.getApiPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Authorization", authHeader);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> response =  restTemplate.exchange(
                    "https://z3nzendeskcodingchallenge9654.zendesk.com/api/v2/tickets?page=" + page + "&per_page=" + perPage,
                    HttpMethod.GET, entity, String.class);
            if(response == null || response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RecordNotFoundException("No tickets found");
            }
            return response;

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

}