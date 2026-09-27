La app permite registrar el nombre y 5 calificaciones de un estudiante (guardadas en un arreglo de tamaño fijo), y calcular automáticamente:

- El promedio general
- La calificación más alta
- La calificación más baja (mediante un **método recursivo**)
- La cantidad de calificaciones aprobatorias
- El estado final (Aprobado / Reprobado)
- Si el estudiante es becado, el monto a pagar de colegiatura según su porcentaje de beca

Requisitos para ejecutar la app

- **Android Studio** (versión reciente, Flamingo o superior recomendado)
- **JDK 11** o superior (Android Studio ya lo incluye)
- Un emulador de Android configurado, o un celular físico con **modo desarrollador** y **depuración USB** activados

Cómo ejecutar la aplicación paso a paso
1. **Clonar el repositorio**
O bien, descarga el proyecto como ZIP desde el botón verde **"Code"** de este repositorio y descomprímelo.

2. **Abrir el proyecto en Android Studio**
   - Abre Android Studio.
   - Selecciona **File → Open**.
   - Navega hasta la carpeta donde clonaste/descomprimiste el proyecto y selecciónala.

3. **Esperar la sincronización de Gradle**
   - Android Studio descargará automáticamente las dependencias necesarias (barra de progreso en la parte inferior).
   - Si aparece una barra amarilla pidiendo "Sync Now", haz clic en ella.

4. **Conectar un dispositivo o iniciar un emulador**
   - Con un celular físico: conéctalo por USB con la depuración USB activada.
   - Sin celular: ve a **Device Manager** (icono de celular en la barra lateral) y crea/inicia un emulador (AVD).

5. **Ejecutar la app**
   - Presiona el botón verde  (Run) en la barra superior, o `Shift + F10`.
   - La app se instalará y abrirá automáticamente en el dispositivo/emulador.

---

## Cómo usar la aplicación

1. Escribe el **nombre del estudiante**.
2. Ingresa sus **5 calificaciones** (valores entre 0 y 10).
3. Si el estudiante cuenta con beca, marca la casilla **"¿Es becado?"** e ingresa el **porcentaje de beca** y el **monto de la colegiatura**.
4. Presiona el botón **"Calcular"**.
5. La app mostrará automáticamente:
   - Nombre del estudiante
   - Promedio
   - Nota más alta
   - Nota más baja (calculada de forma recursiva)
   - Cantidad de calificaciones aprobadas
   - Estado final (Aprobado/Reprobado)
   - Monto a pagar (si aplica beca)
   - Si algún campo de calificación queda vacío o no es un número válido, la app mostrará un mensaje de error (Toast) y no realizará los cálculos hasta corregirlo.
