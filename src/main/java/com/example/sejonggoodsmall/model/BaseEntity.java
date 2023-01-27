package com.example.sejonggoodsmall.model;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
public class BaseEntity {

    private Date createdAt;
    private Date modifiedAt;
}
