package com.hxtx.tcp.client;

import com.hxtx.tcp.server.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * ClientHandler
 *
 * @author sunweihong
 * @desc 描述
 * @date 2020/7/2 14:58
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
//            ByteBuf bb = (ByteBuf) msg;
//            byte[] respByte = new byte[bb.readableBytes()];
//            bb.readBytes(respByte);
//            String respStr = new String(respByte, "UTF-8");
//            System.err.println("client--收到响应：" + respStr);

            // 直接转成对象
            handlerObject(ctx, msg);

        } finally {
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);

        }

    }

    private void handlerObject(ChannelHandlerContext ctx, Object msg) {

        Student student = (Student) msg;
        System.err.println("server 获取信息：" + student.getId() + student.getName());
    }


    // 数据读取完毕的处理
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端读取数据完毕");
    }

    // 出现异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("client 读取数据出现异常");
        ctx.close();
    }

}