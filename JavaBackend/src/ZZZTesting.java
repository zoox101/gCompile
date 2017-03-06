import java.net.MalformedURLException;

public class ZZZTesting {
	
	public static void main(String[] args) throws MalformedURLException {
		
		//System.out.println(Bash.run("echo testing12345")[1]);
		Bash.run("javac Test.java");
		System.out.println(Bash.run("ls")[0]);
		String[] io = Bash.run("java -cp class Test");
		//Bash.run("jar cfm Test.jar Manifest.txt *.class");
		//String[] io = Bash.run("java -cp Test.jar");
		
		System.out.println(io[1]);

		
	}

}
