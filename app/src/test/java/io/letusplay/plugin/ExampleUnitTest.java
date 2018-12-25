package io.letusplay.plugin;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testSplit() {
        String s = "2,1,4,3,5,";
        List<String> data = Arrays.asList(s.split(","));
        assertTrue(data.contains(String.valueOf(1)));
    }
}