package com.nukkitx.network.query.codec;

import network.query.QueryPacket;
import com.nukkitx.network.query.enveloped.DirectAddressedQueryPacket;
import com.nukkitx.network.query.packet.HandshakePacket;
import com.nukkitx.network.query.packet.StatisticsPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.Arrays;
import java.util.List;

public class QueryPacketCodec extends MessageToMessageCodec<DatagramPacket, DirectAddressedQueryPacket> {
    private static final byte[] QUERY_SIGNATURE = new byte[]{(byte) 0xFE, (byte) 0xFD};
    private static final int HANDSHAKE = 0x09;
    private static final short STATISTICS = 0x00;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DirectAddressedQueryPacket packet, List<Object> list) throws Exception {
        try {
            ByteBuf buf = ByteBufAllocator.DEFAULT.ioBuffer();
            buf.writeByte(packet.content().getId() & 0xFF);
            packet.content().encode(buf);
            list.add(new DatagramPacket(buf, packet.recipient(), packet.sender()));
        } finally {
            packet.release();
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket packet, List<Object> list) throws Exception {
        ByteBuf buf = packet.content();
        if (buf.readableBytes() < 3) {
            // not interested
            return;
        }
        buf.markReaderIndex();

        byte[] prefix = new byte[2];
        buf.readBytes(prefix);
        if (Arrays.equals(prefix, QUERY_SIGNATURE)) {
            short id = buf.readUnsignedByte();
            QueryPacket networkPacket;
            switch (id) {
                case HANDSHAKE:
                    networkPacket = new HandshakePacket();
                    break;
                case STATISTICS:
                    networkPacket = new StatisticsPacket();
                    break;
                default:
                    buf.resetReaderIndex();
                    return;
            }
            networkPacket.decode(buf);
            list.add(new DirectAddressedQueryPacket(networkPacket, packet.recipient(), packet.sender()));
        } else {
            buf.resetReaderIndex();
        }
    }
}
