import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Scanner;

public class Program {

    public static char extractSign(@NotNull String string){

        if (string.indexOf('+')>0) {
            return string.toCharArray()[string.indexOf('+')];
        }
        else if (string.indexOf('-')>0) {
            return string.toCharArray()[string.indexOf('-')];
        }
        else if (string.indexOf('*')>0) {
            return string.toCharArray()[string.indexOf('*')];
        }
        else if (string.indexOf('/')>0) {
            return string.toCharArray()[string.indexOf('/')];
        }
        else if (string.indexOf('^')>0) {
            return string.toCharArray()[string.indexOf('^')];
        }
        return '?';
    }

    public static void main(String []args) throws IOException {

        String input;
        char sign;
        double x;
        double y;

        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("Podaj polecenie");
            input = sc.nextLine();
            if (input.equals("stop")){
                break;
            }
            sign = extractSign(input);
            x = Double.parseDouble(input.substring(0,input.indexOf(sign)));
            y = Double.parseDouble(input.substring(input.indexOf(sign)+1,input.length()));
            Calculator calculator= new Calculator(x,y,sign);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(calculator);

        }

    }
}
