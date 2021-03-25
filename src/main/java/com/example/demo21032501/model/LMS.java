package com.example.demo21032501.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(schema = "testdb03", catalog = "testdb03")
public class LMS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idx;
    boolean flag = false;

    @Embedded
    UserInfo user;

    String callback;

    String senderIdentifier; //랜덤값(ajax로 데이터 저장시 진행률 확인할 때 필요)
    int totalSending;

    //발송 일자 추가해서 flag 바꿀때 값 셋해줄것(admin page)

    @ManyToOne(optional = false)
    @JoinColumn(name="message_template_idx")
    MessageTemplate messageTemplate;

    public LMS(UserInfo user, MessageTemplate messageTemplate, String senderIdentifier, int totalSending, String callback){
        this.user = user;
        this.messageTemplate = messageTemplate;
        this.senderIdentifier = senderIdentifier;
        this.totalSending = totalSending;
        this.callback = callback;
    }

}
