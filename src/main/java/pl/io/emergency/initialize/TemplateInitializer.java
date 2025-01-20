package pl.io.emergency.initialize;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.io.emergency.entity.TemplateEntity;
import pl.io.emergency.repository.TemplateRepository;

@Component
public class TemplateInitializer {

    private final TemplateRepository templateRepository;

    public TemplateInitializer(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void initTemplates() {
        TemplateEntity template1 = new TemplateEntity("help1", "pl", "Twoje zgłoszenie pomocy zostało przyjęte!",
                "Witaj {firstName} {lastName},\n Dziękujemy za zgłoszenie pomocy!\n Otrzymaliśmy Twoje zgłoszenie, a poniżej znajdują się szczegóły:\n Opis: {description}\n Unikalny kod zgłoszenia: {uniqueCode}\n Status Twojego zgłos" +
                        "zenia: {status}\n Pozdrawiamy, Zespół Pomocy Humanitarnej");
        TemplateEntity template2 = new TemplateEntity("help1", "en", "Your help request has been received!", "Hello {firstName} {lastName},\n" +
                "Thank you for submitting your help request!\n" +
                "We have received your request, and below are the details:\n" +
                "Description: {description}\n" +
                "Unique request code: {uniqueCode}\n" +
                "Status of your request: {status}\n" +
                "Best regards,  \n" +
                "The Humanitarian Aid Team");
        TemplateEntity template3 = new TemplateEntity("help2", "pl", "Twoje zgłoszenie pomocy zostało zaktualizowane!", "Witaj {firstName} {lastName},\n" +
                "Twoje zgłoszenie zostało zaktualizowane poniżej znajdują się szczegóły:\n" +
                "Opis: {description}\n" +
                "Unikalny kod zgłoszenia: {uniqueCode}\n" +
                "Status Twojego zgłoszenia: {status}\n" +
                "Pozdrawiamy, Zespół Pomocy Humanitarnej");
        TemplateEntity template4 = new TemplateEntity("help2", "en", "Your help request has been updated!", "Hello {firstName} {lastName},\n" +
                "Your help request has been updated. Below are the details:\n" +
                "Description: {description}\n" +
                "Unique request code: {uniqueCode}\n" +
                "Status of your request: {status}\n" +
                "Best regards,  \n" +
                "The Humanitarian Aid Team");
        TemplateEntity template5 = new TemplateEntity("vol1", "pl", "Zaproszenie do dołączenia do akcji pomocowej", "Witaj wolontariuszu!\n" +
                "Zapraszamy cię do dołączenia do akcji pomocowej. \n" +
                "W celu dołączenia do akcji kliknij w ten link - >  {eventLink}");
        TemplateEntity template6 = new TemplateEntity("vol1", "en", "Invitation to Join the Relief Effort", "Hello Volunteer!\n" +
                "\n" +
                "We invite you to join the relief effort.\n" +
                "To participate in the event, please click on this link -> {eventLink}");

        templateRepository.insertTemplate(template1);
        templateRepository.insertTemplate(template2);
        templateRepository.insertTemplate(template3);
        templateRepository.insertTemplate(template4);
        templateRepository.insertTemplate(template5);
        templateRepository.insertTemplate(template6);
    }
}
