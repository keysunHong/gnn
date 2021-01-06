package com.hxtx.tcp.client;

import com.hxtx.tcp.server.MarshallingCodefactory;
import com.hxtx.tcp.server.Student;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * ClientNetty
 *
 * @author sunweihong
 * @desc 描述
 * @date 2020/7/2 14:58
 **/
public class ClientNetty {

    private String ip;

    private int port;

    public ClientNetty(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        new ClientNetty("127.0.0.1", 8848).action();
    }

    /**
     * 请求端主题
     */
    public void action() throws InterruptedException, UnsupportedEncodingException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        Bootstrap bs = new Bootstrap();

        bs.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /**
                         * 传递对象类的时候需要开启序列化
                         */
                        // marshalling 序列化对象的解码
                        socketChannel.pipeline().addLast(MarshallingCodefactory.buildDecoder());
                        // marshalling 序列化对象的编码
                        socketChannel.pipeline().addLast(MarshallingCodefactory.buildEncoder());

                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        // 客户端开启
        ChannelFuture cf = bs.connect(ip, port).sync();

        // 发送客户端的请求
//        cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端请求1$_".getBytes("UTF-8")));
//        Thread.sleep(300);
//        cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端请求2$_---".getBytes("UTF-8")));
//        Thread.sleep(300);
//        cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端请求3$_".getBytes("UTF-8")));

        /**
         * 传递对象类的时候需要开启序列化
         */
        Student student = new Student();
        student.setId(3);
        student.setName("张三");
        cf.channel().writeAndFlush(student);
        Thread.sleep(300);
        Student student2 = new Student();
        student2.setId(1);
        student2.setName("李四");
        cf.channel().writeAndFlush(student2);

        // 等待直到连接中断
        cf.channel().closeFuture().sync();
    }

}