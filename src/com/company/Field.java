package com.company;

public abstract class Field implements IField{
    private int extension;
    private boolean[] generator;
    private int generatorLen;
    public boolean[] mulIdentity;
    public boolean[] addIdentity;
}
