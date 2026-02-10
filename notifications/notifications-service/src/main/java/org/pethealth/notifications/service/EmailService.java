package org.pethealth.notifications.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.model.EmailContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

    @Async
    public void sendEmailWithAttachment(String toAddress,
                                        String subject,
                                        String message,
                                        String... attachments) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(from);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        for (String attachment : attachments) {
            FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
            messageHelper.addAttachment(file.getFilename(), file);

        }
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendHtmlEmail(EmailContext emailContext) throws MessagingException {
        log.info("Sending email to " + emailContext.getTo());

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        messageHelper.setFrom(emailContext.getFrom() != null? emailContext.getFrom() : from);
        messageHelper.setTo(emailContext.getTo());
        messageHelper.setSubject(emailContext.getSubject());

        Context context = new Context();
        context.setVariables(emailContext.getContext());

        String processed =
                templateEngine.process(emailContext.getTemplateType().getTemplateAddress(), context);

        messageHelper.setText(processed, true);

        for (File attachment : emailContext.getAttachments()) {
            messageHelper.addAttachment(attachment.getName(), attachment);
        }

        mailSender.send(message);
        log.info("Email sent successfully");
    }

}

