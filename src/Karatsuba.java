import java.util.Scanner;

public class Karatsuba {

    private Scanner scanner;

    public Karatsuba() {
        scanner = new Scanner(System.in);

        System.out.print("Digite dois números binários separados por espaço: ");
        String input = scanner.nextLine();
        scanner.close();

        // divides the input line in 2 binary numbers
        String[] numbers = input.split(" ");
        if (numbers.length != 2) {
            System.out.println("Por favor, insira exatamente dois números binários separados por espaço.");
            return;
        }

        String num1 = numbers[0];
        String num2 = numbers[1];

        String result = karatsuba(num1, num2);
        System.out.println("Resultado: " + result);
    }

    private static String karatsuba(String x, String y) {
        int n = Math.max(x.length(), y.length());

        // Pad both numbers with zeros to make their lengths equal
        x = padLeftZeros(x, n);
        y = padLeftZeros(y, n);

        // Base case for recursion
        if (n == 1) {
            return Integer.toString((x.charAt(0) - '0') * (y.charAt(0) - '0'));
        }

        int m = n / 2;

        // Split the num 1 digit sequences into two halves
        String x1 = x.substring(0, n - m);
        String x0 = x.substring(n - m);

        // Split the num 2 digit sequences into two halves
        String y1 = y.substring(0, n - m);
        String y0 = y.substring(n - m);

        // 1100 1010

        // 11 00
        // 10 10
        // 1 0   -
        // 1 0

        // Recursive calls
        String z2 = karatsuba(x1, y1);
        String z0 = karatsuba(x0, y0);
        String z1 = karatsuba(addBinary(x1, x0), addBinary(y1, y0));

        // Combine the three products to get the final result
        z1 = subtractBinary(z1, addBinary(z2, z0));
        String result = addBinary(addBinary(shiftLeft(z2, 2 * m), shiftLeft(z1, m)), z0);

        return removeLeadingZeros(result);
    }

    private static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();

        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0 || j >= 0 || carry == 1) {
            carry += (i >= 0) ? a.charAt(i--) - '0' : 0;
            carry += (j >= 0) ? b.charAt(j--) - '0' : 0;

            result.append(carry % 2);
            carry /= 2;
        }

        return result.reverse().toString();
    }

    private static String subtractBinary(String a, String b) {
        StringBuilder result = new StringBuilder();

        int borrow = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0 || j >= 0) {
            int bitA = (i >= 0) ? a.charAt(i--) - '0' : 0;
            int bitB = (j >= 0) ? b.charAt(j--) - '0' : 0;

            bitA -= borrow;
            if (bitA < bitB) {
                bitA += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(bitA - bitB);
        }

        // Remove leading zeros
        while (result.length() > 1 && result.charAt(result.length() - 1) == '0') {
            result.setLength(result.length() - 1);
        }

        return result.reverse().toString();
    }

    private static String padLeftZeros(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    private static String shiftLeft(String s, int shift) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < shift; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    private static String removeLeadingZeros(String s) {
        int index = 0;
        while (index < s.length() - 1 && s.charAt(index) == '0') {
            index++;
        }
        return s.substring(index);
    }
}

