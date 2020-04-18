package com.example.bugs;

import java.util.Random;

public class BugThread extends Thread//вспомогательный класс для добавления жука в поток
{
    private Bug bug;
    private int x_max;
    private int y_max;
    private int x_start;
    private int side;
    private boolean game;
    private Random random = new Random();

    BugThread(Bug bug, int x_max, int y_max, int side, boolean isGame)
    {
        setBug(bug);
        setX_max(x_max);
        setY_max(y_max);
        setSide(side);
        setX_start(bug.getX_start());
        setGame(isGame);
    }

    private void setBug(Bug bug)
    {
        this.bug = bug;
    }

    private void setX_max(int x_max)
    {
        this.x_max = x_max;
    }

    private int getX_max()
    {
        return this.x_max;
    }

    private void setY_max(int y_max)
    {
        this.y_max = y_max;
    }

    private int getY_max()
    {
        return this.y_max;
    }

    private void setSide(int side)
    {
        this.side = side;
    }

    private int getSide()
    {
        return this.side;
    }

    private void setX_start(int x_start)
    {
        this.x_start = x_start;
    }

    private int getX_start()
    {
        return this.x_start;
    }
    private void setGame(boolean isGame)
    {
        this.game = isGame;
    }

    private boolean getGame()
    {
        return this.game;
    }

    @Override
    public void run()//функция работы потока(движения)
    {
        while(getGame())
        {
            if(getSide() == 1)//идет движение влево
            {
                for(int i = getX_start(); i >= 0; i -= bug.getSpeed())//движение жука слева направо
                {
                    if(bug.isLive())//если жук жив
                    {
                        bug.setX(bug.getX() - bug.getSpeed());//сдвигаем Х в зависимости от его скорости
                        bug.setY(bug.getY() + bug.getSpeed() - random.nextInt(5));//сдвигаем Y и вычитаем число от 0 до 4 для разного движения жуков
                        try
                        {
                            Thread.sleep(10);//задержка работы потока(скорость отрисовки движения)
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try
                        {
                            Thread.sleep(bug.getDeath());//задержка работы потока(красное пятно)
                            bug.setLive(true);//воскрешаем его
                            bug.setY(bug.getY_start());//возвращаем на исходную позицию
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();//информация об ошибке
                        }

                    }
                }
                setSide(0);//дальше жук будет двигаться вправо
                setX_start(0);//начиная с нулевого X
            }
            else
            {
                for(int i = getX_start(); i < getX_max() - bug.getX_picture(); i += bug.getSpeed())
                {
                    if(bug.isLive())
                    {
                        bug.setX(bug.getX() + bug.getSpeed());
                        bug.setY(bug.getY() + bug.getSpeed() - random.nextInt(5));
                        try
                        {
                            Thread.sleep(10);
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try
                        {
                            Thread.sleep(bug.getDeath());
                            bug.setLive(true);
                            bug.setY(bug.getY_start());
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }
                setSide(1);
                setX_start(getX_max() - bug.getX_picture());
            }
            if(getY_max() <= bug.getY())//если жук не был убит в течении своего пути
            {
                setX_start(bug.getX_start());//запоминаем начальный Х
                bug.setX(bug.getX_start());//возвращаем исходную позицию и воскрешаем его
                bug.setY(bug.getY_start());
                bug.setLive(true);
            }
        }
    }
}
