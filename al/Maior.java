import java.util.Scanner;
public class Maior{

public static double maior(double numA , double numB){
return (numA>numB)?numA:numB;
}
public static void main(String... args){
        Scanner sc = new Scanner(System.in);
        double curry = 0;
        for(int i = 0 ; i<3 ; i++){
          curry =  maior(curry,sc.nextDouble());
        }
        System.out.println(curry);

}



}
