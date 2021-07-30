package server;

import java.io.IOException;

public interface Server {

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    void stop();
}
