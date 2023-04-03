import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Principal_v0 {

	public final static Path path = Paths.get("src\\fortune-br.txt");
	private int NUM_FORTUNES = 0;

	public class FileReader {

		public int countFortunes() throws FileNotFoundException {

			int lineCount = 0;

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();

				}// fim while

				System.out.println(lineCount);
			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
			return lineCount;
		}

		public void parser(HashMap<Integer, String> hm) throws FileNotFoundException {

			InputStream is = new BufferedInputStream(new FileInputStream(path.toString()));
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				int lineCount = 0;

				String line = "";
				while (!(line == null)) {

					if (line.equals("%"))
						lineCount++;

					line = br.readLine();
					StringBuffer fortune = new StringBuffer();
					while (!(line == null) && !line.equals("%")) {
						fortune.append(line + "\n");
						line = br.readLine();
						// System.out.print(lineCount + ".");
					}

					hm.put(lineCount, fortune.toString());
					System.out.println(fortune.toString());

					System.out.println(lineCount);
				}// fim while

			} catch (IOException e) {
				System.out.println("SHOW: Excecao na leitura do arquivo.");
			}
		}

		public String read(HashMap<Integer, String> hm) throws FileNotFoundException {

			SecureRandom aleatorio = new SecureRandom();
			int key = aleatorio.nextInt(NUM_FORTUNES); //recebe o id da fortuna
			String conteudo = hm.get(key); //conteudo da fortuna

			System.out.println("ID (" + key + "): " + conteudo);

			return conteudo;
		}

		public void write(HashMap<Integer, String> hm) throws FileNotFoundException {

			String conteudo = read(hm);
			try{
				FileWriter escrita = new FileWriter(path.toString(), true);
				escrita.write("\n%\n" + conteudo);
				escrita.close();
				System.out.println("Fortuna: " + path.toString());
				parser(hm);
				NUM_FORTUNES = countFortunes();
			} catch (IOException e){
				System.out.println("Erro na escrita: " + e.getMessage());
			}
		}
	}

	public void iniciar() {

		FileReader fr = new FileReader();
		try {
			int qtFt = 3;
			NUM_FORTUNES = fr.countFortunes();
			HashMap hm = new HashMap<Integer, String>();
			fr.parser(hm);

			for(int i = 0; i < qtFt; i++){
				fr.read(hm);
			}
			
			fr.write(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Principal_v0().iniciar();
	}

}
