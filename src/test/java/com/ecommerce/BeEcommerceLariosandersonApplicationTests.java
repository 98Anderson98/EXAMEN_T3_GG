package com.ecommerce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeEcommerceLariosandersonApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private String login(String user, String pass) throws Exception {
        String body = mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode json = mapper.readTree(body);
        return json.get("token").asText();
    }

    @Test
    void sinTokenDevuelve401() throws Exception {
        mvc.perform(get("/api/productos")).andExpect(status().isUnauthorized());
    }

    @Test
    void loginIncorrectoDevuelve401() throws Exception {
        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"anderson\",\"password\":\"mala\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void crudCompletoConJwt() throws Exception {
        String token = "Bearer " + login("anderson", "anderson123");
        String producto = """
                {"nombre":"Tablet","categoria":"Tablets","precioCompra":500.00,"precioVenta":700.00,
                 "stock":5,"marca":"Samsung","codigoBarras":"TEST-001","fechaIngreso":"2026-11-11"}
                """;

        String creado = mvc.perform(post("/api/productos").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON).content(producto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tablet"))
                .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(creado).get("id").asLong();

        mvc.perform(get("/api/productos").header("Authorization", token))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").exists());
        mvc.perform(get("/api/productos/" + id).header("Authorization", token))
                .andExpect(status().isOk());
        mvc.perform(get("/api/productos/categoria/tablets").header("Authorization", token))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].codigoBarras").value("TEST-001"));
        mvc.perform(get("/api/productos/buscar").param("nombre", "tab").header("Authorization", token))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));

        mvc.perform(put("/api/productos/" + id).header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON).content(producto.replace("\"stock\":5", "\"stock\":9")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.stock").value(9));

        mvc.perform(delete("/api/productos/" + id).header("Authorization", token))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/productos/" + id).header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    void usuarioConRolUserNoPuedeCrear() throws Exception {
        String token = "Bearer " + login("invitado", "invitado123");
        mvc.perform(get("/api/productos").header("Authorization", token)).andExpect(status().isOk());
        mvc.perform(post("/api/productos").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void swaggerEsPublico() throws Exception {
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
    }
}
