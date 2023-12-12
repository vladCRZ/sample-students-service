package com.vscg.samplestudentservice.model.repository;

import com.vscg.samplestudentservice.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

    Page<Student> findAll(Pageable pageable);

}