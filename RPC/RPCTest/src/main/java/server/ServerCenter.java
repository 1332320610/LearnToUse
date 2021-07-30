package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCenter implements Server{

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Map<String, Object> services = new HashMap<>();

    private ServerSocket serverSocket;

    private boolean state = false;
    private int port;

    public ServerCenter(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        this.serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));

        try {
            state = true;
            while(true) {
                executorService.execute(new ServerTask(serverSocket.accept()));
            }
        }finally {
            serverSocket.close();
        }

    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        services.put(serviceInterface.getName(), impl);
    }

    @Override
    public void stop() {
        state = false;
        executorService.shutdown();
    }

    private class ServerTask implements Runnable{

        Socket client;
        ObjectInputStream objectInputStream;

        ObjectOutputStream objectOutputStream;
        private ServerTask(Socket client) {
            this.client = client;
        }
        @Override
        public void run() {
            try {
                objectInputStream = new ObjectInputStream(client.getInputStream());
                String serviceName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                Object[] args = (Object[]) objectInputStream.readObject();
                Class service = (Class) services.get(serviceName);
                Method serviceMethod = service.getMethod(methodName, parameterTypes);
                Object returnValue = serviceMethod.invoke(service.getConstructor().newInstance(), args);
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(returnValue);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
