package it.unicam.cs.ids.municipeplatform;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseCrudController<T, ID> {

    @PostMapping
    ResponseEntity<?> create(@RequestBody T entity);

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable ID id);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody T entity, @PathVariable ID id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable ID id);
}
