package in.tech_camp.furima.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private String postalCode;
    private Integer prefectureId;
    private String city;
    private String addresses;
    private String building;
    private String phoneNumber;
    private Long orderId;
}