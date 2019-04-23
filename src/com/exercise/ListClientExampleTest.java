package com.exercise;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListClientExampleTest {

    @Test
    public void testListClientExample(){
        ListClientExample lce = new ListClientExample();
        List list = lce.getList();
        Assert.assertThat(list, CoreMatchers.instanceOf(ArrayList.class));
    }

}
