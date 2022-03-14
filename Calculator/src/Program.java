import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

/*
 Na razie dziala dla main: tylko na dwóch liczbach i jednym znaku pomiędzy nimi

 Chcemy aby byl kompatybilny z z GUI, czyli bral string z rownaniem np. 3+5+2/5---4*7
 1.kazda parzysta liczbe minusow (pod rzad) zamienia na +, a nieparzyste na jeden -
 2.szuka "*" jeśli znajdzie to wydobywa liczbe na lewo i na prawo od "*" wykonuje dzialanie "*" dwoch liczb i na miejsce tych liczb i znaku wkleja wynik do stringa
 3.robi tak z kolejnymi znakami : "/", "+", "-".
    3.5. obsluga błedu jak zostanie jeden znak i jedna liczba (zakladamy że użytkownik blednie wpisal) (w czym + i - po lewej można nie są błedem, tylko * i /)
 4.jeśli nie znajdze już żadnego znaku to znaczy, że otrzymano wynik i można zwrócić wartość do GUI, aby ją wyświetlić

 ?.Można potem dodać nawiasy procenty, logarytmy, potrzebna będzie bardziej zaawansowana metoda sprawdzania syntaxu równania
*/

public class Program {


    public static @NotNull String simplifySingle(String equation, char sign) {
        // 2+2+2
        String before, after;
        int start = 0, end = 0, j;
        for (int i = 1; i < equation.length(); i++) {
            if (equation.charAt(i) == sign) {
                j = i - 1;
                while (Character.isDigit(equation.charAt(j)) || equation.charAt(j) == '.') {
                    if (j > 0) {
                        j--;
                    }else {break;}
                }
                start = j;
                j = i + 1;
                while (Character.isDigit(equation.charAt(j)) || equation.charAt(j) == '.') {
                    if (j < equation.length() - 1) {
                        j++;
                    }else {break;}
                }

                end = j;
                //tutaj jest problem :/
                String a = equation.substring(i + 1, end+1);
                String b = equation.substring(start, i);
                Calculator calculator = new Calculator(Double.parseDouble(equation.substring(start, i)), Double.parseDouble(equation.substring(i + 1, end+1)), sign);
                after = equation.substring(0, start);
                before = equation.substring(end);
                equation = after + calculator.result + before;

            }
        }
        return equation;
    }

    public static String simplify(@NotNull String equation){

        String before, after;
        int counter=0;
        for(int i=0;i<equation.length();i++){ //pozbywamy się ciągów "-"
            if(equation.charAt(i)=='-'){
                counter++;
            }
            else{                   // 012345678
                if(counter>1){ // 555---423
                    before=equation.substring(0,i-counter);
                    after=equation.substring(i);
                    if(counter%2==0){
                        equation=before+"+"+after;
                    }
                    else {
                        equation=before+"-"+after;
                    }
                }
                counter=0;
            }
        }

        counter=0;
        for(int i=0;i<equation.length();i++){ //pozbywamy się ciągów "+"
            if(equation.charAt(i)=='+'){
                counter++;
            }
            else{              // 012345678
                if(counter>1){ // 555+++423
                    before=equation.substring(0,i-counter);
                    after=equation.substring(i);
                        equation=before+"+"+after;
                    }
                counter=0;
                }

            }
        if(equation.charAt(0)=='+'){ //pozbywamy się "+" na początku
            equation = equation.substring(1);
        }
        equation = equation.replace("+-","-"); //pozbywamy się "+-" i "-+"
        equation = equation.replace("-+","-");

        while(equation.contains("*")){
            equation = simplifySingle(equation,'*');
        }
        while(equation.contains("/")){
            equation = simplifySingle(equation,'/');
        }
        while(equation.contains("+")){
            equation = simplifySingle(equation,'+');
        }
        while(equation.contains("-")){
            equation = simplifySingle(equation,'-');
        }

        return equation;
    }


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

    public static void main(String []args) {

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
            y = Double.parseDouble(input.substring(input.indexOf(sign)+1));
            Calculator calculator= new Calculator(x,y,sign);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(calculator);

        }

    }
}
