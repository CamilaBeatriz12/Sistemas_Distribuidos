
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Cliente {
    
    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    private int porta=1025;

    public void read() throws IOException, ParseException {
        socket = new Socket("127.0.0.1", porta);

        entrada = new DataInputStream(socket.getInputStream());
        saida = new DataOutputStream(socket.getOutputStream());

        JSONObject obj = new JSONObject();

        obj.put("method", "read");
        obj.put("args", "");

        saida.writeUTF(obj.toJSONString());
        String resultado = entrada.readUTF();

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(resultado);

        System.out.println(response.get("result").toString());

        socket.close();
    }

    public void write(String msg) throws IOException, ParseException {
        socket = new Socket("127.0.0.1", porta);

        entrada = new DataInputStream(socket.getInputStream());
        saida = new DataOutputStream(socket.getOutputStream());

        JSONObject obj = new JSONObject();

        obj.put("method", "write");
        obj.put("args", msg);

        saida.writeUTF(obj.toJSONString());
        String resultado = entrada.readUTF();

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(resultado);

        System.out.println(response.get("result").toString());

        socket.close();
    }

    public void shutdownServer() throws IOException{

        socket = new Socket("127.0.0.1", porta);

        entrada = new DataInputStream(socket.getInputStream());
        saida = new DataOutputStream(socket.getOutputStream());

        JSONObject obj = new JSONObject();

        obj.put("method", "shutdown");
        obj.put("args", "");

        saida.writeUTF(obj.toJSONString());
        String resultado = entrada.readUTF();
        
        System.out.println(resultado);

        socket.close();
    }

    public void displayMenu() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 para Leitura");
            System.out.println("2 para Escrita");
            System.out.println("3 para Sair");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    try {
                        read();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "2":
                    System.out.println("Digite uma mensagem:");
                    String message = scanner.nextLine();
                    System.out.println("Resultado: " + message);

                    write(message);

                    break;
                case "3":
                    System.out.println("Saindooo!");
                    return;
                default:
                    System.out.println("Inválido!");
                    break;
            }
        }
    }
    
    public void iniciar(){
    	System.out.println("Cliente iniciado na porta: "+porta);
    	
    	try {
            displayMenu();
            shutdownServer();
            
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Cliente().iniciar();
    }
    
}
