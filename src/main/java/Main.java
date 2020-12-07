import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
    private static Logger LOGGER = Logger.getLogger("[Main]");

    public static void main(String[] args) {
        if(args == null || args.length == 0){
            /* run client */
            LOGGER.log(Level.ALL, "Running client");
            client.Runner.main(args);
        }else if(args.length == 1) {
            if (args[0].equals("client")) {
                /* run client */
                LOGGER.log(Level.ALL, "Running client");
                client.Runner.main(args);
            } else if (args[0].equals("server")) {
                /* run server */
                LOGGER.log(Level.ALL, "Running server");
                server.Runner srv = new server.Runner(true);
                srv.run();
            }
        }
    }
}
