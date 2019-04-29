package com.exercise;

import com.exercise.Profiler.Timeable;
import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Exercise4
 */
public class ProfileListAdd {
    public static void main(String[] args) {
        //profileArrayListAddEnd();
        //profileArrayListAddBeginning();
        //profileLinkedListAddBeginning();
        profileLinkedListAddEnd();
    }

    public static void profileArrayListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            @Override
            public void setup(int n) {
                list = new ArrayList();
            }

            @Override
            public void timeMe(int n) {
                for (int i = 0; i < n; i++) {
                    list.add("add String");
                }
            }
        };
        int startN = 4000;
        int endMillis = 1000;
        runProfiler("ArrayList add end",timeable,startN,endMillis);
    }

    public static void profileArrayListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            @Override
            public void setup(int n) {
                list = new ArrayList();
            }

            @Override
            public void timeMe(int n) {
                for (int i = 0; i < n; i++) {
                    list.add(0,"add String");
                }
            }
        };
        int startN = 4000;
        int endMillis = 10000;
        runProfiler("ArrayList add begin",timeable,startN,endMillis);
    }

    public static void profileLinkedListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            @Override
            public void setup(int n) {
                list = new LinkedList<>();
            }

            @Override
            public void timeMe(int n) {
                for (int i = 0; i < n; i++) {
                    list.add(0,"add String");
                }
            }
        };
        int startN = 128000;
        int endMillis = 2000;
        runProfiler("LinkedList add end",timeable,startN,endMillis);
    }

    public static void profileLinkedListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            @Override
            public void setup(int n) {
                list = new LinkedList<>();
            }

            @Override
            public void timeMe(int n) {
                for (int i = 0; i < n; i++) {
                    list.add("add String");
                }
            }
        };
        int startN = 4000;
        int endMillis = 10000;
        runProfiler("LinkedList add end",timeable,startN,endMillis);
    }

    /**
     * Runs the profiles and displays results.
     *
     * @param timeable
     * @param startN
     * @param endMillis
     */
    private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }
}
