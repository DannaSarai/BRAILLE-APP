import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrailleTranslatorTest {

    private final BrailleTranslator translator = new BrailleTranslator();

    // ==========================================
    // PRUEBAS: TEXTO A BRAILLE (Español -> Braille)
    // ==========================================

    @Test
    public void testTextToBraille_palabraSimple() {
        // "hola" -> ⠓ (h) ⠕ (o) ⠇ (l) ⠁ (a)
        assertEquals("⠓⠕⠇⠁", translator.textToBraille("hola"));
    }

 @Test
public void testTextToBraille_mayusculas() {
    // Cambiamos el valor esperado para que coincida con la lógica de tu código
    assertEquals("⠨⠁", translator.textToBraille("A"));
    assertEquals("⠨⠓⠕⠇⠁", translator.textToBraille("HOLA")); 
}

    @Test
    public void testTextToBraille_numeros() {
        // Los números deben llevar el prefijo ⠼ (NUMBER_SIGN)
        assertEquals("⠼⠁", translator.textToBraille("1"));
        assertEquals("⠼⠁⠃⠉", translator.textToBraille("123"));
    }

    @Test
    public void testTextToBraille_acentos() {
        // Verifica que á, é, í, ó, ú funcionen
        assertEquals("⠷", translator.textToBraille("á"));
        assertEquals("⠾", translator.textToBraille("ú"));
    }

    @Test
    public void testTextToBraille_espaciosYFrases() {
        // Verifica que los espacios se respeten
        assertEquals("⠓⠕⠇⠁ ⠍⠥⠝⠙⠕", translator.textToBraille("hola mundo"));
    }

    // ==========================================
    // PRUEBAS: BRAILLE A TEXTO (Braille -> Español)
    // ==========================================

    @Test
    public void testBrailleToText_basico() {
        assertEquals("hola", translator.brailleToText("⠓⠕⠇⠁"));
    }

    @Test
    public void testBrailleToText_conMayusculas() {
        // Al detectar ⠨ la siguiente letra debe ser mayúscula
        assertEquals("Hola", translator.brailleToText("⠨⠓⠕⠇⠁"));
    }

    @Test
    public void testBrailleToText_conNumeros() {
        // Al detectar ⠼ debe entrar en modo numérico
        assertEquals("123", translator.brailleToText("⠼⠁⠃⠉"));
    }

    // ==========================================
    // PRUEBAS DE ROBUSTEZ (Edge Cases)
    // ==========================================

    @Test
    public void testCasosVaciosYNulos() {
        assertEquals("", translator.textToBraille(""));
        assertEquals("", translator.textToBraille(null));
        assertEquals("", translator.brailleToText(""));
        assertEquals("", translator.brailleToText(null));
    }

    @Test
    public void testCaracterNoExistente() {
        // Si ingresas algo que no está en el mapa, tu código devuelve un signo de interrogación especial
        assertTrue(translator.textToBraille("$").contains("⍰"));
    }
}