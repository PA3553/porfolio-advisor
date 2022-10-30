package io.pa3553.portfolioadvisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCryptoCurrencyContext
{
    private String name;
    private String symbol;
}
