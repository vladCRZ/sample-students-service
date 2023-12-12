package com.vscg.samplestudentservice.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentResponse {

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String dateOfBirth;
    private String createdBy;
    private String modifiedBy;
    private Date created;
    private Date modified;

}