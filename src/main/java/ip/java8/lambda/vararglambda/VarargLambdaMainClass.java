package ip.java8.lambda.vararglambda;

public class VarargLambdaMainClass {
	
	public static void main(String[] args) {
		System.out.println("Sum Value : " +calculate((int ...a) -> a[0] + a[1] + a[2] + a[3], 10, 20, 3, 4));
		System.out.println("Product Value : " +calculate((int ...a) -> a[0] * a[1] * a[2] * a[3], 10, 20, 3, 4));
	}
	
	public static int calculate(Calculator calc, int ...values){
		return calc.calculate(values);
	}

}
