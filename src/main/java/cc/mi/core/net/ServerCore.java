package cc.mi.core.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
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

public class ServerCore {
	public static void run(int port, ChannelHandler handler) throws Exception {
		final EventLoopGroup bossGroup = new NioEventLoopGroup();
		final EventLoopGroup workerGroup = new NioEventLoopGroup();
		final ServerBootstrap bootstrap = new ServerBootstrap();
		
		try {
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .option(ChannelOption.SO_REUSEADDR, true)
					 .childOption(ChannelOption.TCP_NODELAY, true)
					 .childOption(ChannelOption.SO_KEEPALIVE, true)
					 .childOption(ChannelOption.SO_RCVBUF, 43690)
					 .childOption(ChannelOption.SO_SNDBUF, 8900)
					 .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					 .handler(new LoggingHandler(LogLevel.DEBUG))
					 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1 << 7, 0, 4, -4, 4));
							ch.pipeline().addLast("decoder", new NetDefrager());
							
							ch.pipeline().addLast("idleState", new IdleStateHandler(60, 60, 60));
							ch.pipeline().addLast("serverHandler", handler);
							
							ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4, true));
							ch.pipeline().addLast("encoder", new NetEnfrager());
						}
					 });
			
			ChannelFuture future = bootstrap.bind(port).sync();
			System.out.println("CenterServer启动成功");
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}