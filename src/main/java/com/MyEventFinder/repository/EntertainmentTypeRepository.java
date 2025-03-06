package com.MyEventFinder.repository;

import com.MyEventFinder.model.entity.Entertainment;
import com.MyEventFinder.model.entity.EntertainmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Long> {

    List<EntertainmentType> getAllByDeleted(Boolean deleted);

    List<EntertainmentType> findByIdAndDeleted(Long id, Boolean deleted);
}
