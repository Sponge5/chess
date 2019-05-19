public class Main {
    public static void main(String[] args) throws Exception {
        if(args == null || args.length == 0){
            /* run client */
            System.out.println("Running client");
            client.Runner.main(args);
        }else if(args.length == 1) {
            if (args[0].equals("client")) {
                /* run client */
                System.out.println("Running client");
                client.Runner.main(args);
            } else if (args[0].equals("server")) {
                /* run server */
                System.out.println("Running server");
                server.Runner srv = new server.Runner(8888, true);
                Integer port = srv.getPort();
                System.out.println("server port: " + port.toString());
                srv.run();
            }
        }
    }
}
