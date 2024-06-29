# **Proyecto API - Mars Photo Viewer y Music Player**

***Descripción***

Este proyecto integra dos funcionalidades principales: visualización de fotos del planeta Marte utilizando la API de la NASA y reproducción de música local.

La aplicación permite buscar y mostrar fotos de Marte según filtros seleccionados, así como reproducir una lista de reproducción de música mientras se navega por las imágenes.

***Funcionalidades***

Visualización de Fotos de Marte:

- Selecciona un rover (Curiosity, Opportunity, Spirit), una cámara y un rango de fechas para buscar fotos del planeta Marte.
- Muestra información detallada de cada foto, incluyendo fecha terrestre, cámara utilizada, estado del rover, y más.
  
***Reproductor de Música:***

- Permite reproducir una lista de reproducción de música mientras se utiliza la aplicación.
- Configura la lista de reproducción con archivos de música local.

***Requisitos***

- JDK 16 o superior instalado.
- IDE compatible con Java (como IntelliJ IDEA, Eclipse, etc.).
- Conexión a internet para acceder a la API de la NASA.

***Configuración Inicial***

Descarga de JARs:

Asegúrate de descargar los siguientes archivos JAR necesarios para el proyecto:

- json-1.1.jar
- json-20180813.jar
- rest-1.1-tests.jar
- rest-1.1.jar
- jlayer-1.0.1.4.jar
- junit-3.8.2.jar

Link: https://uceedu-my.sharepoint.com/:f:/g/personal/beloya_uce_edu_ec/EvGUtdLFhfVMhQ-ntD28rVQB1OmKRuW4bR3zMBFdlVHI3A?e=l4s3ar

***Configuración de la Música:***

Coloca tus archivos de música en una carpeta accesible desde el proyecto.

Actualiza la lista de reproducción en la clase MusicPlayer.java con las rutas absolutas de tus archivos de música.}

List<String> playlist = Arrays.asList(

    "C:\\ruta\\a\\tu\\cancion1.mp3",
    "C:\\ruta\\a\\tu\\cancion2.mp3"   
    
);

***Ejecución***

Ejecución desde el IDE:

- Abre el proyecto en tu IDE y ejecuta la clase MarsPhotoViewer.java.

- Se abrirá la interfaz gráfica de la aplicación.

***Uso de la Aplicación:***

- Selecciona el rover, la cámara, el sol y las fechas de inicio y fin para buscar fotos de Marte.

- Haz clic en "Fetch Photos" para obtener y mostrar las fotos correspondientes.

- Utiliza el botón "Play Music" para reproducir la lista de música configurada.

***Notas Adicionales***

- Asegúrate de tener configurado correctamente el entorno y las rutas de los archivos para evitar errores de ejecución.

- Si encuentras problemas, revisa la consola del IDE para obtener mensajes de error detallados que te ayuden a diagnosticar y solucionar problemas.

***Autor***

Este proyecto fue desarrollado por Bryan Loya.

¡Disfruta explorando Marte y escuchando tu música favorita con el Proyecto API - Mars Photo Viewer y Music Player!

# ***Licencia***

Distribuido bajo la licencia MIT. Ver LICENSE para más información.
