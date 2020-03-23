package com.example.tabs;

public class State {

    private String name;
    private int flagResource;

    public State(String name, int flag)
    {
        this.name = name;
        this.flagResource = flag;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getFlagResource()
    {
        return this.flagResource;
    }
}
