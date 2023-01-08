package com.artmcoder.spreadsearch.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Exchange {
    private String firstCrypto;
    private String secondCrypto;
    private Double difference;

    private String buyExchange;
    private Double buyCourse;

    private String sellExchange;
    private Double sellCourse;
}
