package com.mzhang.restaurant.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long id;

  @JsonProperty("customerName")
  @Column(name = "customerName")
  public String customerName;

  @JsonProperty("tableSize")
  @Column(name = "tableSize")
  public int tableSize;

  @Column(name = "reservationDateTime")
  @JsonProperty("reservationDateTime")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  public Date reservationDateTime;

  @JsonProperty("reservationHours")
  @Column(name = "reservationHours")
  public int reservationHours;

  @Column(name = "bookDateTime")
  @JsonProperty("bookDateTime")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @CreatedDate
  public Date bookDateTime;

}
