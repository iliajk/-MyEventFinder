package com.MyEventFinder.repository;

import com.MyEventFinder.model.entity.EntertainmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Long> {

}
