import java.util.Scanner;
/**
 * Clase principal para ejecutar la aplicación Transcriptor a Braille en modo consola (CLI).
 * Permite al usuario introducir texto mediante la terminal.
 */
public class Main {
    /**
     * El método de entrada principal que inicializa el traductor y gestiona
     * la interacción con el usuario a través de la consola.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        BrailleTranslator translator = new BrailleTranslator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================================");
        System.out.println("     Transcriptor a Braille - Consola         ");
        System.out.println("==============================================");
        System.out.print("Ingrese el texto en español: ");

        String input = scanner.nextLine();

        if (input.isBlank()) {
            System.out.println("\nNo has ingresado ningún texto.");
        } else {
            String braille = translator.textToBraille(input);

            System.out.println("\nTexto original : " + input);
            System.out.println("Texto en braille:");
            System.out.println(braille);
        }

        scanner.close();
        System.out.println("\nConversión finalizada.");
    }
}
