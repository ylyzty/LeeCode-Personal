package Others;

import java.math.BigDecimal;

public class BigDecimalTest {
    
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("0.9"); 
        
        System.out.println(a.subtract(b));
    }
    
}
