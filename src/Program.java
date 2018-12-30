import java.io.File;

public class Program {

	public static void main(String[] args) {
		
		File f = new File(args[0]);
		int delkaPole = 30_000;
		if (args.length == 2)
			delkaPole = Integer.parseInt(args[1]);
		try {
			CodeParser cp = new CodeParser(f, delkaPole);
			cp.execute();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}
	}

}
