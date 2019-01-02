package com.company;

import javax.swing.*;
import java.util.Arrays;

public class MyField{
    private int extension;
    private boolean[] generator;
    private int generatorLen;
    private boolean[][] Matrix;
    public boolean[] mulIdentity;
    public boolean[] addIdentity;


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
        if(k<=0){
            return a;
        }
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

    private void reverse(boolean[] arr, int start, int end){
        boolean temp; end--;
        while(start < end){
            temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
            start++;
            end--;
        }
    }

    private boolean[] cycleShiftElementRight(boolean[] arr, int k){
        if(k<=0){
            return arr;
        }
        int len = arr.length;
        reverse(arr,0,len - k);
        reverse(arr, len - k , k);
        reverse(arr, 0, len);
        return arr;
    }


    private boolean[] cycleShiftElementLeft(boolean[] arr, int k){
        if(k<=0){
            return arr;
        }
        int len = arr.length;
        reverse(arr,0,k);
        reverse(arr, k , len);
        reverse(arr, 0, len);
        return arr;
    }

    private boolean[][] setMatrix(int extension){
        long p = 2*extension + 1;
        long[] lng = new long[extension];
        lng[0] = 1;
        for(int i = 1; i < extension; i++){
            lng[i] = (lng[i-1] << 1 )%p;
        }
        long pow_i, pow_j;
        boolean[][] Matrix = new boolean[extension][extension];
        for (int i = 0; i < extension; i++ ){
            pow_i = lng[i];
            for (int j = 0; j < extension; j++){
                pow_j = lng[j];
                if (((pow_i + pow_j) % p) == 1 || (((pow_i - pow_j) + p) % p) == 1 ||
                        ((pow_j - pow_i + p) % p) == 1 || (((-pow_i - pow_j)+p) % p) == 1){
                    Matrix[i][j] = true;
                    continue;
                }
                Matrix[i][j] = false;
            }
        }
        return Matrix;
    }

    private boolean[] mulElements(boolean[] a,boolean[] b){
        int len_a = a.length; int len_b = b.length;
        a = zeroIzation(a,extension - len_a);
        b = zeroIzation(b, extension - len_b);
        len_a = a.length;
        boolean[] res = new boolean[len_a];
        for(int k = 0; k < extension; k++){
            if( k > 0 ) {
                a = cycleShiftElementLeft(a, 1);
                b = cycleShiftElementLeft(b, 1);
            }
            boolean res_k = false;
            boolean[] temp = new boolean[extension];
            for(int j = 0; j < extension; j ++){
                for(int i = 0; i < extension; i++){
                    temp[j] = (temp[j])^(a[i]&Matrix[i][j]);
                }
            }
            for(int i = 0; i < extension; i ++){
                res_k = (res_k)^(temp[i]&b[i]);
            }
            res[k] = res_k;
        }
        return res;
    }

    private long[] hexToDecimal(String basic_str){
        int k = 4;
        int str_len = basic_str.length();
        int arr_len = str_len/k + ( str_len%k>0 ? 1:0 ) ;
        StringBuffer str_buf = new StringBuffer(basic_str);
        long[] arr = new long [arr_len];
        for(int i = k, j = 0; i<=str_len && j <arr_len; i+=k, j++){
            arr[j] = Long.parseLong(str_buf.substring(str_len-i,str_len-i+k),16);
        }
        if (str_len%k!=0) {
            arr[arr_len - 1] = Long.parseLong(str_buf.substring(0, str_len % k), 16);
        }
        return arr;
    }

    private String getBinString(String arg){
        long[] arr = hexToDecimal(arg);
        int len = arr.length; StringBuffer str = new StringBuffer("");
        for(int i = len - 1; i > -1; i--){
            str.append(Long.toBinaryString(arr[i]));
        }
        String ret_str = str.toString();
        return ret_str;
    }

    public boolean[] getBooleanArray(String basic){
        int string_len = basic.length();
        boolean[] arr = new boolean[string_len]; char chr;
        for(int i = string_len - 1; i > -1; i--){
            chr = basic.charAt(i);
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
        this.Matrix = setMatrix(extension);
    }

    public void setGenerator(){
        boolean[] p0 = new boolean[191];
        for(int i = 0; i < extension; i++){
            p0[i] = true;
        }
        boolean[] p1 = addGalois(p0,new boolean[]{true});
        boolean[] temp; this.generator = new boolean[]{false};
        for(int i = 1; i < this.extension; i++){
            this.generator = addElements(mulElements(p1,new boolean[]{true}),p0);
            p0 = p1;
            p1 = this.generator;
        }
        this.generatorLen = this.generator.length;
    }

    public boolean[] addGalois(boolean[] a, boolean[] b){
        return addElements(a,b);
    }

    public boolean[] mulGalois(boolean[] a,boolean[] b){
        return mulElements(a,b);
    }

    public boolean[] squaredElement(boolean[] a){
        a = cycleShiftElementRight(a,1);
        return a;
    }

    public boolean getTrace(boolean[] arg){
        boolean trace = false; int len = arg.length;
        for( int i = 0; i < len; i ++){
            trace = trace^arg[i];
        }
        return trace;
    }

    public boolean[] getPower(boolean[] arg, String power){
        String bin_string_b = power;
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
        boolean[] prod = new boolean[]{true};
        for(int i = 1; i < this.extension; i++){
            arg = squaredElement(arg);
            prod = mulElements(prod,arg);
        }
        return prod;
    }

}
