package network.query.packet;

import network.query.QueryPacket;
import io.netty.buffer.ByteBuf;

public class StatisticsPacket implements QueryPacket {
    private static final short ID = 0x00;
    // Both
    private int sessionId;
    // Request
    private int token;
    private boolean full;
    // Response
    private ByteBuf payload;

    @Override
    public void decode(ByteBuf buffer) {
        sessionId = buffer.readInt();
        token = buffer.readInt();
        full = (buffer.isReadable());
        buffer.skipBytes(buffer.readableBytes());
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeInt(sessionId);
        buffer.writeBytes(payload);
    }

    @Override
    public int getSessionId() {
        return 0;
    }

    @Override
    public void setSessionId(int sessionId) {

    }

    @Override
    public short getId() {
        return ID;
    }
}
