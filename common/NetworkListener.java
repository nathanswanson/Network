package network.common;

import java.net.InetSocketAddress;

public interface NetworkListener {

    boolean bind();

    void close();

    InetSocketAddress getAddress();
}
