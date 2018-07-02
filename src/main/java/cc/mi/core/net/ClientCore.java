package cc.mi.core.net;

import cc.mi.core.handler.ChannelHandlerGenerator;
import cc.mi.core.log.CustomLogger;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public enum ClientCore {
	INSTANCE;
	
	static final CustomLogger logger = CustomLogger.getLogger(ClientCore.class);
	
	public void start(String ip, int port, ChannelHandlerGenerator handlerGenerator) throws Exception {
		final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        try {
        	final Bootstrap bootstrap = new Bootstrap();
	        bootstrap.channel(NioSocketChannel.class)
	        		 .option(ChannelOption.SO_KEEPALIVE,true)
	        		 .option(ChannelOption.TCP_NODELAY, true)
	        		 .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
	        		 .group(eventLoopGroup);
	        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
	            @Override
	            protected void initChannel(SocketChannel ch) throws Exception {
	            	ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, -4, 4));
					ch.pipeline().addLast("decoder", new NetDefrager());
					
					ch.pipeline().addLast("clientHandler", handlerGenerator.newChannelHandler());
					
					ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4, true));
					ch.pipeline().addLast("encoder", new NetEnfrager());
	            }
	        });
	        
	        logger.devLog("connect to ip = {} port = {}", ip, port);
	        ChannelFuture future = bootstrap.connect(ip, port).sync();
        	future.channel().closeFuture().sync();
        } finally {
        	eventLoopGroup.shutdownGracefully();
        }
    }
}
