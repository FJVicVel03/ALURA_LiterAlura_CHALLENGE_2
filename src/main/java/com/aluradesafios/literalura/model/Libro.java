package com.aluradesafios.literalura.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;
    private String idioma;
    private Integer numeroDescargas;

    public Libro(DatosLibros dLibros) {
        this.titulo = dLibros.titulo();
        this.idioma = dLibros.idiomas().get(0);
        this.numeroDescargas = dLibros.numeroDeDescargas();
        Optional<DatosAutor> autor = dLibros.autor().stream().findFirst();
        if (autor.isPresent()) {
            this.autor = new Autor(autor.get().nombre(), autor.get().fechaNacimiento(), autor.get().fechaDefuncion());

        }else{
            System.out.println("No se ha encontrado autor para el libro: " + this.titulo);
        }

    }
    public Libro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return
                "Titulo de Libro: " + titulo + '\'' +
                " Autor: " + autor + '\'' +
                " Idioma: " + idioma + '\'' +
                " Descargas del libro: " + numeroDescargas + '\'';
    }
}
