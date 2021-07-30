package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RPCClient {
    private Socket socket;

    private ObjectOutputStream outputStream;

    private ObjectInputStream inputStream;

    public RPCClient() {
        this.socket = new Socket();

    }

    public void connect(InetSocketAddress address) {
        try {
            socket.connect(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object invokeRemoteService(String serviceName, String methodName, Class[] parameterTypes, Object[] args) throws IOException, ClassNotFoundException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeUTF(serviceName);
        outputStream.writeUTF(methodName);
        outputStream.writeObject(parameterTypes);
        outputStream.writeObject(args);
        inputStream = new ObjectInputStream(socket.getInputStream());
        Object returnValue = inputStream.readObject();
        return returnValue;
    }
}
