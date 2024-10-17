import java.net.*; 
import java.io.*; 
import java.util.Scanner;

public class server { 
    public static void main(String[] args) throws IOException { 
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o endereço IP para escutar (ou pressione Enter para usar 127.0.0.1): ");
        String ipAddress = scanner.nextLine();
        if (ipAddress.isEmpty()) {
            ipAddress = "127.0.0.1"; // valor padrão
        }

        System.out.print("Digite a porta para escutar (ou pressione Enter para usar 10007): ");
        String portInput = scanner.nextLine();
        int port = 10007; // valor padrão
        if (!portInput.isEmpty()) {
            try {
                port = Integer.parseInt(portInput);
            } catch (NumberFormatException e) {
                System.err.println("Porta inválida, usando o padrão 10007.");
                port = 10007; // valor padrão em caso de erro
            }
        }

        ServerSocket serverSocket = null; 

        try { 
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(ipAddress)); 
        } catch (IOException e) { 
            System.err.println("Could not listen on port: " + port + ". " + e.getMessage()); 
            System.exit(1); 
        } 

        Socket clientSocket = null; 
        System.out.println("Waiting for connection.....");

        try { 
            clientSocket = serverSocket.accept(); 
        } catch (IOException e) { 
            System.err.println("Accept failed. " + e.getMessage()); 
            System.exit(1); 
        } 

        System.out.println("Connection successful");
        System.out.println("Waiting for input.....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 

        String inputLine; 

        while ((inputLine = in.readLine()) != null) { 
            System.out.println("Server: " + inputLine); 
            out.println(inputLine); 

            if (inputLine.toUpperCase().equals("BYE.")) 
                break; 
        } 

        out.close(); 
        in.close(); 
        clientSocket.close(); 
        serverSocket.close(); 
        scanner.close();
    } 
}
