package com.company;

public abstract class Field implements IField{
    private int extension;
    public boolean[] generator;
    private int generatorLen;
    public boolean[] mulIdentity;
    public boolean[] addIdentity;
}
