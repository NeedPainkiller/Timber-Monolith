package xyz.needpainkiller.lib.mail;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import xyz.needpainkiller.lib.mail.error.MailException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.MAIL_SENDER_CREATE_FAILED;


@Slf4j
@Component
@Scope("prototype")
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private SmtpAuthenticator smtpAuthenticator = new SmtpAuthenticator();

    private MimeMessage message;
    private MimeMessageHelper messageHelper;

    @Override
    public void setFrom(String fromAddress) throws MessagingException {
        messageHelper.setFrom(fromAddress);
    }

    @Override
    public void setTo(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    @Override
    public void setToList(List<String> emailList) throws MessagingException {
        messageHelper.setTo(emailList.toArray(new String[0]));
    }

    @Override
    public void setCc(String email) throws MessagingException {
        messageHelper.setTo(email);
    }

    @Override
    public void setCcList(List<String> emailList) throws MessagingException {
        messageHelper.setCc(emailList.toArray(new String[0]));
    }

    @Override
    public void setSubject(String subject) throws MessagingException {
        messageHelper.setSubject(subject);
    }

    @Override
    public void setText(String text) throws MessagingException {
        messageHelper.setText(text, false);
    }

    @Override
    public void setHtml(Context context, String templateFileName) throws MessagingException {

        String html = templateEngine.process(templateFileName, context);
        messageHelper.setText(html, true);
    }

    @Override
    public void setAttach(String displayFileName, String pathToAttachment) throws MessagingException, IOException {
        File file = new ClassPathResource(pathToAttachment).getFile();
        FileSystemResource fsr = new FileSystemResource(file);

        messageHelper.addAttachment(displayFileName, fsr);
    }

    @Override
    public void setInline(String contentId, String pathToInline) throws MessagingException, IOException {
        ClassPathResource cpr = new ClassPathResource(pathToInline);
        try (InputStream inputStream = cpr.getInputStream()) {
            File file = File.createTempFile(String.valueOf(inputStream.hashCode()), ".tmp");
            FileUtils.copyInputStreamToFile(inputStream, file);
            FileSystemResource fsr = new FileSystemResource(file);
            messageHelper.addInline(contentId, fsr);
        } catch (IOException e) {
            log.warn("IOException : {}", e.getMessage());
        }

    }

    @Override
    public void send() {
        sender.send(message);
        clear();
    }

    @PostConstruct
    @Override
    public void clear() throws MailException {
        try {
            message = sender.createMimeMessage();
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new MailException(MAIL_SENDER_CREATE_FAILED, e.getMessage());
        }
    }
}
