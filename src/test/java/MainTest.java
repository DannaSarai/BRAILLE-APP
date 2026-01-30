import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    // Guardamos los flujos originales para restaurarlos después de cada prueba
    private final InputStream systemInOriginal = System.in;
    private final PrintStream systemOutOriginal = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    public void setUp() {
        // Preparamos la captura de la salida por consola
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // Restauramos los flujos originales del sistema
        System.setIn(systemInOriginal);
        System.setOut(systemOutOriginal);
    }

    /**
     * Prueba el flujo feliz: El usuario ingresa una palabra válida.
     */
    @Test
    public void testMain_flujoExitoso() {
        // Simulamos que el usuario escribe "hola" y presiona Enter
        provideInput("hola\n");

        // Ejecutamos el método main
        Main.main(new String[]{});

        String salida = outputStreamCaptor.toString();

        // Verificamos que se muestre el texto original y la traducción correcta
        assertTrue(salida.contains("Texto original : hola"));
        assertTrue(salida.contains("⠓⠕⠇⠁")); // Traducción de "hola"
        assertTrue(salida.contains("Conversión finalizada."));
    }

    /**
     * Prueba el caso donde el usuario no ingresa nada (solo espacios o Enter).
     */
    @Test
    public void testMain_entradaVacia() {
        // Simulamos que el usuario presiona Enter sin escribir nada
        provideInput("\n");

        Main.main(new String[]{});

        String salida = outputStreamCaptor.toString();

        // Verificamos que el programa detecte la entrada vacía
        assertTrue(salida.contains("No has ingresado ningún texto."));
    }

    /**
     * Prueba una frase compleja con números para verificar la integración.
     */
    @Test
    public void testMain_fraseConNumeros() {
        provideInput("A1\n");

        Main.main(new String[]{});

        String salida = outputStreamCaptor.toString();

        // Verificamos que se apliquen las reglas de mayúsculas (⠨) y números (⠼)
        assertTrue(salida.contains("⠨⠁⠼⠁")); 
    }

    // Método auxiliar para inyectar texto al teclado (System.in)
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}