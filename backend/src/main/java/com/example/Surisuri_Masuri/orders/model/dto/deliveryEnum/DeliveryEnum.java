package com.example.Surisuri_Masuri.orders.model.dto.deliveryEnum;
import lombok.Getter;

@Getter
public enum DeliveryEnum {

    PACKING("배송전"),
    RELEASE("출고 처리"),
    SHIPPING("배송중"),
    DELIVERED("배송 완료");

    private final String status;

    DeliveryEnum(String status) {
        this.status = status;
    }

    public static DeliveryEnum fromString(String text) {
        for (DeliveryEnum b : DeliveryEnum.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }


}
