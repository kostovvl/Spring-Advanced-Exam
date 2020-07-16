package springadvanced.exam.message.domain;

import springadvanced.exam.utils.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    private String senderName;
    private String senderEmail;
    private String subject;
    private String messageBody;

    public Message() {
    }

    @Column(name = "sender_name", nullable = false)
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column(name = "sender_emial", nullable = false, unique = true)
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Column(name = "subject", nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "message_body", nullable = false, columnDefinition = "text")
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
