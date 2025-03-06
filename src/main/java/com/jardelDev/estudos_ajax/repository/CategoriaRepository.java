package com.jardelDev.estudos_ajax.repository;

import com.jardelDev.estudos_ajax.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
