package com.assignment.api.service;

import com.assignment.api.model.Rent;

import java.util.List;

public interface IRentService {

    Rent startRent(Integer userId, Integer carId);

    Rent endRent(Integer rentId, Integer userId);

    List<Rent> getUsersRents(Integer userId);

    Rent findByRentIdUserId(Integer rentId, Integer userId);


}
