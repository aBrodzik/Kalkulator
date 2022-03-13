public class Calculator {

    double x;
    double y;
    char sign;
    double result;

    public Calculator(double x, double y, char sign) {
        this.x = x;
        this.y = y;
        this.sign = sign;
        this.result = calculate();
    }
    double calculate(){

        if (this.sign=='+'){
            return this.x + this.y;
        }
        else if (this.sign=='-'){
            return this.x - this.y;
        }
        else if (this.sign=='*'){
            return this.x * this.y;
        }
        else if (this.sign=='/'){
            return this.x / this.y;
        }
        else if (this.sign=='^'){
            return  myPow(this.x, this.y);
        }
        else
            return 0;
    }

    double myPow(double x, double y){
        y = (int) y;
        if (y==0){
            return 1;
        }
        double result = 1;
        for (int i=0; i<y; i++){
            result *= x;
        }
        return result;
    }

    @Override
    public String toString() {
        return x +""+ sign + ""+ y + "=" + result;
    }
}

