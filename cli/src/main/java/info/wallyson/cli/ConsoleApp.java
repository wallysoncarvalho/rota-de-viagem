package info.wallyson.cli;

public class ConsoleApp {

  public static void main(String[] args) {
    var filePath = args[0];

    System.out.println("\n");
    System.out.println("CLI INTERFACE. WRITE 'exit' to leave.");
    System.out.println("GETTING GRAPH FROM THE FILE: " + filePath);
    System.out.println("\n");

    ConsoleInterface.start(filePath);
  }
}
