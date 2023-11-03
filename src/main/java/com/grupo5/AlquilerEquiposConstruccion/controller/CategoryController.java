package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) throws NotFoundException {
        Optional<CategoryDTO> categorySearch = categoryService.getCategoryById(id);
        if(categorySearch.isPresent()) {
            return ResponseEntity.ok(categorySearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) throws BadRequestException {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO category) throws Exception{
        Optional<CategoryDTO> categorySearch = categoryService.getCategoryById(category.getId());
        if(categorySearch.isPresent()){
            return ResponseEntity.ok(categoryService.updateCategory(category, category.getId()));
        } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id: " + category.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) throws NotFoundException {
        if(categoryService.getCategoryById(id).isPresent()){
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("The category with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id: " + id + " was not found.");
    }


}
