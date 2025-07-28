package com.example.springcore.storage;

import com.example.springcore.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStorageImplTest {

    private InMemoryStorageImpl<Trainee> storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorageImpl<>();
    }

    @Test
    void testSaveNewTrainee() {
        Trainee trainee = Trainee.builder().firstName("John").lastName("Doe").build();
        Trainee saved = storage.save(trainee);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
    }

    @Test
    void testSave() {
        Trainee trainee = Trainee.builder().firstName("Alice").build();
        storage.save(trainee);

        Optional<Trainee> result = storage.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getFirstName());
    }

    @Test
    void testSaveWithExistingId() {
        Trainee trainee = Trainee.builder().firstName("Alice").build();
        storage.save(trainee);

        Trainee updated = Trainee.builder().id(1L).firstName("UpdatedName").build();
        storage.save(updated);

        Optional<Trainee> result = storage.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("UpdatedName", result.get().getFirstName());
    }

    @Test
    void testSaveWithNonExistingId() {
        Trainee trainee = Trainee.builder().id(100L).firstName("Name").build();
        Trainee result = storage.save(trainee);

        assertNotNull(result);
    }

    @Test
    void testFindAll() {
        storage.save(Trainee.builder().firstName("A").build());
        storage.save(Trainee.builder().firstName("B").build());

        List<Trainee> all = storage.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindById() {
        Trainee trainee = Trainee.builder().firstName("Jane").build();
        Trainee saved = storage.save(trainee);

        Optional<Trainee> found = storage.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());
    }

    @Test
    void testDeleteById() {
        Trainee trainee = Trainee.builder().firstName("ToDelete").build();
        Trainee saved = storage.save(trainee);

        Trainee deleted = storage.deleteById(saved.getId());
        assertEquals(saved.getId(), deleted.getId());

        Optional<Trainee> shouldBeEmpty = storage.findById(saved.getId());
        assertFalse(shouldBeEmpty.isPresent());
    }
}
