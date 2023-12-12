package com.vscg.samplestudentservice.model;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student")
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private Date dateOfBirth;
    private String createdBy;
    private String modifiedBy;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date modified;

}