package com.test.demo.nio;

import com.sun.javafx.image.ByteToBytePixelConverter;
import org.junit.*;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by zhang_htao on 2019/6/12.
 */
public class socketChannelTest {

    @org.junit.Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        FileChannel fileChannel = FileChannel.open(Paths.get("a.txt"), StandardOpenOption.READ);
        while (fileChannel.read(allocate) != -1) {
            allocate.flip();
            socketChannel.write(allocate);
            allocate.clear();
        }

        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void serverSocket() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //获取客户端的通道
        SocketChannel acceptChannel = serverSocketChannel.accept();
        FileChannel outChannel = FileChannel.open(Paths.get("server.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(acceptChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        outChannel.close();
        acceptChannel.close();
        serverSocketChannel.close();

    }

}
