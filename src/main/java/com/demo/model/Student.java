package com.demo.model;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Student extends Person {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="class_id", nullable=false)
    private Class classe;   

   
}

