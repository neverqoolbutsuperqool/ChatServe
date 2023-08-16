public class ChatServer {

    private XMPPConnection connection;
    private ChatManager chatManager;

    public ChatServer(String domain, int port) throws XMPPException {
        ConnectionConfiguration configuration = new ConnectionConfiguration(domain, port);
        configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.TLS);
        connection = new XMPPConnection(configuration);
        connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                System.out.println("Chat server connected");
            }

            @Override
            public void authenticated(XMPPConnection connection) {
                System.out.println("Chat server authenticated");
            }

            @Override
            public void connectionClosed() {
                System.out.println("Chat server connection closed");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                System.out.println("Chat server connection closed on error");
            }
        });
        connection.connect();
        chatManager = connection.getChatManager();
    }

    public void start() {
        chatManager.addChatListener(new MessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message from " + message.getFrom() + ": " + message.getBody());
            }
        });
    }

    public void sendMessage(String to, String message) throws XMPPException {
        Chat chat = chatManager.createChat(to, null);
        chat.sendMessage(message);
    }

}
