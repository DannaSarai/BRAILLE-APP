import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BrailleTranslatorTest {

    private final BrailleTranslator translator = new BrailleTranslator();

    @Test
    @DisplayName("CP-001: Conversión básica de letras minúsculas")
    void testLetrasBasicas() {
        String resultado = translator.textToBraille("hola");
        assertEquals("⠓⠕⠇⠁", resultado);
    }

    @Test
    @DisplayName("CP-002: Conversión de vocales acentuadas")
    void testVocalesAcentuadas() {
        String resultado = translator.textToBraille("café");
        assertEquals("⠉⠁⠋⠮", resultado);
    }

    @Test
    @DisplayName("CP-003: Conversión de números con prefijo numérico")
    void testNumeros() {
        String resultado = translator.textToBraille("123");
        assertEquals("⠼⠁⠃⠉", resultado);
    }

    @Test
    @DisplayName("CP-004: Conversión de texto mixto (letras, números y espacios)")
    void testTextoMixto() {
        // ERROR CORREGIDO: Eliminadas las etiquetas 'text:' y 'message:'
        String resultado = translator.textToBraille("hola 123 mundo");
        String esperado = "⠓⠕⠇⠁ ⠼⠁⠃⠉ ⠍⠥⠝⠙⠕";
        assertEquals(esperado, resultado, "El texto mixto debe manejar letras, números y espacios correctamente");
    }

    @Test
    @DisplayName("CP-005: Conversión de signos de puntuación")
    void testPuntuacion() {
        String resultado = translator.textToBraille("¿hola?");
        assertEquals("⠢⠓⠕⠇⠁⠢", resultado);
    }

    @Test
    @DisplayName("CP-006: Manejo de entrada vacía o nula")
    void testEntradaVaciaNula() {
        assertEquals("", translator.textToBraille(""));
        assertEquals("", translator.textToBraille(null));
        assertEquals("", translator.textToBraille("   "));
    }

    @Test
    @DisplayName("CP-007: Traducción inversa (Braille a Español)")
    void testTraduccionInversa() {
        String resultado = translator.brailleToText("⠨⠓⠕⠇⠁ ⠼⠁");
        assertEquals("Hola 1", resultado);
    }

    @Test
    @DisplayName("CP-008: Números con guiones")
    void testNumerosGuiones() {
        assertEquals("⠼⠁⠤⠼⠃", translator.textToBraille("1-2"));
    }

    @Test
    @DisplayName("CP-009: Mayúsculas por bloques (EPN)")
    void testMayusculasBloque() {
        assertEquals("⠨⠑⠏⠝", translator.textToBraille("EPN"));
    }
}
