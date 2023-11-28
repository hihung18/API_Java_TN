package com.example.api_java.controller;

import com.example.api_java.service.IBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public interface IBaseController<D, ID, S extends IBaseService<D, ID>> {
    S getService();

    @GetMapping("/{id}")
    default D get1(@PathVariable ID id) {
        return getService().findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    default D insert(@Valid @RequestBody D d) {
        return getService().save(d);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    default D update(@PathVariable ID id, @Valid @RequestBody D d) {
        return getService().update(id, d);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    default D delete(@PathVariable ID id) {
        return getService().delete(id);
    }
}
