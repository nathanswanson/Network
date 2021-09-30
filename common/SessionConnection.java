package network.common;

import network.common.util.DisconnectReason;

import java.net.InetSocketAddress;

public interface SessionConnection<T> {

    InetSocketAddress getAddress();

    void close();

    void close(DisconnectReason reason);

    void disconnect();

    void disconnect(DisconnectReason reason);

    void send(T packet);

    void sendImmediate(T packet);

    boolean isClosed();

    long getPing();
}
