package io.pa3553.portfolioadvisor.entity;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "wish_list_crypto_currency")
public class WishListCryptoCurrency
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "goal")
    private Double goal;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crypto_currency_id", nullable = false)
    private CryptoCurrency cryptoCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_list_id", nullable = false)
    private WishList wishList;

    @Column(name = "removed")
    private boolean removed = false;
}
