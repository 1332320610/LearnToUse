package client;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RPCClient rpcClient = new RPCClient();
        rpcClient.connect(new InetSocketAddress("localhost", 8090));
        String result = (String) rpcClient
                .invokeRemoteService("service.Service",
                        "declareIndependence", new Class[]{String.class}, new Object[]{"傻逼"});
        System.out.println(result);
    }
}
