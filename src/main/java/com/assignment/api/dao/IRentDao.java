package com.assignment.api.dao;

import com.assignment.api.model.Rent;
import com.assignment.api.dao.base.IBaseDao;

import java.util.List;
import java.util.Optional;

public interface IRentDao extends IBaseDao<Rent> {

    Optional<Rent> findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(Integer userId, Integer carId);

    Optional<Rent> findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(Integer rentId, Integer userId);

    List<Rent> findByUserId(Integer userId);

    Optional<Rent> findByRentIdUserId(Integer rentId, Integer userId);

}
