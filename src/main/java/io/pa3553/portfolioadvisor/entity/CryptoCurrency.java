package io.pa3553.portfolioadvisor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "crypto_currency")
public class CryptoCurrency
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;

    @Column(name = "removed")
    private boolean removed = false;
}
