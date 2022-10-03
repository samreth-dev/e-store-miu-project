package miu.edu.payment.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String address;
    private String state;
    private String zipcode;
}
