import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado de convertir texto en español a braille y viceversa.
 * Esta clase contiene la lógica de negocio central y el mapeo de caracteres.
 * {@code Versión para consola — Iteración 1 y 2 del proyecto.}
 */
public class BrailleTranslator {
    /** Mapeo de caracteres del español (minúsculas, acentos, dígitos, puntuación) a su símbolo Braille. */
    private final Map<Character, String> brailleMap;
    
    /** Mapeo de símbolos Braille a caracteres del español (minúsculas, acentos, dígitos, puntuación). */
    private final Map<String, Character> reverseBrailleMap;
    
    /** Símbolo de prefijo utilizado para indicar que los siguientes caracteres son dígitos. */
    private final String NUMBER_SIGN = "⠼";

    /** Símbolo de prefijo utilizado para indicar que los siguientes caracteres son mayúsculas. */
    private final String CAPITAL_SIGN = "⠨";

    /**
     * Crea un traductor e inicializa los diccionarios de conversión.
     *
     * El diccionario inverso se construye a partir del diccionario directo para garantizar consistencia
     * en ambas direcciones.
     */
    public BrailleTranslator() {
        brailleMap = new HashMap<>();
        initLetters();
        initAccents();
        initPunctuation();
        initDigits();

        reverseBrailleMap = new HashMap<>();
        for (Map.Entry<Character, String> e : brailleMap.entrySet()) {
            reverseBrailleMap.put(e.getValue(), e.getKey());
        }
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
        brailleMap.put('x', "⠦");
        brailleMap.put('÷', "⠲");
        brailleMap.put('=', "⠶");
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
     * El método realiza las siguientes operaciones:
     * Maneja letras mayúsculas anteponiendo el signo de mayúscula '⠨'.
     * Maneja la conversión a minúsculas, el espaciado, y la inserción
     * del signo numérico '⠼' para secuencias de dígitos.
     * Soporta números con guiones.
     *
     * @param text El texto en español a traducir. (Cadena de texto limpia, es decir,eliminar saltos de linea.)
     * @return La cadena de texto traducida a código Braille. Retorna una cadena vacía si la entrada es nula o vacía.
     */
    public String textToBraille(String text) {
        if (text == null || text.isBlank()) return "";

        text = text.replaceAll("[\\n\\r]", " ");
        StringBuilder result = new StringBuilder();
        boolean insideNumber = false;

        String[] tokens = text.split("((?<= )|(?= ))");

        for (String token : tokens) {

            if (token.equals(" ")) {
                result.append(" ");
                insideNumber = false;
                continue;
            }

            if (token.matches("[A-ZÁÉÍÓÚÜÑ]+")) {
                result.append(CAPITAL_SIGN);
                for (char c : token.toCharArray()) {
                    char lower = Character.toLowerCase(c);
                    result.append(getBrailleChar(lower));
                }
                insideNumber = false;
                continue;
            }
            
            if (token.matches("\\d+(?:-\\d+)+")) {
                String[] parts = token.split("-");
                for (int i = 0; i < parts.length; i++) {
                    String p = parts[i];
                    if (p.matches("\\d+")) {
                        result.append(NUMBER_SIGN);
                        for (char d : p.toCharArray()) {
                            result.append(getBrailleChar(d));
                        }
                    }
                    if (i < parts.length - 1) {
                        result.append("⠤");
                    }
                }
                insideNumber = false;
                continue;
            }
            
            for (char c : token.toCharArray()) {

                if (Character.isDigit(c)) {
                    if (!insideNumber) {
                        result.append(NUMBER_SIGN);
                        insideNumber = true;
                    }
                    result.append(getBrailleChar(c));
                    continue;
                }
                
                if (Character.isLetter(c) && Character.isUpperCase(c)) {
                    result.append(CAPITAL_SIGN);
                    result.append(getBrailleChar(Character.toLowerCase(c)));
                    insideNumber = false;
                    continue;
                }

                result.append(getBrailleChar(c));
                insideNumber = false;
            }
        }

        return result.toString();
    }

    /**
     * Convierte una cadena de texto en código Braille a su representación en español.
     * El método realiza las siguientes operaciones:
     * Maneja letras mayúsculas al detectar el signo de mayúscula '⠨'.
     * Maneja el modo numérico al detectar el signo numérico '⠼'.
     * Soporta números con guiones.
     *
     * @param braille La cadena de texto en código Braille a traducir.
     * @return La cadena de texto traducida al español. Retorna una cadena vacía si la entrada es nula o vacía.
     */
    public String brailleToText(String braille) {
        if (braille == null || braille.isBlank()) return "";

        StringBuilder out = new StringBuilder();
        boolean nextUpper = false;
        boolean numberMode = false;

        for (int i = 0; i < braille.length(); i++) {
            char ch = braille.charAt(i);

            if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') {
                out.append(ch);
                nextUpper = false;
                numberMode = false;
                continue;
            }

            String cell = String.valueOf(ch);

            if (cell.equals(CAPITAL_SIGN)) {
                nextUpper = true;
                continue;
            }

            if (cell.equals(NUMBER_SIGN)) {
                numberMode = true;
                continue;
            }

            Character decoded = reverseBrailleMap.get(cell);

            if (decoded == null) {
                out.append("�");
                continue;
            }

            if (numberMode) {
                char digit = letterToDigit(decoded);
                if (digit != 0) {
                    out.append(digit);
                    continue;
                } else {
                    numberMode = false;
                }
            }

            if (nextUpper) {
                out.append(Character.toUpperCase(decoded));
                nextUpper = false;
            } else {
                out.append(decoded);
            }
        }

        return out.toString();
    }

    /**
     * Convierte una letra (a-j) a su dígito correspondiente (1-0).
     *
     * @param c La letra a convertir.
     * @return El dígito correspondiente o 0 si la letra no es válida.
     */
    private char letterToDigit(char c) {
        c = Character.toLowerCase(c);
        return switch (c) {
            case 'a' -> '1';
            case 'b' -> '2';
            case 'c' -> '3';
            case 'd' -> '4';
            case 'e' -> '5';
            case 'f' -> '6';
            case 'g' -> '7';
            case 'h' -> '8';
            case 'i' -> '9';
            case 'j' -> '0';
            default -> 0;
        };
    }

    /**
     * Busca el símbolo Braille correspondiente a un carácter dado en el mapa.
     *
     * @param c El carácter (letra, dígito o puntuación) a buscar.
     * @return La representación en Braille (String) o '⍰' si el carácter no se encuentra en el mapa.
     */
    private String getBrailleChar(char c) {
        return brailleMap.getOrDefault(c, "⍰ signo no existente en el diccionario");
    }
}
