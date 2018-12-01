package com.company;

public interface IField {
    public void setExtension(int extension);
    public void setGenerator(boolean[] gen);
    public boolean[] getAddIdentity();
    public boolean[] getMulIdentity();
    public boolean[] addGalois(boolean[] a, boolean[] b);
    public boolean[] mulGalois(boolean[] a,boolean[] b);
    public boolean getTrace(boolean[] arg);
}
