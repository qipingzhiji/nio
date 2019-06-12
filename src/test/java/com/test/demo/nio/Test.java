package com.test.demo.nio;

import java.nio.ByteBuffer;

/**
 * Created by zhang_htao on 2019/6/10.
 */
public class Test {

    @org.junit.Test
    public void test() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());
        System.out.println("----------------------------------------------");

        String str = "hello";
        buffer.put(str.getBytes());
        System.out.println("-----------------------put-----------------------");
        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());

        buffer.flip();
        System.out.println("-----------------------flip-----------------------");
        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());

        System.out.println("-----------------------get-----------------------");

        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes,0,buffer.limit()));

        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());

        buffer.rewind();//可重复读数据
        System.out.println("-----------------------rewind-----------------------");
        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());


        buffer.clear();//使缓冲区内的处于暂时遗忘状态
        System.out.println("-----------------------clear-----------------------");
        System.out.println("position:" + buffer.position());
        System.out.println("capactiy:" + buffer.capacity());
        System.out.println("limit:" + buffer.limit());
        System.out.println((char) buffer.get());

    }


    @org.junit.Test
    public void testMark() {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        byte[] dst = new byte[2];
        buffer.get(dst);
        System.out.println("---------------------");
        System.out.println(new String(dst,0,dst.length));
        System.out.println("position:" + buffer.position());

        System.out.println("--------------mark------------------");

        buffer.mark();

        System.out.println("--------------get------------------");
        buffer.get(dst);
        System.out.println(new String(dst,0,dst.length));
        System.out.println("position:" +  buffer.position());

        System.out.println("-----------------reset---------------");
        buffer.reset();
        System.out.println("position:" + buffer.position());
    }

}
