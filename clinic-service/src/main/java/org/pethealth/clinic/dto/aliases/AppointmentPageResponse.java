package org.pethealth.clinic.dto.aliases;

import io.swagger.v3.oas.annotations.media.Schema;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

@Schema(name = "AppointmentPageResponse")
public class AppointmentPageResponse  extends PagedModel<AppointmentResponse> {
    public AppointmentPageResponse(Page<AppointmentResponse> page) {
        super(page);
    }
}
