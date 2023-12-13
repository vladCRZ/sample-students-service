package com.vscg.samplestudentservice.service;

import com.vscg.samplestudentservice.dto.StudentRequest;
import com.vscg.samplestudentservice.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {

    StudentResponse addStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(StudentRequest studentRequest);

    void deleteStudent(String studentId);

    StudentResponse getStudentById(String studentId);

    Page<StudentResponse> getAllStudents(Pageable pageable);

}