package com.MyEventFinder.repository;

import com.MyEventFinder.model.entity.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntertainmentRepository extends JpaRepository<Entertainment, Long> {

    List<Entertainment> getAllByDeleted(Boolean deleted);
}
