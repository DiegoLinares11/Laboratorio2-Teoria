import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class BalanceadorExpresiones {

    public static void main(String[] args) {
        String archivo = "expresiones.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Procesando expresión: " + linea);
                if (esBalanceada(linea)) {
                    System.out.println("La expresión " + linea + " está balanceada.");
                } else {
                    System.out.println("La expresión " + linea + " no está balanceada.");
                }
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean esBalanceada(String expresion) {
        Stack<Character> pila = new Stack<>();
        char[] simbolos = expresion.toCharArray();

        for (char simbolo : simbolos) {
            switch (simbolo) {
                case '(':
                case '[':
                case '{':
                    pila.push(simbolo);
                    System.out.println("Pila después de insertar: " + simbolo + ": " + pila);
                    break;
                case ')':
                    if (pila.isEmpty() || pila.pop() != '(') {
                        System.out
                                .println("Símbolo de cierre no coincidente: " + simbolo + " encontrado. Pila: " + pila);
                        return false;
                    }
                    System.out.println("Pila después de retirar: " + simbolo + ": " + pila);
                    break;
                case ']':
                    if (pila.isEmpty() || pila.pop() != '[') {
                        System.out
                                .println("Símbolo de cierre no coincidente: " + simbolo + " encontrado. Pila: " + pila);
                        return false;
                    }
                    System.out.println("Pila después de retirar: " + simbolo + ": " + pila);
                    break;
                case '}':
                    if (pila.isEmpty() || pila.pop() != '{') {
                        System.out
                                .println("Símbolo de cierre no coincidente: " + simbolo + " encontrado. Pila: " + pila);
                        return false;
                    }
                    System.out.println("Pila después de retirar: " + simbolo + ": " + pila);
                    break;
            }
        }

        if (!pila.isEmpty()) {
            System.out.println("Expresión no balanceada. Pila restante: " + pila);
            return false;
        }

        return true;
    }
}
