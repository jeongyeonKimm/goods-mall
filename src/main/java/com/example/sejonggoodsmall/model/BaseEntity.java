package com.example.sejonggoodsmall.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modifiedAt;
}
