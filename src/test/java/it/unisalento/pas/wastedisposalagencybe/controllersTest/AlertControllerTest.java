package it.unisalento.pas.wastedisposalagencybe.controllersTest;

import it.unisalento.pas.wastedisposalagencybe.domains.Alert;
import it.unisalento.pas.wastedisposalagencybe.services.IAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAlertService alertService;

    @Test
    void getAllCapacityAlertsTest() throws Exception {
        ArrayList<Alert> alertList = new ArrayList<>();
        Alert alert = new Alert();
        alert.setId("mockAlertID");
        alert.setTimestamp("2023-10-06T10:00:00Z");
        alert.setBinId("mockBinID");
        alert.setAlertLevel(2);
        alertList.add(alert);

        when(alertService.getAllAlerts()).thenReturn(alertList);

        mockMvc.perform(get("/api/alert/get/all")
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("mockAlertID"))
                .andExpect(jsonPath("$[0].timestamp").value("2023-10-06T10:00:00Z"))
                .andExpect(jsonPath("$[0].binId").value("mockBinID"))
                .andExpect(jsonPath("$[0].alertLevel").value(2));
    }
}
