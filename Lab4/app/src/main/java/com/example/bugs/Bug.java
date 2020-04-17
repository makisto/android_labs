package com.example.bugs;

import android.graphics.Bitmap;

class Bug//класс атрибутов жука
{
    private int x;
    private int y;
    private int x_start;
    private int y_start;
    private int x_picture;
    private int y_picture;
    private int speed;
    private int death;
    Bitmap live_picture;
    Bitmap death_picture;
    private boolean live;

    Bug(int x, int y, int speed, Bitmap a, Bitmap b, int death)
    {
        setX(x);
        setY(y);
        setX_start(x);
        setY_start(y);
        setX_picture(a.getWidth() / 2);
        setY_picture(a.getHeight() / 2);
        setSpeed(speed);
        setDeath(death);
        setLive_picture(a);
        setDeath_picture(b);
        setLive(true);
    }

    void setX(int x)
    {
        this.x = x;
    }

    int getX()
    {
        return this.x;
    }

    void setY(int y)
    {
        this.y = y;
    }

    int getY()
    {
        return this.y;
    }

    private void setX_start(int x)
    {
        this.x_start = x;
    }

    int getX_start()
    {
        return this.x_start;
    }

    private void setY_start(int y)
    {
        this.y_start = y;
    }

    int getY_start()
    {
        return this.y_start;
    }

    private void setX_picture(int x_picture)
    {
        this.x_picture = x_picture;
    }

    int getX_picture()
    {
        return this.x_picture;
    }

    private void setY_picture(int y_picture)
    {
        this.y_picture = y_picture;
    }

    int getY_picture()
    {
        return this.y_picture;
    }

    private void setSpeed(int speed)
    {
        this.speed = speed;
    }

    int getSpeed()
    {
        return this.speed;
    }

    private void setDeath(int death)
    {
        this.death = death;
    }

    int getDeath()
    {
        return this.death;
    }

    private void setLive_picture(Bitmap live_picture)
    {
        this.live_picture = live_picture;
    }

    private void setDeath_picture(Bitmap death_picture)
    {
        this.death_picture = death_picture;
    }

    void setLive(boolean live)
    {
        this.live = live;
    }

    boolean isLive()
    {
        return this.live;
    }
}
