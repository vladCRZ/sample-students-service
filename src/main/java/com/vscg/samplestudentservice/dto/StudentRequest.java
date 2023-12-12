package com.vscg.samplestudentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;

}