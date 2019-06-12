package com.test.demo.nio;

import org.junit.*;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * Created by zhang_htao on 2019/6/10.
 */
public class charsetTest {
    @org.junit.Test
    public void test() {
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        stringCharsetSortedMap.keySet().stream().forEach(
                s->{
                    System.out.println(stringCharsetSortedMap.get(s));
                }
        );
    }
}
