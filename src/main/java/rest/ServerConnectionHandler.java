package rest;

public class ServerConnectionHandler {
    private static ServerConnectionHandler serverConnectionHandler = new ServerConnectionHandler();
    private Requester requester;

    private ServerConnectionHandler(){
        requester = new RequestHandler();

    }

    //Get the only object available
    public static ServerConnectionHandler getInstance(){
        if (serverConnectionHandler == null)
            serverConnectionHandler = new ServerConnectionHandler();
        return serverConnectionHandler;
    }

    public Requester getRequester() {
        return requester;
    }


}
