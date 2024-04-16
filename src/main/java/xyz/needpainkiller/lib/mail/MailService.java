package xyz.needpainkiller.lib.mail;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;
import xyz.needpainkiller.lib.mail.error.MailException;

import java.io.IOException;
import java.util.List;

public interface MailService {

    void setFrom(String fromAddress) throws MessagingException;

    void setTo(String email) throws MessagingException;

    void setToList(List<String> emailList) throws MessagingException;


    void setCc(String email) throws MessagingException;

    void setCcList(List<String> emailList) throws MessagingException;

    void setSubject(String subject) throws MessagingException;

    void setText(String text) throws MessagingException;

    void setHtml(Context context, String templateFileName) throws MessagingException;

    void setAttach(String displayFileName, String pathToAttachment) throws MessagingException, IOException;

    void setInline(String contentId, String pathToInline) throws MessagingException, IOException;

    void send() throws MessagingException;

    void clear() throws MessagingException, MailException;
}
