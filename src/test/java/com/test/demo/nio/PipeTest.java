package com.test.demo.nio;

import org.junit.*;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created by zhang_htao on 2019/6/12.
 */
public class PipeTest {

    private  Pipe pipe = null;

    public void init() throws Exception{
        pipe = Pipe.open();
    }
    @org.junit.Test
    public void pipeA() throws Exception {
        init();
        pipeB(pipe);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sink = pipe.sink();
        buffer.put("hello world this is pipe a".getBytes());
        buffer.flip();
        sink.write(buffer);
        buffer.clear();
        sink.close();

//        Pipe.SourceChannel source = pipe.source();
//        source.read(buffer);
//        buffer.flip();
//        System.out.println(new String(buffer.array(),0,buffer.limit()));
//        source.close();
    }

    @org.junit.Test
    public void pipeB(Pipe pipe) throws Exception {
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        sourceChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit()));
        sourceChannel.close();
    }
}
