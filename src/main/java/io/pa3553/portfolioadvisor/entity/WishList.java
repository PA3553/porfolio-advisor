package io.pa3553.portfolioadvisor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "wish_list")
public class WishList
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy="wishList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<WishListCryptoCurrency> cryptoCurrencies = new HashSet<>();

    @Column(name = "removed")
    private boolean removed = false;

    public WishList(Long id, @NonNull String name)
    {
        this.id = id;
        this.name = name;
    }
}
