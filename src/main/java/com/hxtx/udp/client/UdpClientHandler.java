package com.hxtx.udp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;



/**
 * @author admin
 */
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext,
                                   DatagramPacket datagramPacket) throws Exception {
        String response = datagramPacket.content().toString(CharsetUtil.UTF_8);

        if(response.startsWith("结果：")){
            System.out.println(response);
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)throws Exception{
        ctx.close();
        cause.printStackTrace();
    }

}
