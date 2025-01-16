package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Role;
import pl.io.emergency.entity.TemplateEntity;
import pl.io.emergency.repository.TemplateRepository;

import java.util.Map;

@Service
public class TemplateService {
    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public TemplateEntity getTemplateById(long id) {
        return templateRepository.findById(id);
    }

    public String renderTemplate(String template, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }
}
