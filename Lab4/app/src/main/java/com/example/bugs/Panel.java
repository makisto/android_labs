package com.example.bugs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

class Panel extends View//основной класс отрисовки
{
    private int points;
    private int kill_points;
    private int miss_points;
    private int[] sound_killed = new int[5];
    private int[] sound_missed = new int[5];
    private boolean threads;
    private boolean miss;
    private boolean game;
    private Bitmap death_picture;
    private Bitmap[] pictures = new Bitmap[5];
    private MediaPlayer phon;
    private MediaPlayer titles;
    private SoundPool sounds;
    private Bug[] bugs = new Bug[10];
    private Random random = new Random();

    @SuppressLint("ClickableViewAccessibility")
    public Panel(Context context)
    {
        super(context);

        setThreads(true);
        setMiss(true);
        setGame(true);
        setDeathPicture(BitmapFactory.decodeResource(getResources(), R.drawable.p0));
        setStartPoints();
        setBugs();
        setMusic(context);

        this.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                setMiss(true);//изначально промаха не было
                for(int i = 0; i < 10; i++)
                {
                    isKilled(bugs[i], event.getX(), event.getY());//проверка на убийство жука
                }
                if((getMiss()) && (getGame()))//если промахнулись и идет игра
                {
                    sounds.play(sound_missed[random.nextInt(5)], 0.8f, 0.8f, 0, 0, 0f);//играется музыка промаха
                    setAllPoints(-1);//-1 очко
                    setMissPoints(1);//+1 промах
                }
                return false;
            }
        });
        new CountDownTimer(30000, 1000)//таймер работы игры
        {
            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                setGame(false);
            }
        }.start();
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas)//функция прорисовки
    {
        Paint paint = new Paint();//создание кисти
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(20);
        if(getGame())
        {
            if(getThreads())//создание потоков - единократное
            {
                setThreads(false);
                Thread[] th = new Thread[10];
                for(int i = 0; i < 10; i++)
                {
                    th[i] = new Thread(new BugThread(bugs[i], this.getWidth(), this.getHeight(), random.nextInt(2), getGame()));
                    th[i].start();
                }
            }
            isPaint(canvas);
            invalidate();//запрос на перепрорисовку
        }
        else
        {
            showPoints(canvas, paint);//отображение итогов по окончании игры
        }
    }

    private void isPaint(Canvas canvas)//вспомогательная функция отрисовки жуков
    {
        for(int i = 0; i < 10; i++)
        {
            Matrix matrix = new Matrix();//отрисовка с помощью матрицы
            matrix.postScale(0.5f, 0.5f);//масштабирование картинки
            matrix.postTranslate(bugs[i].getX(), bugs[i].getY());//получаем координаты жука
            if(bugs[i].isLive())//если жук жив
            {
                canvas.drawBitmap(bugs[i].live_picture, matrix, null);//рисуем живого жука
            }
            else
            {
                canvas.drawBitmap(bugs[i].death_picture, matrix, null);//иначе - мертвого
            }
        }
    }

    private void isKilled(Bug bug, float x, float y)//функция проверки на убийство жука
    {
        if(getGame())
        {
            if ((x >= bug.getX()) && (x <= (bug.getX_picture() + bug.getX())) && (y >= bug.getY()) && (y <= (bug.getY_picture() + bug.getY())))
            {
                if(bug.isLive())//если попали в жука
                {
                    bug.setLive(false);//жук убит
                    sounds.play(sound_killed[random.nextInt(5)], 1.0f, 1.0f, 0, 0, 0f);//играется музыка убийства жука
                    setMiss(false);//промаха не было
                    setAllPoints(bug.getSpeed());//добавляем очки, соответствующие скорости жука
                    setKillPoints(1);//+1 убийство
                }
            }
        }
    }

    private void showPoints(Canvas canvas, Paint paint)
    {
        phon.stop();//останавливаем фоновую музыку
        int flag = setTitle(canvas, paint);//ставим музыку на итог игры
        titles.start();//запускаем итоговую фоновую музыку
        canvas.drawText("Количество убийств - " + getKillPoints(),//отрисовка итогов игры
                50,canvas.getHeight() / 4 + 100, paint);
        canvas.drawText("Количество промахов - " + getMissPoints(),
                50,canvas.getHeight() / 4 + 200, paint);
        canvas.drawText("Общий счет - " + getAllPoints() + " очков",
                50,canvas.getHeight() / 4 + 300, paint);
        canvas.drawText("Нажмите кнопку Назад для выхода",
                50,canvas.getHeight() / 4 + 400, paint);
        new CountDownTimer(flag * 1000, 1000)//таймер длительности музыки
        {
            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                titles.stop();
            }
        }.start();
    }

    private int setTitle(Canvas canvas, Paint paint)//функция выбора достижения и времени финальной музыки
    {
        if(getAllPoints() == getKillPoints() && getKillPoints() == getMissPoints())
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.wtf);
            canvas.drawText("Получено достижение - НЕО",
                    50,canvas.getHeight() / 4, paint);
            return 10;
        }
        else if(getAllPoints() <= 0)
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.fail);
            canvas.drawText("Получено достижение - Неудачник",
                    50,canvas.getHeight() / 4, paint);
            return 4;
        }
        else if(getKillPoints() >= 100 && getMissPoints() == 0)
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.sniper);
            canvas.drawText("Получено достижение - Снайпер",
                    50,canvas.getHeight() / 4, paint);
            return 6;
        }
        else if(getMissPoints() == 0)
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.missed4);
            canvas.drawText("Получено достижение - Почти, но не то",
                    50,canvas.getHeight() / 4, paint);
            return 1;
        }
        else if(getAllPoints() >= 1000)
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.cool);
            canvas.drawText("Получено достижение - Крутой Парень",
                    50,canvas.getHeight() / 4, paint);
            return 10;
        }
        else
        {
            titles = MediaPlayer.create(this.getContext(), R.raw.title);
            canvas.drawText("Получено достижение - Обычный человек",
                    50,canvas.getHeight() / 4, paint);
            return 5;
        }
    }

    private void setBugs()//инициализация жуков
    {
        pictures[0] = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
        pictures[1] = BitmapFactory.decodeResource(getResources(), R.drawable.p2);
        pictures[2] = BitmapFactory.decodeResource(getResources(), R.drawable.p3);
        pictures[3] = BitmapFactory.decodeResource(getResources(), R.drawable.p4);
        pictures[4] = BitmapFactory.decodeResource(getResources(), R.drawable.p5);
        int k = 0;
        for(int i = 0; i < 10; i++)
        {
            bugs[i] = new Bug(getWidth() + random.nextInt(400), getHeight(), random.nextInt(5) + 3, pictures[k], death_picture, 250);
            if(i % 2 == 1)
            {
                k++;
            }
        }
    }

    private void setMusic(Context context)//инициализация музыки
    {
        sounds = new SoundPool(20, AudioManager.STREAM_MUSIC,0);
        phon = MediaPlayer.create(context, R.raw.phon);
        phon.start();

        sound_killed[0] = sounds.load(context, R.raw.killed1, 1);
        sound_killed[1] = sounds.load(context, R.raw.killed2, 1);
        sound_killed[2] = sounds.load(context, R.raw.killed3, 1);
        sound_killed[3] = sounds.load(context, R.raw.killed4, 1);
        sound_killed[4] = sounds.load(context, R.raw.killed5, 1);

        sound_missed[0] = sounds.load(context, R.raw.missed1, 1);
        sound_missed[1] = sounds.load(context, R.raw.missed2, 1);
        sound_missed[2] = sounds.load(context, R.raw.missed3, 1);
        sound_missed[3] = sounds.load(context, R.raw.missed4, 1);
        sound_missed[4] = sounds.load(context, R.raw.missed5, 1);
    }

    private void setStartPoints()//инициализация начальных очков
    {
        this.points = 0;
        this.kill_points = 0;
        this.miss_points = 0;
    }

    private void setAllPoints(int points)
    {
        this.points += points;
    }

    private int getAllPoints()
    {
        return this.points;
    }

    private void setKillPoints(int kill_points)
    {
        this.kill_points += kill_points;
    }

    private int getKillPoints()
    {
        return this.kill_points;
    }

    private void setMissPoints(int miss_points)
    {
        this.miss_points += miss_points;
    }

    private int getMissPoints()
    {
        return this.miss_points;
    }

    private void setThreads(boolean threads)
    {
        this.threads = threads;
    }

    private boolean getThreads()
    {
        return this.threads;
    }

    private void setMiss(boolean miss)
    {
        this.miss = miss;
    }

    private boolean getMiss()
    {
        return this.miss;
    }

    private void setGame(boolean game)
    {
        this.game = game;
    }

    private boolean getGame()
    {
        return this.game;
    }

    private void setDeathPicture(Bitmap death_picture)
    {
        this.death_picture = death_picture;
    }
}
