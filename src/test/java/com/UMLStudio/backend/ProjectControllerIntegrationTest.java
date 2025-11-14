// package com.UMLStudio.backend;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.UMLStudio.backend.dto.ProjectRequest;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.hamcrest.Matchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class ProjectControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     void createListAndGetProject() throws Exception {
//         ProjectRequest req = new ProjectRequest("Test Project", "A simple test project");

//         // Create
//         String createdJson = mockMvc.perform(post("/api/projects")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(req)))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.id", notNullValue()))
//                 .andExpect(jsonPath("$.name", is("Test Project")))
//                 .andReturn()
//                 .getResponse()
//                 .getContentAsString();

//         // Verify list contains it
//         mockMvc.perform(get("/api/projects"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
//                 .andExpect(jsonPath("$[0].name", notNullValue()));

//         // Extract id and GET
//         Long id = objectMapper.readTree(createdJson).get("id").asLong();

//         mockMvc.perform(get("/api/projects/" + id))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id", is(id.intValue())))
//                 .andExpect(jsonPath("$.name", is("Test Project")));
//     }
// }

