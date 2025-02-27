package com.banking.violetApple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private ObjectMapper objectMapper; // Converts Java objects to JSON

    private Fruit testFruit;

    @BeforeEach
    void setUp() {
        fruitRepository.deleteAll(); // Clear DB before each test

        testFruit = new Fruit();
        testFruit.setName("Mango");
        testFruit.setColor("Yellow");
        testFruit.setPrice(3.99);
        fruitRepository.save(testFruit);
    }

    // 🔹 1️⃣ Test GET All Fruits
    @Test
    void shouldReturnListOfFruits() throws Exception {
        mockMvc.perform(get("/fruits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(0))) // Ensure list is not empty
                .andExpect(jsonPath("$[0].name", is(testFruit.getName())));
    }

    // 🔹 2️⃣ Test GET Fruit by ID
    @Test
    void shouldReturnFruitById() throws Exception {
        mockMvc.perform(get("/fruits/{id}", testFruit.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testFruit.getName())));
    }

    // 🔹 3️⃣ Test POST (Create) Fruit
    @Test
    void shouldCreateNewFruit() throws Exception {
        Fruit newFruit = new Fruit();
        newFruit.setName("Apple");
        newFruit.setColor("Red");
        newFruit.setPrice(2.99);

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFruit))) // Convert Java Object to JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Apple")));
    }

    // 🔹 4️⃣ Test PUT (Update) Fruit
    @Test
    void shouldUpdateExistingFruit() throws Exception {
        testFruit.setPrice(4.49); // Update price

        mockMvc.perform(put("/fruits/{id}", testFruit.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFruit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(4.49)));
    }

    // 🔹 5️⃣ Test DELETE Fruit
    @Test
    void shouldDeleteFruit() throws Exception {
        mockMvc.perform(delete("/fruits/{id}", testFruit.getId()))
                .andExpect(status().isNoContent());

        // Ensure fruit is deleted
        Optional<Fruit> deletedFruit = fruitRepository.findById(testFruit.getId());
        assert (!deletedFruit.isPresent());
    }

    // 🔹 6️⃣ Test GET Fruit by ID (Not Found)
    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get("/fruits/{id}", 999)) // Non-existent ID
                .andExpect(status().isNotFound());
    }
}
