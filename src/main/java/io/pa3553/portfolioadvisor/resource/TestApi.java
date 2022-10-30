package io.pa3553.portfolioadvisor.resource;

import io.pa3553.portfolioadvisor.model.response.CGCoin;
import io.pa3553.portfolioadvisor.service.CryptoTickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
public class TestApi
{
    @Autowired
    private CryptoTickerService cryptoTickerService;

    @GetMapping(value = "/test")
    public boolean getTest()
    {
        return cryptoTickerService.isAlive();
    }

    @GetMapping(value = "/coin/{coin}")
    public CGCoin getCoin(@PathVariable String coin)
    {
        return cryptoTickerService.getCoinData(coin);
    }
}
