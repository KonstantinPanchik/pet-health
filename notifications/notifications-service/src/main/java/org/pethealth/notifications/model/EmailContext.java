package org.pethealth.notifications.model;

import lombok.*;
import org.pethealth.notifications.model.enums.TemplateType;

import java.io.File;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailContext {

    private String from;
    private String to;
    private String subject;
    private TemplateType templateType;
    private List<File> attachments;
    private Map<String, Object> context;
}
