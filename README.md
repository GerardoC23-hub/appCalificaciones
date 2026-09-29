# Gestión de Calificaciones - TDA Estudiante

Autor: Gerardo Cruz Hernández

## Descripción

Aplicación móvil para Android, desarrollada en Java, que registra el nombre de un estudiante y su calificación en cada una de las 7 materias de su plan de estudios.
Las calificaciones se guardan en un arreglo de tamaño fijo dentro de un Tipo de Dato Abstracto (TDA) llamado Estudiante. Las materias son fijas, por lo que la pantalla 
solo pide la calificación de cada una.

Al presionar el botón Calcular, la aplicación obtiene automáticamente:
- El promedio general.
- La calificación más alta.
- La calificación más baja, calculada mediante un método recursivo (indica la materia y su nota).
- La cantidad de materias aprobadas y reprobadas.
- El estado final del semestre: Aprobado, o Pendiente cuando hay materias reprobadas (en ese caso se indican cuáles debe aprobar).
- Si el estudiante es becado, el porcentaje de beca y el monto a pagar de colegiatura.
-Una materia se considera aprobada con una calificación de 7 o más.

## Conceptos aplicados
- TDA y encapsulamiento: la clase Estudiante mantiene sus atributos privados (nombre y arreglo de calificaciones) y solo se accede a ellos mediante métodos.
- Herencia: la clase EstudianteBecado hereda de Estudiante y agrega la colegiatura y el cálculo de la beca.
- Constructores con validaciones: el nombre no puede estar vacío ni contener números o símbolos, las calificaciones deben estar entre 0 y 10 y la colegiatura
-  no puede ser negativa.
- Recursividad: el método BuscarPosicionNotaMinimaRecursiva tiene como caso base la última posición del arreglo y avanza revisando el resto del arreglo (índice + 1).
- Patrón MVP (Modelo, Vista, Presentador): la Vista solo captura y muestra datos, el Presentador convierte y da formato, y el Modelo contiene toda la lógica.

## Estructura del proyecto
- Modelo: `Estudiante` y `EstudianteBecado`.
- Presentador: `EstudiantePresenter`.
- Vista: `MainActivityView`, junto con el diseño `activity_main.xml`.

La clase Estudiante contiene 10 métodos:

- Acceso (5): ObtenerMaterias, GetNombre, SetNombre, GetCalificacion y RegistrarCalificacion.
- Dominio (5): CalcularPromedio, BuscarNotaMaxima, BuscarPosicionNotaMinimaRecursiva, ContarReprobadas y EstaReprobada.

## Cálculo de la beca
El porcentaje de beca no se captura: se calcula con el promedio del estudiante usando la siguiente tabla:

| Promedio     | Porcentaje de beca |
|--------------|--------------------|
| 9.5 o más    | 100%               |
| 9.0 o más    | 75%                |
| 8.5 o más    | 50%                |
| 8.0 o más    | 25%                |
| Menos de 8.0 | Sin beca           |

Si el estudiante tiene alguna materia reprobada, no recibe beca sin importar su promedio. El monto a pagar se obtiene restando a la colegiatura el descuento
correspondiente al porcentaje de beca.

## Requisitos para ejecutar la app

- Android Studio (versión reciente, Flamingo o superior recomendado).
- JDK 11 o superior (Android Studio ya lo incluye).
- Un emulador de Android configurado, o un celular físico con el modo desarrollador y la depuración USB activados.

## Cómo ejecutar la aplicación paso a paso

1. Clonar el repositorio, o bien descargar el proyecto como ZIP desde el botón verde "Code" de este repositorio y descomprimirlo.
2. Abrir el proyecto en Android Studio:
   - Abrir Android Studio.
   - Seleccionar File, luego Open.
   - Buscar la carpeta donde se clonó o descomprimió el proyecto y seleccionarla.
3. Esperar la sincronización de Gradle:
   - Android Studio descargará automáticamente las dependencias necesarias (se ve una barra de progreso en la parte inferior).
   - Si aparece una barra amarilla que pide "Sync Now", hacer clic en ella.
4. Conectar un dispositivo o iniciar un emulador:
   - Con un celular físico: conectarlo por USB con la depuración USB activada.
   - Sin celular: abrir Device Manager y crear o iniciar un emulador (AVD).
5. Ejecutar la app:
   - Presionar el botón verde Run en la barra superior, o usar Shift + F10.
   - La app se instalará y se abrirá automáticamente en el dispositivo o emulador.

## Cómo usar la aplicación

1. Escribir el nombre del estudiante. Solo se aceptan letras, espacios, apóstrofo y guion.
2. Escribir la calificación de cada una de las 7 materias (valores entre 0 y 10).
3. Si el estudiante cuenta con beca, marcar la casilla "¿Es becado?" e ingresar el monto de la colegiatura. El porcentaje de beca no se escribe,
   la app lo calcula con el promedio.
4. Presionar el botón CALCULAR.
5. La app mostrará automáticamente:
   - El estado final del semestre.
   - La calificación de cada materia, indicando si está aprobada o reprobada.
   - El nombre del estudiante.
   - El promedio.
   - La nota más alta.
   - La nota más baja (calculada de forma recursiva).
   - La cantidad de materias aprobadas y reprobadas.
   - El estado de la beca y el monto a pagar (si el estudiante es becado, de lo contrario indica que no aplica).

## Validaciones y mensajes de error
Si algún campo de calificación queda vacío, contiene un valor que no es un número válido, está fuera del rango de 0 a 10,
o si el nombre o la colegiatura no son válidos, la app muestra un mensaje de error (Toast) y no realiza los cálculos hasta que se corrija el dato.
Mientras haya un error, se ocultan los resultados anteriores para no mostrar información desactualizada.
