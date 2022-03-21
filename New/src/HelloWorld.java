import java.util.Scanner;
public class HelloWorld {
	public static void main(String[] args) {
//		System.out.print("hello world");
//		//System.out.println();
//		System.out.println();
		
//		
		Scanner s = new Scanner(System.in);
		String str = s.next();
		char name = str.charAt(0);
		int m1 = s.nextInt();
		int m2 = s.nextInt();
		int m3 = s.nextInt();
		System.out.println(name);
		System.out.println((m1+m2+m3)/3);
	}
}
