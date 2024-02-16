package com.mzhang.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookRequest implements Serializable {

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("tableSize")
    private int tableSize;

    @JsonProperty("reservationDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservationDateTime;

    @JsonProperty("reservationHours")
    private int reservationHours = 2;

}