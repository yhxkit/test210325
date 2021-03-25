package com.example.demo21032501.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
@Table(schema = "testdb03", catalog = "testdb03")
public class MessageTemplate  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int templateIdx;

    boolean forStorage = true; // 템플릿 저장용 : true, 실제 발신용:false

    String templateName;
    String template;


    public MessageTemplate(String templateName, String template){
        this.templateName = templateName;
        this.template = template;
    }

    public MessageTemplate(String templateName, String template, boolean forStorage){
        this.templateName = templateName;
        this.template = template;
        this.forStorage = forStorage;
    }

}
