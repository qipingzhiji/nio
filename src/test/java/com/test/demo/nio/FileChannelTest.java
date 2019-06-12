package com.test.demo.nio;

import org.junit.*;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by zhang_htao on 2019/6/10.
 */
public class FileChannelTest {

    @org.junit.Test
    public void test() throws Exception {
        FileInputStream fis = new FileInputStream("a.txt");
        FileOutputStream fos = new FileOutputStream("b.txt");
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        ByteBuffer dst = ByteBuffer.allocate(1024);
        while (fisChannel.read(dst) != -1) {
            dst.flip();
            fosChannel.write(dst);
            dst.clear();
        }
        fosChannel.close();
        fisChannel.close();
        fos.close();
        fis.close();

    }

    @Test
    public void directBuffer() throws Exception {
        FileChannel in = FileChannel.open(Paths.get("a.txt"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("c.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        MappedByteBuffer map = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
        out.write(map.get(new byte[map.position()]));
        out.close();
        in.close();

    }

    @Test
    public void testTransfrom() throws Exception {
        FileChannel in = FileChannel.open(Paths.get("a.txt"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("e.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        in.transferTo(0, in.position(), out);
        out.close();
        in.close();

    }

    @Test
    public void testRandomRead() throws Exception {
        RandomAccessFile rw = new RandomAccessFile("f.txt", "rw");
        FileChannel channel = rw.getChannel();
        ByteBuffer by1 = ByteBuffer.allocate(100);
        ByteBuffer by2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bys = {by1,by2};
        channel.read(bys);
        Arrays.stream(bys).forEach(
                s->{
                    s.flip();
                    System.out.println(new String(s.array(),0,s.limit()));
                }
        );

    }
}
