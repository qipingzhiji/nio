package com.test.demo.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by zhang_htao on 2019/6/12.
 */
public class socketChannel2Test {

    @org.junit.Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        FileChannel fileChannel = FileChannel.open(Paths.get("a.txt"), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.shutdownOutput();
        //接收服务端的响应
        int len = 0;
        while ((len = socketChannel.read(buffer)) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void server() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        FileChannel outChannel = FileChannel.open(Paths.get("server2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        while(socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        //给客户端返回响应
        byteBuffer.put("收到了客户端的响应，文件已经复制完成".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        outChannel.close();
        socketChannel.close();
        serverSocketChannel.close();
    }


}
