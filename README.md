# ALURA_LiterAlura_CHALLENGE_2

El Challenge 2 llamado "LiterAlura" fue hecho con Java y Springboot, este proyecto hace uso de una api ("https://gutendex.com/books/").

Estos datos obtenidos por la API son procesadors y enviados a una base de datos hecha con Postgres SQL, la cual tiene de nombre "literalura", en esta base de datos se encuentran las tablas mapeadas en java, las cuales son, autores y libros.

El funcionamiento es sencillo, se consume la API, los datos obtenidos son procesados en clases y records para posteriormente ser almacenados en la base de datos.

El programa incluye unas funcionalidades específicas para ver cierto contenido o buscar específicamente un libro, ya sea por nombre, o por el idioma.

Empezando por la primera opción, que funciona de la siguiente forma:

![11.png](assets/1.1.png)
![12.png](assets/1.2.png)

La segunda opción se encarga de mostrar los libros que ya fueron almacenados dentro de la base de datos.

![21.png](assets/2.1.png?t=1720386992198)


![22.png](assets/2.2.png?t=1720387000298)


La tercera opción se encarga de buscar y mostrar los autores existentes dentro de la base de datos.

![31.png](assets/3.1.png)

![32.png](assets/3.2.png)

La opción número 4 nos permite ingresar un año, y el programa se encargará de buscar entre todos los años de nacimiento y defunción de los autores, si este número está, si es el caso, mostrará a los autores que coinciden con esta petición. Está hecha a base de una Derived Query.

![41.png](assets/4.1.png)

La quinta y última opción, nos permite filtrar los libros por idioma.

![51.png](assets/5.1.png)

![52.png](assets/5.2.png)

![53.png](assets/5.3.png)

En la base de datos se vería así el llamado de libros:

![61.png](assets/6.1.png)

y el llamado de autores registrados en la base de datos, así:

![62.png](assets/6.2.png)


#AluraLATAM #G6
