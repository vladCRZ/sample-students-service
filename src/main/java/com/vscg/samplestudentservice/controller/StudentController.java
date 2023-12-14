package com.vscg.samplestudentservice.controller;

import com.vscg.samplestudentservice.dto.StudentRequest;
import com.vscg.samplestudentservice.dto.StudentResponse;
import com.vscg.samplestudentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(path = "/v1/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(path = "/add")
    public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.addStudent(studentRequest));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentService.updateStudent(studentRequest));
    }

    @DeleteMapping(path = "/{studentId}/delete")
    public void deleteStudent(@PathVariable(name = "studentId") String studentId) {
        studentService.deleteStudent(studentId);
    }

    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentResponse> getStudent(
            @PathVariable(name = "studentId") String studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Page<StudentResponse>> getStudents(
            @PageableDefault(page = 0, size = 30) @SortDefault.SortDefaults({
                    @SortDefault(sort = "modified", direction = Sort.Direction.DESC)}) Pageable pageable) {

        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

}