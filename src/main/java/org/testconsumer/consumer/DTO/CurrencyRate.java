package org.testconsumer.consumer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class CurrencyRate {


    private String r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;

}
