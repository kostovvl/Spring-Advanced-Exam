package springadvanced.exam.message.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class MessageBinding {

    private String senderName;
    private String senderEmail;
    private String subject;
    private String messageBody;

    public MessageBinding() {
    }

    @Length(min = 2, message = "Sender name must be at least 2 characters long!")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Email(message = "Please enter valid Email!")
    @Length(min = 1, message = "Email field can not be empty!")
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Length(min = 3, message = "Message subject must be at least 3 characters long!")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Length(min = 10, message = "Message body must be at least 10 characters long!")
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
