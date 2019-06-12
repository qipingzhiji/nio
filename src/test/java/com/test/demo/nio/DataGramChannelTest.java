package com.test.demo.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by zhang_htao on 2019/6/12.
 */
public class DataGramChannelTest {
    @org.junit.Test
    public void test() throws Exception {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((new Date().toString()).getBytes());
        buffer.flip();
        datagramChannel.send(buffer,new InetSocketAddress("127.0.0.1",8080));
        buffer.clear();
        datagramChannel.close();

    }


    @Test
    public void server() throws Exception {
        DatagramChannel receive = DatagramChannel.open();
        receive.bind(new InetSocketAddress(8080));
        receive.configureBlocking(false);
        Selector selector = Selector.open();
        receive.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    receive.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit()));
                }
                iterator.remove();
            }
        }

    }
}

