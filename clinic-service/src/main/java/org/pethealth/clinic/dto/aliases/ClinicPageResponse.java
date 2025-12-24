package org.pethealth.clinic.dto.aliases;

import io.swagger.v3.oas.annotations.media.Schema;
import org.pethealth.clinic.dto.response.ClinicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

@Schema(name = "ClinicPageResponse")
public class ClinicPageResponse extends PagedModel<ClinicResponse> {
    public ClinicPageResponse(Page<ClinicResponse> page) {
        super(page);
    }
}
