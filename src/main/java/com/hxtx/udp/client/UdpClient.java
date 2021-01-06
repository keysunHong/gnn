package com.hxtx.udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;


/**
 * @author admin
 */
public class UdpClient {
    private Channel ch;
    private EventLoopGroup group;
    private InetSocketAddress inetSocketAddress;


    public void connect(String ip, int port) throws Exception {
        group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new UdpClientHandler());

        ch = b.bind(0).sync().channel();
        inetSocketAddress = new InetSocketAddress(ip, port);
    }

    public void sendMessage(String message) {
        try {
            ch.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8), inetSocketAddress)).sync();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
        }
    }

    public void close() {
        if(group!=null){
            group.shutdownGracefully();
        }
    }

}
