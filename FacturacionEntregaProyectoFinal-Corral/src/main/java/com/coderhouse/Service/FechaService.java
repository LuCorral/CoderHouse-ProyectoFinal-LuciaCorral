package com.coderhouse.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;




@Service
public class FechaService {
	
    private final static String API_URL = "http://worldclockapi.com/api/json/utc/now";

    public LocalDateTime obtenerFecha() {
    	LocalDateTime fechaActual;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String fecha = jsonNode.path("currentDateTime").asText();

            fechaActual = OffsetDateTime.parse(fecha, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime();
            return fechaActual;
        } catch (Exception e) {
            // En caso de error al obtener o parsear la fecha remota, se utiliza la fecha local
            fechaActual = LocalDateTime.now();
            return fechaActual; // si falla uso lo hago de manewra local
        }
    }



}
