package com.mzhang.restaurant.repository;

import com.mzhang.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r FROM Reservation r WHERE TO_CHAR(r.reservationDateTime, 'yyyy-MM-dd') = :day ORDER BY r.bookDateTime")
    List<Reservation> lookupReservationsByDay(@Param("day") String day);

}
