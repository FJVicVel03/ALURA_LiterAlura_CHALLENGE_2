package com.aluradesafios.literalura.repository;

import com.aluradesafios.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
