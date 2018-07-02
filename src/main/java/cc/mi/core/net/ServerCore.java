package cc.mi.core.net;

import java.net.InetAddress;

import cc.mi.core.constance.IdentityConst;
import cc.mi.core.handler.ChannelHandlerGenerator;
import cc.mi.core.log.CustomLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public enum ServerCore {
	INSTANCE;
	
	static final CustomLogger logger = CustomLogger.getLogger(ServerCore.class);
	
	public void run(int port, ChannelHandlerGenerator handlerGenerator, byte serverType) throws Exception {
		final EventLoopGroup bossGroup = new NioEventLoopGroup(4);
		final EventLoopGroup workerGroup = new NioEventLoopGroup(4);
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1 << 10, 0, 4, -4, 4));
							ch.pipeline().addLast("decoder", new NetDefrager());
							
							ch.pipeline().addLast("idleState", new IdleStateHandler(60, 60, 60));
							ch.pipeline().addLast("serverHandler", handlerGenerator.newChannelHandler());
							
							ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4, true));
							ch.pipeline().addLast("encoder", new NetEnfrager());
						}
					 })
					 .option(ChannelOption.SO_REUSEADDR, true)
					 .option(ChannelOption.SO_BACKLOG, 128)
					 .childOption(ChannelOption.TCP_NODELAY, true)
					 .childOption(ChannelOption.SO_KEEPALIVE, true)
					 .childOption(ChannelOption.SO_RCVBUF, 43690)
					 .childOption(ChannelOption.SO_SNDBUF, 8900)
					 .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					 .handler(new LoggingHandler(LogLevel.DEBUG))
					 ;
			
			ChannelFuture future = bootstrap.bind(port).sync();
			String serverName = IdentityConst.getServerName(serverType);
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			logger.devLog("{} start successfully, ip = {}, port = {}", serverName, hostAddress, port);
			future.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}