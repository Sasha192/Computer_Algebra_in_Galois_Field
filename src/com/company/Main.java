package com.company;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.Random;

import java.util.Arrays;

public class Main {


    public static void main(String[] args){
        MyField obj = new MyField();
        obj.setExtension(191);
        obj.setGenerator();
        boolean[] arg_1 = new boolean[191];
        boolean[] arg_2 = new boolean[191];
        for(int i = 0; i < 191; i++){
            if(i%2 == 0){
                arg_1[i] = true;
            }
            else{
                arg_2[i] = true;
            }
        }
        boolean[] res = obj.getInverse(arg_1);
        boolean[] prod = obj.mulGalois(res,arg_1);
        System.out.println("len = " + res.length + "\n" +  Arrays.toString(prod));
    }
}
