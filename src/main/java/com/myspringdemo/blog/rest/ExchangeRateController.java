package com.myspringdemo.blog.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "api/exchange", produces = "application/json")
//@CrossOrigin(origins = "http://localhost:8081")
public class    ExchangeRateController {



    @GetMapping
    public String getExchangeRate() throws URISyntaxException {
      // String str =
        return ResponseEntity.created(new URI("https://bank.gov.ua/NBU_Exchange/exchange?json")).build().toString();
    }


}
