package com.company;

import javax.swing.*;
import java.util.Arrays;

public class MyField extends Field{
    private int extension;
    private boolean[] generator;
    private int generatorLen;
    private boolean[] varForInverse;
    public boolean[] mulIdentity;
    public boolean[] addIdentity;


    /*

    Заметки себе:
        2) modElement:
            Подумать, как можно ускорить это ( Может быть с помощью битовых операций.
            Может быть использовать другие операции. Или дополнительно выделять память: S(n) = O(n). Сохраняя результаты промежуточных операций

        4) Пытаемся мутить по SOLIDу.

    */

    private int shortBitLength(long base, long a){
        if(a == 0 ) {
            return 1;
        }
        int rez;
        rez = (int) (Math.log(a) / Math.log(base)) + 1;
        return rez;
    }

    private boolean[] killZeros(boolean[] a){
        int i = a.length - 1;
        while(!a[i] && i > 0){
            i--;
        }
        boolean[] rez_arr = new boolean[i+1];
        for(int j = 0; j < i + 1; j++){
            rez_arr[j] = a[j];
        }
        return rez_arr;
    }


    private boolean[] zeroIzation(boolean[] a, int k){
        int len_a = a.length;
        int length = len_a + k;
        boolean[] rez_arr = new boolean[length];
        int i = 0;
        for( i = i; i < len_a; i++){
            rez_arr[i] = a[i];
        }
        for(i = i; i < length; i++){
            rez_arr[i] = false;
        }
        return rez_arr;
    }

    private boolean[] addElements(boolean[] a, boolean[] b){
        int len_a = a.length; int len_b = b.length;
        if(len_a < len_b){ a = zeroIzation(a,len_b-len_a);}
        else if(len_b < len_a){ b = zeroIzation(b,len_a - len_b);}
        int length = a.length;
        boolean[] rez_add = new boolean[length];
        for(int i = 0; i < length; i ++){
            rez_add[i] = (a[i])^(b[i]);
        }
        return rez_add;
    }

    private boolean[] bitShiftElementLeft(boolean[] a, int k){
        int len_a = a.length;
        int rez_len = len_a + k;
        boolean[] rez = new boolean[rez_len];
        for(int i = rez_len - 1; i > k - 1; i--){
            rez[i] = a[i-k];
        }
        for(int i = k - 1; i > -1; i--){
            rez[i] = false;
        }
        return rez;
    }

    private void reverse(boolean[] arr, int start, int len){
        boolean temp; int indx = 2*start + len - 1;
        for(int i = start; i < (start + len)/2; i++){
            temp = arr[indx - i];
            arr[2*start + len - i - 1] = arr[i];
            arr[i] = temp;
        }
    }

    private boolean[] cycleShiftElementLeft(boolean[] arr, int k){
        int len = arr.length;
        reverse(arr,0,len - k);
        reverse(arr, len - k , k);
        reverse(arr, 0, len);
        return arr;
    }

    private boolean[] modElement(boolean[] a){
        a = killZeros(a);
        int len_a = a.length;
        if(len_a < this.generatorLen){
            return a;
        }
        boolean[] temp;
        int lendiff = len_a - this.generatorLen;
        while(lendiff>=0 ){
            temp = bitShiftElementLeft(generator,lendiff);
            a = addElements(a,temp);
            a = killZeros(a);
            lendiff = a.length - this.generatorLen;
        }
        return a;
    }

    private boolean[] mulElements(boolean[] a,boolean[] b){




        return new boolean[1];
    }



    private String hexToBin(String basic_str){
            int k = 8;
            int str_len = basic_str.length();
            StringBuffer str_buf = new StringBuffer("");
            long a; int i = k;
            for (i = i; i <= str_len; i += k) {
                a = Long.parseLong(basic_str.substring(i - k, i), 16);
                int nums_number  = shortBitLength(16,a);
                int zeros_number = 32/4 - nums_number;
                String str_0 = new String(new char[zeros_number]).replace("\0", "0");
                str_buf.append(str_0);
                str_buf.append(Long.toBinaryString(a));
            }
            if (str_len % k != 0) {
                a = Long.parseLong(str_buf.substring(i-k,str_len), 16);
                int nums_number  = shortBitLength(16,a);
                int zeros_number = 32 /4 - nums_number;
                String str_0 = new String(new char[zeros_number]).replace("\0", "0");
                str_buf.append(str_0);
                str_buf.append(Long.toBinaryString(a));
            }
            return new String(str_buf);
    }


    private boolean[] getBooleanArray(String basic, boolean bool){
        String str = basic;
        if (bool) {
            str = hexToBin(basic);
        }
        int string_len = str.length();
        boolean[] arr = new boolean[string_len]; char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = str.charAt(i);
            if(chr == '1'){
                arr[i] = true;
                continue;
            }
            arr[i] = false;
        }
        return arr;
    }


    public void setExtension(int extension){
        this.extension = extension;
        this.addIdentity = new boolean[extension];
        this.addIdentity[0] = false;
        this.mulIdentity = new boolean[extension];
        this.mulIdentity[0] = true;
        String binM = Long.toBinaryString(extension);
        this.varForInverse = addGalois(getBooleanArray(binM,false),this.mulIdentity);
    }

    public void setGenerator(boolean[] gen){
        this.generator = gen;
        this.generatorLen = gen.length;
    }

    public boolean[] getAddIdentity(){
        return this.addIdentity;
    }

    public boolean[] getMulIdentity(){
        return this.mulIdentity;
    }


    public boolean[] addGalois(boolean[] a, boolean[] b){
        a = modElement(a);
        b = modElement(b);
        return addElements(a,b);
    }

    public boolean[] mulGalois(boolean[] a,boolean[] b){
        boolean[] prod = mulElements(a,b);
        prod = modElement(prod);
        return prod;
    }


    public boolean[] squaredElement(boolean[] a){
        a = cycleShiftElementLeft(a,1);
        return a;
    }

    public boolean getTrace(boolean[] arg){
        boolean trace = false; int len = arg.length;
        for(int i = 0; i < len; i++){
            trace = trace^arg[i];
        }
        return trace;
    }



    public boolean[] getPower(boolean[] arg, String power){
        String bin_string_b = hexToBin(power);
        int string_len = bin_string_b.length();
        boolean[] arr = new boolean[]{true};
        char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = bin_string_b.charAt(i);
            if(chr == '1'){
                arr = mulElements(arr,arg);
            }
            arg = squaredElement(arg);
        }
        return arr;
    }


    public boolean[] getInverse(boolean[] arg) {
        boolean[] b = arg;
        boolean[] m_1 = this.varForInverse;
        /*

        Тут должен быть Ито-Цудзии

        */

        return new boolean[1];
    }

}
