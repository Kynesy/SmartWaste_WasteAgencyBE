package it.unisalento.pas.wastedisposalagencybe.controllersTest;

import com.nimbusds.jose.shaded.gson.Gson;
import it.unisalento.pas.wastedisposalagencybe.configurations.SecurityConstants;
import it.unisalento.pas.wastedisposalagencybe.controllers.TrashController;
import it.unisalento.pas.wastedisposalagencybe.domains.Trash;
import it.unisalento.pas.wastedisposalagencybe.domains.WasteStatistics;
import it.unisalento.pas.wastedisposalagencybe.dto.TrashDTO;
import it.unisalento.pas.wastedisposalagencybe.dto.WasteStatisticsDTO;
import it.unisalento.pas.wastedisposalagencybe.services.ITrashService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TrashControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITrashService trashService;

    @Test
    void getTrashByUserIdTest() throws Exception {
        String userID = "mockUserID";
        ArrayList<Trash> trashList = new ArrayList<>();
        Trash trash = new Trash();
        trash.setId("mockID");
        trashList.add(trash);

        when(trashService.getTrashNotificationByUserID(userID)).thenReturn(trashList);

        mockMvc.perform(get("/api/trash/notifications/user/{userID}", userID)
                        .with(user("user").authorities(new SimpleGrantedAuthority(SecurityConstants.USER_ROLE_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("mockID"));
    }

    @Test
    void getStatisticsByUserIdTest() throws Exception {
        String userID = "mockUserID";
        int year = 2023;
        WasteStatistics statistics = new WasteStatistics();
        statistics.setUserId(userID);
        statistics.setYear(year);
        statistics.setTotalSortedWaste(200);
        statistics.setTotalUnsortedWaste(200);

        when(trashService.getUserStatistics(userID, year)).thenReturn(statistics);

        mockMvc.perform(get("/api/trash/statistics/user/{userID}/{year}", userID, year)
                        .with(user("user").authorities(new SimpleGrantedAuthority(SecurityConstants.USER_ROLE_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userID))
                .andExpect(jsonPath("$.year").value(year));
    }

    @Test
    void getStatisticsByUserIdListTest() throws Exception {
        ArrayList<String> userIdList = new ArrayList<>();
        userIdList.add("mockUserID");
        int year = 2023;
        ArrayList<WasteStatistics> statList = new ArrayList<>();
        WasteStatistics statistics = new WasteStatistics();
        statistics.setUserId("mockUserID");
        statistics.setYear(year);
        statList.add(statistics);

        when(trashService.getUserStatistics("mockUserID", year)).thenReturn(statistics);

        Gson gson = new Gson();
        String json = gson.toJson(userIdList);

        mockMvc.perform(post("/api/trash/statistics/user/all/{year}", year)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("admin").authorities(new SimpleGrantedAuthority(SecurityConstants.ADMIN_ROLE_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("mockUserID"))
                .andExpect(jsonPath("$[0].year").value(year));
    }

    @Test
    void getCityStatisticsTest() throws Exception {
        int year = 2023;
        WasteStatistics statistics = new WasteStatistics();
        statistics.setYear(year);

        when(trashService.getCityStatistics(year)).thenReturn(statistics);

        mockMvc.perform(get("/api/trash/statistics/city/{year}", year)
                        .with(user("admin").authorities(new SimpleGrantedAuthority(SecurityConstants.ADMIN_ROLE_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(year));
    }
}
