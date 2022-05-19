package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.AdminTokenDto;
import com.kacyper.carrentalbackend.facade.AdminTokenFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminTokenController.class)
public class AdminTokenControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminTokenFacade adminTokenFacade;

    @DisplayName("Does Token Exist?")
    @Test
    public void testExistingToken() throws Exception {
        //Given
        when(adminTokenFacade.existsByToken("123")).thenReturn(true);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/token/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$", is(true)));
    }

    @DisplayName("Creating New Admin Token")
    @Test
    public void testCreateAdminToken() throws Exception {
        //Given
        AdminTokenDto adminTokenDto = AdminTokenDto.builder()
                .id(1L)
                .token("123")
                .build();
        when(adminTokenFacade.createToken()).thenReturn(adminTokenDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.token", is("123")));

    }

    @DisplayName("Deleting Admin Token")
    @Test
    public void testDeleteTokens() throws Exception {
        //Given
        doNothing().when(adminTokenFacade).deleteAllTokens();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/token"))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

}
