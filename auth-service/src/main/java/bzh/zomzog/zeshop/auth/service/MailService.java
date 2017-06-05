package bzh.zomzog.zeshop.auth.service;

import bzh.zomzog.zeshop.auth.configuration.AuthProperties;
import bzh.zomzog.zeshop.auth.domain.Account;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by Zomzog on 04/06/2017.
 */
@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String ACCOUNT = "account";

    private static final String BASE_URL = "baseUrl";

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final AuthProperties authProperties;
    private final SpringTemplateEngine templateEngine;


    public MailService(final JavaMailSender javaMailSender, final MessageSource messageSource, final AuthProperties authProperties, final SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.authProperties = authProperties;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(final String to, final String subject, final String content, final boolean isMultipart, final boolean isHtml) {
        this.log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(this.authProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
            this.log.debug("Sent e-mail to User '{}'", to);
        } catch (final Exception e) {
            this.log.warn("E-mail could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendActivationEmail(final Account account) {
        this.log.debug("Sending activation e-mail to '{}'", account.getLogin());
        final Locale locale = Locale.forLanguageTag(account.getLangKey());
        final Context context = new Context(locale);
        context.setVariable(ACCOUNT, account);
        context.setVariable(BASE_URL, this.authProperties.getMail().getBaseUrl());
        final String content = this.templateEngine.process("activationEmail", context);
        final String subject = this.messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(account.getLogin(), subject, content, false, true);
    }
}
