package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.CategoryService;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import com.grupo5.AlquilerEquiposConstruccion.utils.FileManager;
import com.grupo5.AlquilerEquiposConstruccion.utils.S3Config;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@MultipartConfig
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileManager fileManager;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3Config s3Config;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category, @RequestPart("file") MultipartFile file) throws BadRequestException, IOException {
        File convertedFile = fileManager.convertMultiPartFileToFile(file);
        String fileName = fileManager.generateFileName(file);
        s3Service.uploadFile(fileName, convertedFile);
        convertedFile.delete();
        String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
        category.setUrlImage(s3Url);
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO category, @RequestParam("file") MultipartFile file) throws Exception{
        Optional<CategoryDTO> categorySearch = categoryService.getCategoryById(category.getId());
        if(categorySearch.isPresent() && file==null){
            return ResponseEntity.ok(categoryService.updateCategory(category, category.getId()));
        } if (categorySearch.isPresent() && file!=null){
            File convertedFile = fileManager.convertMultiPartFileToFile(file);
            String fileName = fileManager.generateFileName(file);
            s3Service.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = s3Config.amazonS3().getUrl(bucketName, fileName).toString();
            category.setUrlImage(s3Url);
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
