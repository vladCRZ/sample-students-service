package com.vscg.samplestudentservice;

import com.vscg.samplestudentservice.dto.StudentRequest;
import com.vscg.samplestudentservice.dto.StudentResponse;
import com.vscg.samplestudentservice.exception.DataMissingException;
import com.vscg.samplestudentservice.exception.DataNotModifiedException;
import com.vscg.samplestudentservice.exception.InvalidDataException;
import com.vscg.samplestudentservice.exception.ResourceNotFoundException;
import com.vscg.samplestudentservice.model.Student;
import com.vscg.samplestudentservice.repository.StudentRepository;
import com.vscg.samplestudentservice.service.StudentServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.vscg.samplestudentservice.util.CommonUtils;


@SpringBootTest
class StudentServiceApplicationTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;


    @Test
    public void testAddStudent() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId("id");
        studentRequest.setFirstName("firstName");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("mobileNumber");


        Student student = new Student();
        student.setId("id");
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setEmail("email");
        student.setMobileNumber("mobileNumber");

        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse studentResponse = studentService.addStudent(studentRequest);

        assertNotNull(studentResponse);
        assertNotNull(studentResponse.getStudentId());
        assertEquals("firstName", studentResponse.getFirstName());
        assertEquals("lastName", studentResponse.getLastName());
        assertEquals("email", studentResponse.getEmail());
        assertEquals("mobileNumber", studentResponse.getMobileNumber());
    }

    @Test
    public void testAddStudentWithMissingData() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId("id");
        studentRequest.setFirstName("");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("mobileNumber");

        DataMissingException dataMissingException = assertThrows(DataMissingException.class, () -> {
            studentService.addStudent(studentRequest);
        });
        assertEquals("firstName is required.", dataMissingException.getMessage());

    }




    @Test
    public void testUpdateStudent() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId("id");
        studentRequest.setFirstName("firstName");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("updatedMobileNumber");

        Student student = new Student();
        student.setId("id");
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setEmail("email");
        student.setMobileNumber("mobileNumber");

        Student updatedStudent = new Student();
        updatedStudent.setId("id");
        updatedStudent.setFirstName("firstName");
        updatedStudent.setLastName("lastName");
        updatedStudent.setEmail("email");
        updatedStudent.setMobileNumber("updatedMobileNumber");

        when(studentRepository.findById(any())).thenReturn(java.util.Optional.of(student));
        when(studentRepository.save(any())).thenReturn(updatedStudent);

        StudentResponse studentResponse = studentService.updateStudent(studentRequest);

        assertNotNull(studentResponse);
        assertNotNull(studentResponse.getStudentId());
        assertEquals("firstName", studentResponse.getFirstName());
        assertEquals("lastName", studentResponse.getLastName());
        assertEquals("email", studentResponse.getEmail());
        assertEquals("updatedMobileNumber", studentResponse.getMobileNumber());
    }


    @Test
    public void testUpdateStudentWithIllegalArgumentException() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setFirstName("firstName");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("updatedMobileNumber");

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(studentRequest);
        });
        assertEquals("studentId is required.", illegalArgumentException.getMessage());

    }
    @Test
    public void testUpdateStudentWithResourceNotFoundException() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId("id");
        studentRequest.setFirstName("firstName");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("updatedMobileNumber");

        when(studentRepository.findById(any())).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.updateStudent(studentRequest);
        });
        assertEquals("student not found.", resourceNotFoundException.getMessage());

    }
    @Test
    public void testUpdateStudentWithDataNotModifiedException(){
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentId("id");
        studentRequest.setFirstName("firstName");
        studentRequest.setLastName("lastName");
        studentRequest.setEmail("email");
        studentRequest.setMobileNumber("mobileNumber");

        Student student = new Student();
        student.setId("id");
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setEmail("email");
        student.setMobileNumber("mobileNumber");

        when(studentRepository.findById(any())).thenReturn(java.util.Optional.of(student));

        DataNotModifiedException dataNotModifiedException = assertThrows(DataNotModifiedException.class, () -> {
            studentService.updateStudent(studentRequest);
        });
        assertEquals("Data not modified because it is the same in the database.", dataNotModifiedException.getMessage());

    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setId("id");
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setEmail("email");
        student.setMobileNumber("mobileNumber");

        when(studentRepository.findById(any())).thenReturn(java.util.Optional.of(student));
        doNothing().when(studentRepository).delete(any());

        studentService.deleteStudent("id");

        verify(studentRepository, times(1)).findById(any());
        verify(studentRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDeleteStudentWithIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            studentService.deleteStudent(null);
        });
        assertEquals("studentId is required.", illegalArgumentException.getMessage());
    }

    @Test
    public void testDeleteStudentWithResourceNotFoundException() {
        when(studentRepository.findById(any())).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent("id");
        });
        assertEquals("student not found.", resourceNotFoundException.getMessage());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setId("id");
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setEmail("email");
        student.setMobileNumber("mobileNumber");

        when(studentRepository.findById(any())).thenReturn(java.util.Optional.of(student));

        StudentResponse studentResponse = studentService.getStudentById("id");

        assertNotNull(studentResponse);
        assertNotNull(studentResponse.getStudentId());
        assertEquals("firstName", studentResponse.getFirstName());
        assertEquals("lastName", studentResponse.getLastName());
        assertEquals("email", studentResponse.getEmail());
        assertEquals("mobileNumber", studentResponse.getMobileNumber());
    }

    @Test
    public void testGetStudentByIdWithIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentById(null);
        });
        assertEquals("studentId is required.", illegalArgumentException.getMessage());
    }

    @Test
    public void testGetStudentByIdWithResourceNotFoundException() {
        when(studentRepository.findById(any())).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById("id");
        });
        assertEquals("student not found.", resourceNotFoundException.getMessage());
    }


    @Test
    public void testGetAllStudents() {
        Pageable pageable = PageRequest.of(0, 30, Sort.Direction.DESC, "modified");
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId("Id");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("johndoe@example.com");
        student.setMobileNumber("1234567890");
        students.add(student);

        Page<Student> studentsPage = new PageImpl<>(students, pageable, students.size());

        when(studentRepository.findAll(pageable)).thenReturn(studentsPage);


        Page<StudentResponse> studentResponses = studentService.getAllStudents(pageable);

        assertEquals(students.size(), studentResponses.getTotalElements());
        assertEquals(students.get(0).getId(), studentResponses.getContent().get(0).getStudentId());
        assertEquals(students.get(0).getFirstName(), studentResponses.getContent().get(0).getFirstName());
        assertEquals(students.get(0).getLastName(), studentResponses.getContent().get(0).getLastName());
        assertEquals(students.get(0).getEmail(), studentResponses.getContent().get(0).getEmail());
        assertEquals(students.get(0).getMobileNumber(), studentResponses.getContent().get(0).getMobileNumber());
        assertEquals(CommonUtils.convertDateToString(students.get(0).getDateOfBirth()), studentResponses.getContent().get(0).getDateOfBirth());
        assertEquals(students.get(0).getCreated(), studentResponses.getContent().get(0).getCreated());
        assertEquals(students.get(0).getModified(), studentResponses.getContent().get(0).getModified());

        verify(studentRepository, times(1)).findAll(pageable);
    }




}
