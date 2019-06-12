package com.test.demo.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by zhang_htao on 2019/6/12.
 */
public class asySocketChannelTest {

    @org.junit.Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        //设置客户端的响应为非阻塞
        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String str = scanner.next();
            buffer.put((new Date().toString() + "\n" + str).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }


    @Test
    public void server() throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置服务端为非阻塞式的
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        //服务器端注册选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //获取选择器上已经就绪状态的事件
        while(selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                //获取准备就绪的事件
                SelectionKey selectionKey = iterator.next();
                //若接收就绪，获取客户端连接
                if(selectionKey.isAcceptable()) {
                    SocketChannel acceptChannel = serverSocketChannel.accept();
                    //切换非阻塞式
                    acceptChannel.configureBlocking(false);
                    //将该通道注册到选择器上
                    acceptChannel.register(selector,SelectionKey.OP_READ);
                    //获取状态为"已读就绪"的通道
                } else if(selectionKey.isReadable()) {

                    SocketChannel acceptChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while(acceptChannel.read(buffer) != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,buffer.limit()));
                        buffer.clear();
                    }
                }
                //取消选择键
                iterator.remove();
            }
        }
    }
}
