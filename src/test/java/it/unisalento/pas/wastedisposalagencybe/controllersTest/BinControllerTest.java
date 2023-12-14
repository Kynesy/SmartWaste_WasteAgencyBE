package it.unisalento.pas.wastedisposalagencybe.controllersTest;

import com.nimbusds.jose.shaded.gson.Gson;
import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.dto.BinDTO;
import it.unisalento.pas.wastedisposalagencybe.dto.ContainerFactory;
import it.unisalento.pas.wastedisposalagencybe.dto.IContainerFactory;
import it.unisalento.pas.wastedisposalagencybe.dto.WasteType;
import it.unisalento.pas.wastedisposalagencybe.services.IBinService;
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
public class BinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBinService binService;

    private final IContainerFactory containerFactory = new ContainerFactory();

    @Test
    void createBinTest_Success() throws Exception {
        BinDTO binDTO = (BinDTO) containerFactory.getContainerType(WasteType.SORTED_UNSORTED);
        binDTO.setId("mockID");
        Gson gson = new Gson();
        String json = gson.toJson(binDTO);

        when(binService.createBin(any(Bin.class))).thenReturn(0);

        mockMvc.perform(post("/api/bin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Bin created successfully\"}"));
    }

    @Test
    void createBinTest_Failure() throws Exception {
        BinDTO binDTO = (BinDTO) containerFactory.getContainerType(WasteType.SORTED_UNSORTED);
        binDTO.setId("mockID");
        Gson gson = new Gson();
        String json = gson.toJson(binDTO);

        when(binService.createBin(any(Bin.class))).thenReturn(1);

        mockMvc.perform(post("/api/bin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"message\": \"Bin creation failed\"}"));
    }

    @Test
    void updateBinTest_Success() throws Exception {
        BinDTO binDTO = (BinDTO) containerFactory.getContainerType(WasteType.SORTED_UNSORTED);
        binDTO.setId("mockID");
        Gson gson = new Gson();
        String json = gson.toJson(binDTO);

        when(binService.updateBin(any(Bin.class))).thenReturn(0);

        mockMvc.perform(post("/api/bin/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Bin updated successfully\"}"));
    }

    @Test
    void updateBinTest_Failure() throws Exception {
        BinDTO binDTO = (BinDTO) containerFactory.getContainerType(WasteType.SORTED_UNSORTED);
        binDTO.setId("mockID");
        Gson gson = new Gson();
        String json = gson.toJson(binDTO);

        when(binService.updateBin(any(Bin.class))).thenReturn(1);

        mockMvc.perform(post("/api/bin/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"message\": \"Bin update failed\"}"));
    }

    @Test
    void deleteBinTest_Success() throws Exception {
        String binID = "mockID";
        when(binService.deleteBinByID(binID)).thenReturn(0);

        mockMvc.perform(delete("/api/bin/delete/{binID}", binID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"Bin deleted successfully\"}"));
    }

    @Test
    void deleteBinTest_Failure() throws Exception {
        String binID = "mockID";
        when(binService.deleteBinByID(binID)).thenReturn(1);

        mockMvc.perform(delete("/api/bin/delete/{binID}", binID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"message\": \"Bin deletion failed\"}"));
    }

    @Test
    void getBinTest_BinExists() throws Exception {
        String binID = "mockID";
        Bin bin = new Bin();
        bin.setId(binID);

        when(binService.getBinbyID(binID)).thenReturn(bin);

        mockMvc.perform(get("/api/bin/get/{binID}", binID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("mockID"));
    }

    @Test
    void getBinTest_BinDoesNotExist() throws Exception {
        String binID = "mockID";
        when(binService.getBinbyID(binID)).thenReturn(null);

        mockMvc.perform(get("/api/bin/get/{binID}", binID)
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBinsTest() throws Exception {
        ArrayList<Bin> binList = new ArrayList<>();
        Bin bin = new Bin();
        bin.setId("mockID");
        binList.add(bin);

        when(binService.getAllBins()).thenReturn(binList);

        mockMvc.perform(get("/api/bin/get/all")
                        .with(user("operator").authorities(new SimpleGrantedAuthority("ROLE_OPERATOR"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("mockID"));
    }
}
