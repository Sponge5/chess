import javafx.stage.Stage;

public class Main {

    public static void main(String[] args) throws Exception {
        if(args == null || args.length == 0){
            /* run client */
            client.Runner.main(args);
        }else if(args.length == 1) {
            if (args[0].equals("client")) {
                /* run client */
                client.Runner.main(args);
            } else if (args[0].equals("server")) {
                /* run server */
                server.Runner server = new server.Runner();
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
