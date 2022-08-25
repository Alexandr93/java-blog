package com.myspringdemo.blog.rest;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = CurrencyRate.class)
public class CurrencyRate {
   /* private String startdate;
    private String timesign;
    private String currencycode;
    private String currencycodel;
    private int units;
    private double amount;*/

    private String r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;

}
