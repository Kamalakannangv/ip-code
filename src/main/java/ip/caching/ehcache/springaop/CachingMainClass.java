package ip.caching.ehcache.springaop;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CachingMainClass {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/config/ip/caching/ehcache/springaop/caching-sample-context.xml");
		ICalculator calculator = (ICalculator)context.getBean("calculator");
		int num = 10;
		List<Integer> fiboacciSeries = calculator.calculateFibonacciSeries(num);	
		System.out.println("Fibonacci Series for " + num + " : " + fiboacciSeries);
		fiboacciSeries = calculator.calculateFibonacciSeries(num);	
		System.out.println("Fibonacci Series for " + num + " : " + fiboacciSeries);
		num = 15;
		fiboacciSeries = calculator.calculateFibonacciSeries(num);	
		System.out.println("Fibonacci Series for " + num + " : " + fiboacciSeries);
		num = 10;
		fiboacciSeries = calculator.calculateFibonacciSeries(num);	
		System.out.println("Fibonacci Series for " + num + " : " + fiboacciSeries);
		num = 15;
		fiboacciSeries = calculator.calculateFibonacciSeries(num);	
		System.out.println("Fibonacci Series for " + num + " : " + fiboacciSeries);
	}
	
}
