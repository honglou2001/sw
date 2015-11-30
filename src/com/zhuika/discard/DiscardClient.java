/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zhuika.discard;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;






/**
 * Keeps sending random data to the specified address.
 */
public final class DiscardClient {
    public static void getClient(final String[] args) throws Exception{
    	client(args,null);
    }
    
    public static void getClient(final String[] args,byte[] byteMsg) throws Exception{
    	client(args,byteMsg);
    }
    
    private static void client(final String[] args,final byte[] byteMsg) throws Exception
    {
    	EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline(); 
                     p.addLast("framer",new DelimiterBasedFrameDecoder(8192, false,Delimiters.lineDelimiter()));
                     p.addLast(new ByteToMessageDec());
                     p.addLast(new MessageToByteEnc());      
                     
                     if (byteMsg!=null && byteMsg.length>0)
                     {
                    	 p.addLast(new DiscardClientHandler(byteMsg));
                     }
                     else
                     {
                    	 p.addLast(new DiscardClientHandler(args[2]));
                     }
                 }
             });            
            final ChannelFuture f = b.connect(args[0], Integer.valueOf(args[1])).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();            
        } finally {
            group.shutdownGracefully();
        }
    }
}
