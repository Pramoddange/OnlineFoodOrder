package com.zosh.dto.request;

import com.zosh.model.Address;
import com.zosh.model.ContactInformation;
import lombok.Data;

import java.util.List;
@Data
public class CreateRestaurantRequest {

    private Long id;

    private String name;

    private String description;

    private String cuisineType;

    private Address address;

    private ContactInformation contactInformation;

    private String opningHours;

    private List<String> image;
}
