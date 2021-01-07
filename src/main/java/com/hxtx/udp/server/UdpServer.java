package com.hxtx.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;


/**
 * @author admin
 */
public class UdpServer {

    /**
     * 相比于TCP而言，UDP不存在客户端和服务端的实际链接，因此不需要为连接(ChannelPipeline)设置handler
     * @param port
     * @throws Exception
     */
    public void start(int port)throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(new UdpServerHandler());

            b.bind(port).sync().channel().closeFuture().await();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
