package com.vscg.samplestudentservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vscg.samplestudentservice.dto.StudentRequest;
import com.vscg.samplestudentservice.dto.StudentResponse;
import com.vscg.samplestudentservice.exception.DataMissingException;
import com.vscg.samplestudentservice.exception.DataNotModifiedException;
import com.vscg.samplestudentservice.exception.ResourceNotFoundException;
import com.vscg.samplestudentservice.model.Student;
import com.vscg.samplestudentservice.model.repository.StudentRepository;
import com.vscg.samplestudentservice.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentResponse addStudent(StudentRequest studentRequest) {
        LOGGER.info("addStudent invoked...");

        if (studentRequest.getFirstName() == null || studentRequest.getFirstName().isEmpty()) {
            throw new DataMissingException("firstName is required.");
        }
        if (studentRequest.getLastName() == null || studentRequest.getLastName().isEmpty()) {
            throw new DataMissingException("lastName is required.");
        }
        if (studentRequest.getEmail() == null || studentRequest.getEmail().isEmpty()) {
            throw new DataMissingException("email is required.");
        }

        Student student = Student.builder().firstName(studentRequest.getFirstName())
                .lastName(studentRequest.getLastName()).email(studentRequest.getEmail())
                .mobileNumber(studentRequest.getMobileNumber())
                .dateOfBirth(CommonUtils.convertStringToDate(studentRequest.getDateOfBirth())).build();

        student = studentRepository.save(student);

        return StudentResponse.builder().studentId(student.getId())
                .firstName(student.getFirstName() != null ? student.getFirstName() : "")
                .lastName(student.getLastName() != null ? student.getLastName() : "")
                .email(student.getEmail() != null ? student.getEmail() : "")
                .mobileNumber(student.getMobileNumber())
                .dateOfBirth(CommonUtils.convertDateToString(student.getDateOfBirth()))
                .created(student.getCreated() != null ? student.getCreated() : null)
                .modified(student.getModified() != null ? student.getModified() : null)
                .createdBy(student.getCreatedBy() != null ? student.getCreatedBy() : "")
                .modifiedBy(student.getModifiedBy() != null ? student.getModifiedBy() : null).build();
    }

    @Override
    public StudentResponse updateStudent(StudentRequest studentRequest) {
        LOGGER.info("updateStudent invoked...");

        if (studentRequest.getStudentId() == null) {
            throw new IllegalArgumentException("studentId is required.");
        }

        Optional<Student> studentOpt = studentRepository.findById(studentRequest.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("student not found.");
        }

        Student existingStudent = studentOpt.get();
        boolean modified = false;

        if (studentRequest.getFirstName() != null && !studentRequest.getFirstName().contentEquals(
                existingStudent.getFirstName() != null ? existingStudent.getFirstName() : "")) {
            modified = true;
            existingStudent.setFirstName(studentRequest.getFirstName());
        }
        if (studentRequest.getLastName() != null && !studentRequest.getLastName().contentEquals(
                existingStudent.getLastName() != null ? existingStudent.getLastName() : "")) {
            modified = true;
            existingStudent.setLastName(studentRequest.getLastName());
        }
        if (studentRequest.getEmail() != null && !studentRequest.getEmail()
                .contentEquals(existingStudent.getEmail() != null ? existingStudent.getEmail() : "")) {
            modified = true;
            existingStudent.setEmail(studentRequest.getEmail());
        }
        if (studentRequest.getMobileNumber() != null && !studentRequest.getMobileNumber().contentEquals(
                existingStudent.getMobileNumber() != null ? existingStudent.getMobileNumber() : "")) {
            modified = true;
            existingStudent.setMobileNumber(studentRequest.getMobileNumber());
        }
        if (studentRequest.getDateOfBirth() != null) {
            modified = true;
            existingStudent
                    .setDateOfBirth(CommonUtils.convertStringToDate(studentRequest.getDateOfBirth()));
        }

        if (!modified) {
            throw new DataNotModifiedException("Data not modified because it is the same in the database.");
        }

        existingStudent = studentRepository.save(existingStudent);

        return StudentResponse.builder().studentId(existingStudent.getId())
                .firstName(existingStudent.getFirstName() != null ? existingStudent.getFirstName() : "")
                .lastName(existingStudent.getLastName() != null ? existingStudent.getLastName() : "")
                .email(existingStudent.getEmail() != null ? existingStudent.getEmail() : "")
                .mobileNumber(existingStudent.getMobileNumber())
                .dateOfBirth(CommonUtils.convertDateToString(existingStudent.getDateOfBirth()))
                .created(existingStudent.getCreated() != null ? existingStudent.getCreated() : null)
                .modified(existingStudent.getModified() != null ? existingStudent.getModified() : null)
                .createdBy(existingStudent.getCreatedBy() != null ? existingStudent.getCreatedBy() : "")
                .modifiedBy(
                        existingStudent.getModifiedBy() != null ? existingStudent.getModifiedBy() : null)
                .build();
    }

    @Override
    public void deleteStudent(String studentId) {
        LOGGER.info("deleteStudent invoked...");
        if (studentId == null) {
            throw new IllegalArgumentException("studentId is required.");
        }
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("student not found.");
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentResponse getStudentById(String studentId) {
        LOGGER.info("getStudentById invoked...");
        if (studentId == null) {
            throw new IllegalArgumentException("studentId is required.");
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("student not found.");
        }

        Student existingStudent = studentOpt.get();

        return StudentResponse.builder().studentId(existingStudent.getId())
                .firstName(existingStudent.getFirstName() != null ? existingStudent.getFirstName() : "")
                .lastName(existingStudent.getLastName() != null ? existingStudent.getLastName() : "")
                .email(existingStudent.getEmail() != null ? existingStudent.getEmail() : "")
                .mobileNumber(existingStudent.getMobileNumber())
                .dateOfBirth(CommonUtils.convertDateToString(existingStudent.getDateOfBirth()))
                .created(existingStudent.getCreated() != null ? existingStudent.getCreated() : null)
                .modified(existingStudent.getModified() != null ? existingStudent.getModified() : null)
                .createdBy(existingStudent.getCreatedBy() != null ? existingStudent.getCreatedBy() : "")
                .modifiedBy(
                        existingStudent.getModifiedBy() != null ? existingStudent.getModifiedBy() : null)
                .build();
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        LOGGER.info("getAllStudents invoked...");

        Page<Student> studentsPage = studentRepository.findAll(pageable);

        List<StudentResponse> studentsResponse = new ArrayList<>();
        Page<StudentResponse> studentsResponsePage = new PageImpl<>(studentsResponse, pageable, 0);

        if (studentsPage != null && !studentsPage.isEmpty()) {

            studentsPage.getContent().forEach(student -> {

                StudentResponse studentResponse = StudentResponse.builder().studentId(student.getId())
                        .firstName(student.getFirstName() != null ? student.getFirstName() : "")
                        .lastName(student.getLastName() != null ? student.getLastName() : "")
                        .email(student.getEmail() != null ? student.getEmail() : "")
                        .mobileNumber(student.getMobileNumber())
                        .dateOfBirth(CommonUtils.convertDateToString(student.getDateOfBirth()))
                        .created(student.getCreated() != null ? student.getCreated() : null)
                        .modified(student.getModified() != null ? student.getModified() : null)
                        .createdBy(student.getCreatedBy() != null ? student.getCreatedBy() : "")
                        .modifiedBy(student.getModifiedBy() != null ? student.getModifiedBy() : null).build();

                studentsResponse.add(studentResponse);
            });
            studentsResponsePage =
                    new PageImpl<>(studentsResponse, pageable, studentsPage.getTotalElements());
        }
        return studentsResponsePage;
    }

}