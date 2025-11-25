import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado de convertir texto en español a braille.
 * Esta clase contiene la lógica de negocio central y el mapeo de caracteres.
 * {@code Versión para consola — Iteración 1 del proyecto.}
 */
public class BrailleTranslator {
    /** Mapeo de caracteres del español (minúsculas, acentos, dígitos, puntuación) a su símbolo Braille. */
    private final Map<Character, String> brailleMap;
    /** Símbolo de prefijo utilizado para indicar que los siguientes caracteres son dígitos. */
    private final String NUMBER_SIGN = "⠼";

    /**
     * Constructor que inicializa el mapa Braille cargando todas las series de caracteres
     * (letras, acentos, puntuación y dígitos) necesarias para la traducción.
     */
    public BrailleTranslator() {
        brailleMap = new HashMap<>();
        initLetters();
        initAccents();
        initPunctuation();
        initDigits();
    }
    /**
     * Inicializa las letras básicas (a-z) y las letras adicionales del braille español (ñ, ü).
     */
    private void initLetters() {
        // Serie 1 (a–j)
        brailleMap.put('a', "⠁");
        brailleMap.put('b', "⠃");
        brailleMap.put('c', "⠉");
        brailleMap.put('d', "⠙");
        brailleMap.put('e', "⠑");
        brailleMap.put('f', "⠋");
        brailleMap.put('g', "⠛");
        brailleMap.put('h', "⠓");
        brailleMap.put('i', "⠊");
        brailleMap.put('j', "⠚");

        // Serie 2 (k–t) = serie 1 + punto 3
        brailleMap.put('k', "⠅");
        brailleMap.put('l', "⠇");
        brailleMap.put('m', "⠍");
        brailleMap.put('n', "⠝");
        brailleMap.put('o', "⠕");
        brailleMap.put('p', "⠏");
        brailleMap.put('q', "⠟");
        brailleMap.put('r', "⠗");
        brailleMap.put('s', "⠎");
        brailleMap.put('t', "⠞");

        // Serie 3 (u, v, x, y, z) = serie 1 + puntos 3 y 6
        brailleMap.put('u', "⠥");
        brailleMap.put('v', "⠧");
        brailleMap.put('x', "⠭");
        brailleMap.put('y', "⠽");
        brailleMap.put('z', "⠵");

        // Letras adicionales del braille español
        brailleMap.put('w', "⠺"); // por influencia del inglés
        brailleMap.put('ñ', "⠻");
    }
    /**
     * Inicializa los caracteres con acento agudo (á, é, í, ó, ú) y el diptongo 'ü'.
     */
    private void initAccents() {
        brailleMap.put('á', "⠷");
        brailleMap.put('é', "⠮");
        brailleMap.put('í', "⠌");
        brailleMap.put('ó', "⠬");
        brailleMap.put('ú', "⠾");
        brailleMap.put('ü', "⠳");
    }
    /**
     * Inicializa los símbolos de puntuación más comunes del braille español.
     */
    private void initPunctuation() {
        brailleMap.put('.', "⠄");
        brailleMap.put(',', "⠂");
        brailleMap.put('?', "⠢");
        brailleMap.put('¡', "⠖");
        brailleMap.put('!', "⠖");
        brailleMap.put('¿', "⠢");
        brailleMap.put(':', "⠒");
        brailleMap.put(';', "⠆");
        brailleMap.put('(', "⠣");
        brailleMap.put(')', "⠜");
        brailleMap.put('"', "⠦");
        brailleMap.put('-', "⠤");
        brailleMap.put('+', "⠖");
        brailleMap.put('x', "⠦");   // multiplicación
        brailleMap.put('÷', "⠲");
        brailleMap.put('=', "⠶");// división
    }

    /**
     * Inicializa los dígitos (0-9) que se mapean a las primeras diez letras de la serie a-j.
     */
    private void initDigits() {
        // Los dígitos usan la serie a–j con signo numeral
        brailleMap.put('0', "⠚");
        brailleMap.put('1', "⠁");
        brailleMap.put('2', "⠃");
        brailleMap.put('3', "⠉");
        brailleMap.put('4', "⠙");
        brailleMap.put('5', "⠑");
        brailleMap.put('6', "⠋");
        brailleMap.put('7', "⠛");
        brailleMap.put('8', "⠓");
        brailleMap.put('9', "⠊");
    }

    /**
     * Convierte una cadena de texto en español a su representación en código Braille.
     * El método maneja la conversión a minúsculas, el espaciado, y la inserción
     * del signo numérico '⠼' para secuencias de dígitos.
     *
     * @param text El texto en español a traducir. (Cadena de texto limpia, es decir,eliminar saltos de linea.)
     * @return La cadena de texto traducida a código Braille. Retorna una cadena vacía si la entrada es nula o vacía.
     */
    public String textToBraille(String text) {
        if (text == null || text.isBlank()) return "";

        text = text.replaceAll("[\\n\\r]", " "); 
        StringBuilder result = new StringBuilder();
        boolean insideNumber = false;

        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isDigit(c)) {
                if (!insideNumber) {
                    result.append(NUMBER_SIGN);
                    insideNumber = true;
                }
                result.append(getBrailleChar(c));
            } else {
                insideNumber = false;
                if (c == ' ') {
                    result.append(" ");
                } else {
                    result.append(getBrailleChar(c));
                }
            }
        }
        return result.toString();
    }
    /**
     * Busca el símbolo Braille correspondiente a un carácter dado en el mapa.
     *
     * @param c El carácter (letra, dígito o puntuación) a buscar.
     * @return La representación en Braille (String) o '⍰' si el carácter no se encuentra en el mapa.
     */
    private String getBrailleChar(char c) {
        return brailleMap.getOrDefault(c, "⍰ signo no existente en el diccionario"); // placeholder si no existe
    }
}
