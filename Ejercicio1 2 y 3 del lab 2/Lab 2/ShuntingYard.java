import java.io.*;
import java.util.*;

public class ShuntingYard {

    public static void main(String[] args) {
        // Lista para almacenar las expresiones regulares
        List<String> regexList = new ArrayList<>();

        // Leer expresiones regulares desde el archivo
        try (BufferedReader br = new BufferedReader(new FileReader("expresiones.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                regexList.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Procesar cada expresión regular
        for (String regex : regexList) {
            try {
                List<String> postfix = shuntingYard(regex);
                System.out.println("Regex: " + regex);
                System.out.println("Postfix: " + postfix);
                System.out.println();
            } catch (Exception e) {
                System.out.println("Error processing regex: " + regex);
                e.printStackTrace();
            }
        }
    }

    public static List<String> shuntingYard(String expression) {
        // Define precedencias para operadores
        Map<Character, Integer> precedence = new HashMap<>();
        precedence.put('|', 1); // OR
        precedence.put('.', 2); // Concatenacion
        precedence.put('*', 3); // Kleene
        precedence.put('+', 3); // Cerradura positiva
        precedence.put('?', 3); // Cerradura opcional

        List<String> output = new ArrayList<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder multiCharToken = new StringBuilder();

        System.out.println("Iniciando conversión de: " + expression);

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (token == '\\') {
                // Manejo de caracteres escapados
                i++;
                if (i < expression.length()) {
                    token = expression.charAt(i);
                    multiCharToken.append('\\').append(token);
                    output.add(multiCharToken.toString());
                    multiCharToken.setLength(0);
                    printStep(token, output, operators);
                }
            } else if (Character.isLetterOrDigit(token) || token == 'ε') {
                multiCharToken.append(token);
                if (i + 1 == expression.length() || !Character.isLetterOrDigit(expression.charAt(i + 1))
                        && expression.charAt(i + 1) != 'ε' && expression.charAt(i + 1) != '\\') {
                    output.add(multiCharToken.toString());
                    multiCharToken.setLength(0);
                    printStep(token, output, operators);
                }
            } else if (token == '(') { // -> Aqui simplemente podria haber agregado como un || token == '['
                operators.push(token);
                printStep(token, output, operators);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.add(operators.pop().toString());
                    printStep(token, output, operators);
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop(); // Eliminar '('
                }
                printStep(token, output, operators);
            } else {
                while (!operators.isEmpty()
                        && precedence.getOrDefault(operators.peek(), 0) >= precedence.getOrDefault(token, 0)) {
                    output.add(operators.pop().toString());
                    printStep(token, output, operators);
                }
                operators.push(token);
                printStep(token, output, operators);
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop().toString());
            printStep('-', output, operators); // '-' indica el final de la expresión
        }

        return output;
    }

    private static void printStep(char token, List<String> output, Stack<Character> operators) {
        System.out.println("Token: " + token);
        System.out.println("Output: " + output);
        System.out.println("Operators: " + operators);
        System.out.println();
    }
}
