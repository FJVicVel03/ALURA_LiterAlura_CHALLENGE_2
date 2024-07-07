package com.aluradesafios.literalura.principal;

import com.aluradesafios.literalura.model.Autor;
import com.aluradesafios.literalura.model.Datos;
import com.aluradesafios.literalura.model.DatosLibros;
import com.aluradesafios.literalura.model.Libro;
import com.aluradesafios.literalura.repository.AutorRepository;
import com.aluradesafios.literalura.repository.LibroRepository;
import com.aluradesafios.literalura.service.ConsumoAPI;
import com.aluradesafios.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";

    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {

        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        var opcion = -1;
        while(opcion != 0) {
            var menu = """
                    1 - Buscar libros por título
                    2 - Mostrar Libros registrados
                    3 - Mostrar Autores registrados
                    4 - Mostrar autores vivos en determinado año
                    5 - Mostrar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    try{
                        busquedaLibros();
                    }catch (Exception e){
                        System.out.println("Error al buscar el libro");
                    }
                    break;
                case 2:
                        getLibros();
                    break;
                case 3:
                        getAutores();
                    break;
                case 4:
                        getAutoresEnDeterminadosAnios();
                    break;
                case 5:
                        getLibrosPorIdioma();
                    break;
            }
        }
    }

    private void getLibrosPorIdioma() {
        System.out.println("Por favor escribe el idioma que desees buscar:");
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se pudo encontrar un libro en el idioma: " + idioma);
        } else {
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void getAutoresEnDeterminadosAnios() {
        System.out.println("Por favor escribe el año que desees buscar:");
        var anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autors = autorRepository.findByFechaNacimientoLessThanEqualAndFechaDefuncionGreaterThanEqual(anio, anio);
        if (autors.isEmpty()) {
            System.out.println("No se pudo encontrar a un autor que haya nacido entre el año:  " + anio);
        } else {
            for (Autor autor : autors) {
                System.out.println(autor.toString());
            }
        }
    }


    private void busquedaLibros() {
        //Buscando libros por nombre
        System.out.println("Por favor escribe el nombre del libro que desees buscar:");
        var tituloLibro = teclado.nextLine();
        var jsonLibro = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(jsonLibro, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.results().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: \n" + libroBuscado.get());
            Libro libroVerificado = libroRepository.findByTituloContainsIgnoreCase(libroBuscado.get().titulo());
            if (libroVerificado == null) {
                System.out.println("Libro no encontrado en la base de datos, ¿Deseas guardarlo? (S/N)");
                var respuesta = teclado.nextLine();
                if (respuesta.equalsIgnoreCase("S")) {
                    Libro libro = new Libro(libroBuscado.get());
                    libroRepository.save(libro);
                    System.out.println("Libro guardado exitosamente");
                }
            } else {
                System.out.println("Libro no encontrado");
            }
        }
    }

    private void getLibros(){
        List<Libro> libros = libroRepository.findAll();
        try{
            if(libros.isEmpty()){
                System.out.println("No hay libros registrados");
            }else{
                for(Libro libro : libros){
                    System.out.println(libro.toString());
                }
            }
        }catch (Exception e){
            System.out.println("Error al mostrar los libros");
        }
    }

    private void getAutores() {
        List<Libro> libros = libroRepository.findAll();
        List<String> autores = libros.stream()
                .map(libro -> libro.getAutor().getNombre())
                .distinct()
                .collect(Collectors.toList());
        try{
            if(autores.isEmpty()){
                System.out.println("No hay autores registrados");
            }else{
                for(String autor : autores){
                    System.out.println("\n3Autor: " + autor + "\n" + "Libros: " + libros.stream()
                            .filter(libro -> libro.getAutor().getNombre().equals(autor))
                            .map(Libro::getTitulo)
                            .collect(Collectors.toList()));
                }
            }
        }catch (Exception e){
            System.out.println("Error al mostrar los autores");
        }
    }
}
