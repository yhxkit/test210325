package com.example.demo21032501.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "testdb03", catalog = "testdb03", name = "lms_log")
public class LmsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int lmsLogIdx;


    @Embedded
    SimpleServicer simpleServicer; //연관관계 주지 않음

    @OneToOne
    @JoinColumn(name="message_template_idx")
    MessageTemplate sentMessageTemplate;

    @CreationTimestamp
    Date savedDate;

    int totalSentCnt;



}
