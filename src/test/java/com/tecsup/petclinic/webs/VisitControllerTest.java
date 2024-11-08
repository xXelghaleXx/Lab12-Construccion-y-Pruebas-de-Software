package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.VisitTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVisits() throws Exception {

        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindVisitOK() throws Exception {

        int VISIT_ID = 1;
        int PET_ID = 7;
        String VISIT_DATE = "2010-03-04";
        String DESCRIPTION = "rabies shot";

        mockMvc.perform(get("/visits/" + VISIT_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(VISIT_ID)))
                .andExpect(jsonPath("$.petId", is(PET_ID)))
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    public void testFindVisitKO() throws Exception {

        mockMvc.perform(get("/visits/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVisit() throws Exception {

        int PET_ID = 8;
        String VISIT_DATE_STR = "2023-11-05"; // Nueva fecha
        String DESCRIPTION = "dental cleaning";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date VISIT_DATE = dateFormat.parse(VISIT_DATE_STR);

        VisitTO newVisitTO = new VisitTO();
        newVisitTO.setPetId(PET_ID);
        newVisitTO.setVisitDate(VISIT_DATE); // Asignamos el objeto Date
        newVisitTO.setDescription(DESCRIPTION);

        mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId", is(PET_ID)))
                .andExpect(jsonPath("$.visitDate", is(VISIT_DATE_STR)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }

    @Test
    public void testDeleteVisit() throws Exception {

        int PET_ID = 8;
        String VISIT_DATE_STR = "2023-11-01"; // Fecha existente
        String DESCRIPTION = "health checkup";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date VISIT_DATE = dateFormat.parse(VISIT_DATE_STR);

        VisitTO newVisitTO = new VisitTO();
        newVisitTO.setPetId(PET_ID);
        newVisitTO.setVisitDate(VISIT_DATE); // Asignamos el objeto Date
        newVisitTO.setDescription(DESCRIPTION);

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateVisit() throws Exception {

        int PET_ID = 7;
        String VISIT_DATE_STR = "2023-11-01"; // Fecha existente
        String DESCRIPTION = "follow-up visit";

        int UPDATED_PET_ID = 8;
        String UPDATED_VISIT_DATE_STR = "2023-11-06"; // Fecha actualizada
        String UPDATED_DESCRIPTION = "surgery";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date VISIT_DATE = dateFormat.parse(VISIT_DATE_STR);
        Date UPDATED_VISIT_DATE = dateFormat.parse(UPDATED_VISIT_DATE_STR);

        VisitTO newVisitTO = new VisitTO();
        newVisitTO.setPetId(PET_ID);
        newVisitTO.setVisitDate(VISIT_DATE); // Asignamos el objeto Date
        newVisitTO.setDescription(DESCRIPTION);

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        VisitTO updatedVisitTO = new VisitTO();
        updatedVisitTO.setId(id);
        updatedVisitTO.setPetId(UPDATED_PET_ID);
        updatedVisitTO.setVisitDate(UPDATED_VISIT_DATE); // Asignamos la nueva fecha como Date
        updatedVisitTO.setDescription(UPDATED_DESCRIPTION);

        mockMvc.perform(put("/visits/" + id)
                        .content(om.writeValueAsString(updatedVisitTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/visits/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.petId", is(UPDATED_PET_ID)))
                .andExpect(jsonPath("$.visitDate", is(UPDATED_VISIT_DATE_STR)))
                .andExpect(jsonPath("$.description", is(UPDATED_DESCRIPTION)));

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }
}
