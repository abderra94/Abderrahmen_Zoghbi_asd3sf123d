package com.demo.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Teacher extends Person{

    @OneToOne
    @JoinColumn(name = "class_id", nullable=false)
    private Class classe;
   
}

