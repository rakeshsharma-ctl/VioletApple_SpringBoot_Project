package com.banking.violetApple;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitRepository fruitRepository;

    public FruitController(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @PostMapping
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit) {
        Fruit savedFruit = fruitRepository.save(fruit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFruit);
    }

    // Get all fruits
    @GetMapping
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    // Get fruit by ID
    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        Optional<Fruit> fruit = fruitRepository.findById(id);
        return fruit.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update fruit by ID
    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id, @RequestBody Fruit fruitDetails) {
        return fruitRepository.findById(id).map(fruit -> {
            fruit.setName(fruitDetails.getName());
            fruit.setColor(fruitDetails.getColor());
            fruit.setPrice(fruitDetails.getPrice());
            return ResponseEntity.ok(fruitRepository.save(fruit));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        if (fruitRepository.existsById(id)) {
            fruitRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found if ID doesn't exist
        }
    }

   
}

