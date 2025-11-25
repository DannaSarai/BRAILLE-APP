
import static spark.Spark.*;
import com.google.gson.Gson;
/**
 * Clase principal que inicializa el servidor web y expone el servicio de traducción
 * a través de una API REST utilizando el framework Spark.
 */
public class Server {
    /**
     * Método principal que arranca el servidor, configura el puerto, CORS y define
     * el endpoint '/traducir'.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        BrailleTranslator translator = new BrailleTranslator();
        Gson gson = new Gson();

        port(8081);
        /**
         * Habilita el Cross-Origin Resource Sharing (CORS) para permitir
         * llamadas desde el frontend (index.html) que se ejecuta en el navegador.
         */
        // Habilitar CORS
        options("/*", (req, res) -> {
            String headers = req.headers("Access-Control-Request-Headers");
            if (headers != null) {
                res.header("Access-Control-Allow-Headers", headers);
            }

            String method = req.headers("Access-Control-Request-Method");
            if (method != null) {
                res.header("Access-Control-Allow-Methods", method);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });

        /**
         * Define el Endpoint REST para la traducción.
         * Recibe el texto en el cuerpo de la solicitud HTTP POST y devuelve el braille como JSON.
         * * URL: POST /traducir
         */
        post("/traducir", (req, res) -> {
            res.type("application/json");

            String texto = req.body();
            String braille = translator.textToBraille(texto);

            return gson.toJson(braille);
        });

        System.out.println("✅ Servidor iniciado en http://localhost:8081");
    }
}