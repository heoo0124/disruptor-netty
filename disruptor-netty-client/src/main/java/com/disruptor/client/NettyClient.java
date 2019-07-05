package com.disruptor.client;

import com.disruptor.codec.MarshallingCodeCFactory;
import com.disruptor.entity.TranlatorData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8765;

    private Channel channel;

    //1、创建一个工作线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    private ChannelFuture cf;

    public NettyClient() {
        this.connect(HOST, PORT);
    }

    private void connect(String host, int port) {

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ClientHandler());
                        }
                    });

            //2、连接
            this.cf = bootstrap.connect(host, port).sync();
            System.err.println("Client connected！..........");

            this.channel = cf.channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void sendData() {

        for (int i = 0; i < 10; i++) {
            System.err.println(i + ":sent!" + this.channel);
            TranlatorData request = new TranlatorData();
            request.setId("" + i);
            request.setName("请求消息名称 " + i);
            request.setMessage("请求消息内容 " + i);
            this.channel.writeAndFlush(request);
        }
    }

    public void close() throws InterruptedException {
        cf.channel().closeFuture().sync();

        workGroup.shutdownGracefully();
        System.err.println("Client shutdown gracefully!");
    }

}
