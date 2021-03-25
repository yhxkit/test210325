package com.example.demo21032501.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(schema = "testdb03", catalog = "testdb03")
@Entity
public class LmsServicer {

    public LmsServicer(SimpleServicer simpleServicer){
        this.simpleServicer = simpleServicer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int lmsServicerIdx;

    @Embedded
    SimpleServicer simpleServicer;
}
