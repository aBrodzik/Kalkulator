

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

    /**
     *
     * @param equation równanie ktorego częśc trzeba uprościć
     * @param sign znak działania które nalezy wykonywać
     * @return uproszczone równanie z wykonanymi działaniami podanego znaku
     */
    public static String simplifySingle(String equation, char sign) {
        // 2+2+2
        String before, after;
        String componentX, componentY;
        int start = 0, end = 0, j;
        /*petla szukajaca indexu danego znaku i indeksu poczatku liczby po jego lewej i indexu konca liczby po jego prawej
          następnie wycinany są dwie liczby z prawej i lewej strony znaku
          wykoynwane jest działanie
          i wynik działania umieszczamy w miejsce tego znaku i liczb
        * */

        for (int i = 1; i < equation.length(); i++) {
            if (equation.charAt(i) == sign) {
                j = i - 1; // i jest indexem znaku
                while ( j > 0) {
                    if (Character.isDigit(equation.charAt(j)) || equation.charAt(j) == '.' ) {
                        j--;
                    }else{break;}
                }
                start = j;
                j = i+1;
                while ( j < equation.length()-1) {
                    if( Character.isDigit(equation.charAt(j)) || equation.charAt(j) == '.') {
                        j++;
                    }
                    else {break;}
                }
                end = j;

                /*omijamy problem z substringiem jak trafiamy na koniec stringa
                        1+1 jest czytane jako a=1 b=1
                        a 1+1+1 jako a=1 b=1+
                 */
                if(end+1 == equation.length()){
                    componentX = equation.substring(i + 1, end+1);
                    before = equation.substring(end+1);
                }
                else
                {
                    componentX = equation.substring(i + 1, end);
                    before = equation.substring(end);

                }
                after = equation.substring(0, start);
                componentY = equation.substring(start, i);
                Calculator calculator = new Calculator(Double.parseDouble(componentX), Double.parseDouble(componentY), sign);
                equation = after + calculator.result + before;

            }
        }
        return equation;
    }

    public static String simplify(String equation){

        String before, after;
        int counter=0;
        for(int i=0;i<equation.length();i++){ //pozbywamy się ciągów "-"
            if(equation.charAt(i)=='-'){
                counter++;
            }
            else{              // 012345678
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


    public static char extractSign( String string){

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
