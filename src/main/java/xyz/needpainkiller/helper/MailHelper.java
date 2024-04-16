package xyz.needpainkiller.helper;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;
import xyz.needpainkiller.lib.mail.MailService;

public class MailHelper {

    private MailHelper() {
    }

    public static void sendMail(MailService mailService, String mailTo, String subject, String content) throws MessagingException {
        mailService.setTo(mailTo);
        mailService.setSubject(subject);
        mailService.setText(content);
        mailService.send();
    }

    public static void sendHtmlMail(MailService mailService, String mailTo, String subject, Context context, String templateFileName) throws MessagingException {
        mailService.setTo(mailTo);
        mailService.setSubject(subject);
        mailService.setHtml(context, templateFileName);
        mailService.send();
    }
}