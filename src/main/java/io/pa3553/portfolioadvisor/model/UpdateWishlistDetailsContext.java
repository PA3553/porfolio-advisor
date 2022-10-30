package io.pa3553.portfolioadvisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateWishlistDetailsContext
{
    private Long wishListId;
    private String name;
    private String description;
}
