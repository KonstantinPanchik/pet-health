package org.pethealth.clinic.mapper;

import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.stereotype.Component;

@Component
public class InitiatorMapper {

    public InitiatorInfo toInitiatorInfo(JwtUserPrincipal principal) {
        InitiatorInfo initiatorInfo = new InitiatorInfo();
        initiatorInfo.setInitiatorId(principal.getSub());
        initiatorInfo.setInitiatorEmail(principal.getEmail());
        initiatorInfo.setInitiatorName(principal.getFirstName());
        initiatorInfo.setInitiatorLastName(principal.getLastName());
        return initiatorInfo;
    }
}
