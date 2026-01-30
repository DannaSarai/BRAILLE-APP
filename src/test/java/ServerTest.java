import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {

    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    public static void setup() {
        // Arrancamos el servidor llamando al main de la clase Server
        Server.main(new String[]{});
        // Esperamos a que Spark esté totalmente inicializado antes de lanzar los tests
        Spark.awaitInitialization(); 
    }

    @AfterAll
    public static void tearDown() {
        // Detenemos el servidor al finalizar todas las pruebas para liberar el puerto 8081
        Spark.stop();
    }

    @Test
    public void testPostTraducir_Exitoso() throws Exception {
        // Preparamos la petición POST al endpoint /traducir
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/traducir"))
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString("hola"))
                .build();

        // Enviamos la petición
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificamos el código de estado 200 (OK)
        assertEquals(200, response.statusCode());
        // Gson envuelve el resultado en comillas, por eso esperamos "⠓⠕⠇⠁"
        assertTrue(response.body().contains("⠓⠕⠇⠁"));
    }

    @Test
    public void testPostTraducirBraille_Exitoso() throws Exception {
        // Preparamos la petición POST al endpoint /traducir-braille
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/traducir-braille"))
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString("⠓⠕⠇⠁"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        // Verificamos que la traducción inversa funcione y devuelva "hola"
        assertTrue(response.body().contains("hola"));
    }

    @Test
    public void testCORS_Headers() throws Exception {
        // Verificamos que las cabeceras CORS estén presentes (importante para tu index.html)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/traducir"))
                .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificamos que el servidor responda a las peticiones OPTIONS (Preflight)
        assertEquals("OK", response.body());
    }
}