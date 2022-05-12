package uk.co.mruoc.app.env;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class PortReady implements Callable<Boolean> {

    private final String host;
    private final int port;

    public static PortReady local(int port) {
        return PortReady.builder().host("localhost").port(port).build();
    }

    @Override
    public Boolean call() {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(true);
            channel.connect(new InetSocketAddress(host, port));
            log.info("successfully connected to {} on port {}", host, port);
            return true;
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
            log.warn("failed to connect to {} on port {}", host, port);
            return false;
        }
    }
}
