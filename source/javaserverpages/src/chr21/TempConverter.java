package chr21;

public class TempConverter {

    public static void main (String[] args) throws Exception {
        System.out.println("20 C is " + toFahrenheit(20) + " F");
        System.out.println("68 F is " + toCelsius(68) + " C");
    }
    
    public static double toCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
