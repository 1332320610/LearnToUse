package server;

import service.Service;
import service.ServiceImpl;

import java.io.IOException;

public class RPCServerMain {

    public static void main(String[] args) {
        new Thread(() -> {
            ServerCenter serverCenter = new ServerCenter(8090);
            try {
                serverCenter.register(Service.class, ServiceImpl.class);
                serverCenter.start();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                serverCenter.stop();
            }
        }).start();

    }
}
