import java.io.*;
import java.net.*;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite o endereço IP do servidor (ou pressione Enter para usar 127.0.0.1): ");
        String serverHostname = scanner.nextLine();
        

        if (serverHostname.isEmpty()) {
            serverHostname = "127.0.0.1";
        }

        System.out.print("Digite a porta do servidor (ou pressione Enter para usar 10007): ");
        String portInput = scanner.nextLine();
        int port = 10007; 
        if (!portInput.isEmpty()) {
            try {
                port = Integer.parseInt(portInput);
            } catch (NumberFormatException e) {
                System.err.println("Porta inválida, usando o padrão 10007.");
                port = 10007; // valor padrão em caso de erro
            }
        }

        System.out.println("Tentando conectar ao host " + serverHostname + " na porta " + port + ".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Não conheço o host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Não consegui I/O para a conexão com: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.print("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
            System.out.print("input: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
        scanner.close();
    }
}
