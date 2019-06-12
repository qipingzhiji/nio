package com.test.demo.nio;

import org.junit.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by zhang_htao on 2019/6/10.
 */
public class directBufferTest {

    @org.junit.Test
    public void test() throws Exception{
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        Charset gbk = Charset.forName("GBK");
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        CharsetDecoder charsetDecoder = gbk.newDecoder();
        CharBuffer allocate = CharBuffer.allocate(1024);
        allocate.put("明明如月，何时可掇？");
        allocate.flip();
        ByteBuffer encodeBuffer = charsetEncoder.encode(allocate);
        for (int i = 0; i < 12; i++) {
            byte b = encodeBuffer.get();
            System.out.println(b + " position:" +  encodeBuffer.position());
        }

        System.out.println("-----------------decode------------------------");
        encodeBuffer.flip();
        CharBuffer decodeBuffer = charsetDecoder.decode(encodeBuffer);
        System.out.println(decodeBuffer.toString());
    }
}
