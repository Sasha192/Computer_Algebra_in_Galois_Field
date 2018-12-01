package com.company;
import java.util.Random;

import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        boolean[] in = new boolean[]{true,false,true,true,false};
        MyField obj = new MyField();
        System.out.println(Arrays.toString(obj.cycleShiftElementLeft(in,1)));
    }
}
