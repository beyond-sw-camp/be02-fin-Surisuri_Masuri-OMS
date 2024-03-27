package com.example.Surisuri_Masuri.product.model.productEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {

    BAKERY(0, "Bakery items such as bread and pastries"),
    COFFEE_BEANS(1, "Variety of coffee beans for brewing"),
    DISPOSABLES(2, "Single-use disposable products"),
    SYRUP(3, "Flavored syrups for beverages"),
    LIQUIDS(4, "Various liquid products"),
    ;

    private final Integer no;
    private final String description;


}

