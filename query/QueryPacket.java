package network.query;

import network.common.NetworkPacket;

public interface QueryPacket extends NetworkPacket {

    int getSessionId();

    void setSessionId(int sessionId);

    short getId();
}
