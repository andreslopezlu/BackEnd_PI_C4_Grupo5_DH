package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Integer id);
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Integer id);
}
