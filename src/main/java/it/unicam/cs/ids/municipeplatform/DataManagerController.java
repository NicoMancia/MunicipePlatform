package it.unicam.cs.ids.municipeplatform;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DataManagerController<T, ID> {

    @PostMapping
    ResponseEntity<?> create(@RequestBody T entity);

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable ID id);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable ID id);
}
