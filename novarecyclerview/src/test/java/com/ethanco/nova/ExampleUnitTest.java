package com.ethanco.nova;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        List<String> mDataList = new ArrayList<>();
        mDataList.add("q1");
        mDataList.add("q2");
        mDataList.add("q3");
        mDataList.add("q4");
        mDataList.add("q5");

        int position = mDataList.size();
        if (mDataList.add("wd")) {
            assertEquals(position, 5);
            assertEquals(mDataList.get(position), "wd");
        }
    }

    @Test
    public void test2() {
        List<String> mDataList = new ArrayList<>();
        mDataList.add("q1");
        mDataList.add("q2");
        mDataList.add("q3");
        mDataList.add("q4");
        mDataList.add("q5");

        int position = 1;

        mDataList.add(position, "wd");
        int itemCount = mDataList.size() - position;
//        assertEquals(position,);
        assertEquals(itemCount,5);
        //notifyItemRangeChanged(position, itemCount);
    }
}