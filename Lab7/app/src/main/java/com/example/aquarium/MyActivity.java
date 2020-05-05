package com.example.aquarium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.BatteryManager;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import java.util.Random;


public class MyActivity extends WallpaperService{

    private Bitmap pictures;
    private Fish[] fishes = new Fish[5];
    private Random random = new Random();
    private int percentage;
    public void onCreate()
    {
        super.onCreate();
    }
    public void onDestroy()
    {
        super.onDestroy();
    }
    public Engine onCreateEngine()
    {
        return new MyWallpaperEngine();
    }
    class MyWallpaperEngine extends WallpaperService.Engine {

        // Handler – ставит потоки в очередь на выполнение
        private final Handler handler = new Handler();
        // drawRunner сам поток
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw(); // метод прорисовки обоев
            }
        };


        private boolean visible = true;// обои видны или нет-true|false
        private Bitmap bg;
        private Bitmap bg1;
        private Bitmap bg8;
        private Bitmap bg80;
        private Bitmap bg6;
        private Bitmap bg60;
        private Bitmap bg4;
        private Bitmap bg40;
        private Bitmap bg2;
        private Bitmap bg20;

        MyWallpaperEngine() {
        }

        //вызывается при создании обоев
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override //вызывается при изменении видимости (повороте)
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            // if screen wallpaper is visible then draw the image otherwise do not draw
            if (visible) // если обои видны
            {
                handler.post(drawRunner); // поставить прорисовку в очередь
            } else {
                handler.removeCallbacks(drawRunner); //убрать прорисовку из очереди
            }
        }

        @Override // вызывается при удалении полотна прорисовки
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner); //убрать прорисовку из очереди
        }

        //Вызывается при смене рабочих столов
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float
                yStep, int xPixels, int yPixels) {
            handler.post(drawRunner); // поставить прорисовку в очередь
        }

            /// Прорисовка
            public void draw() {
            // получаем полотно рабочего стола
                final SurfaceHolder holder = getSurfaceHolder();
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas(); //Захват полотна(только наш процесс рисует)
                    if (canvas != null) //если удалось захватить
                    { //Рисуем на нем, что хотим
                        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE); //получение BatteryManager для управления состоянием батареи
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) { //если версия sdk >= версии ос андроида 2014 года
                             percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); //оставшийся заряд
                        }

                        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg); //достаем изображения фона
                        bg8 = BitmapFactory.decodeResource(getResources(), R.drawable.bg8);
                        bg6 = BitmapFactory.decodeResource(getResources(), R.drawable.bg6);
                        bg4 = BitmapFactory.decodeResource(getResources(), R.drawable.bg4);
                        bg2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
                        int width = bg.getWidth(); //определяем ширину каждого фона
                        int width6 = bg6.getWidth();
                        int width8 = bg8.getWidth();
                        int width4 = bg4.getWidth();
                        int width2 = bg2.getWidth();
                        int halfWidth = width / 2; //уменьшаем ширину фона
                        int halfWidth6 = width6 / 2;
                        int halfWidth8 = width8 / 2;
                        int halfWidth4 = width4 / 2;
                        int halfWidth2 = width2 / 2;
                        if(percentage > 80 && percentage <= 100) { //определяем уровень заряда
                            bg1 = Bitmap.createScaledBitmap(bg, halfWidth, 1440, true); //меняем размеры фона

                            pictures = BitmapFactory.decodeResource(getResources(), R.drawable.fish); //достаем изображение рыбы

                            fishes[0] = new Fish(50 + random.nextInt(500), 200, random.nextInt(8) + 5, pictures);//опеределяем параметры для рыб
                            fishes[1] = new Fish(100 + random.nextInt(500), 900, random.nextInt(8) + 5, pictures);
                            fishes[2] = new Fish(10 + random.nextInt(500), 1100, random.nextInt(8) + 5, pictures);
                            fishes[3] = new Fish(140 + random.nextInt(500), 400, random.nextInt(8) + 5, pictures);
                            fishes[4] = new Fish(90 + random.nextInt(500), 600, random.nextInt(8) + 5, pictures);

                            Paint p = new Paint();
                            canvas.drawBitmap(bg1, 0, 0, p);//рисуем фон

                        }

                        if(percentage > 60 && percentage <= 80) {
                            bg80 = Bitmap.createScaledBitmap(bg8, halfWidth8, 1440, true);

                            pictures = BitmapFactory.decodeResource(getResources(), R.drawable.fish);

                            fishes[0] = new Fish(50 + random.nextInt(500), 400, random.nextInt(8) + 5, pictures);
                            fishes[1] = new Fish(100 + random.nextInt(500), 900, random.nextInt(8) + 5, pictures);
                            fishes[2] = new Fish(10 + random.nextInt(500), 1100, random.nextInt(8) + 5, pictures);
                            fishes[3] = new Fish(140 + random.nextInt(500), 700, random.nextInt(8) + 5, pictures);
                            fishes[4] = new Fish(90 + random.nextInt(500), 600, random.nextInt(8) + 5, pictures);

                            Paint p = new Paint();
                            canvas.drawBitmap(bg80, 0, 0, p);

                        }

                        if(percentage > 40 && percentage <= 60) {
                            bg60 = Bitmap.createScaledBitmap(bg6, halfWidth6, 1440, true);

                            pictures = BitmapFactory.decodeResource(getResources(), R.drawable.fish);

                            fishes[0] = new Fish(50 + random.nextInt(500), 800, random.nextInt(8) + 5, pictures);
                            fishes[1] = new Fish(100 + random.nextInt(500), 900, random.nextInt(8) + 5, pictures);
                            fishes[2] = new Fish(10 + random.nextInt(500), 1100, random.nextInt(8) + 5, pictures);
                            fishes[3] = new Fish(140 + random.nextInt(500), 1000, random.nextInt(8) + 5, pictures);
                            fishes[4] = new Fish(90 + random.nextInt(500), 600, random.nextInt(8) + 5, pictures);

                            Paint p = new Paint();
                            canvas.drawBitmap(bg60, 0, 0, p);

                        }

                        if(percentage > 20 && percentage <= 40) {
                            bg40 = Bitmap.createScaledBitmap(bg4, halfWidth4, 1440, true);

                            pictures = BitmapFactory.decodeResource(getResources(), R.drawable.fish);

                            fishes[0] = new Fish(50 + random.nextInt(500), 980, random.nextInt(8) + 5, pictures);
                            fishes[1] = new Fish(100 + random.nextInt(500), 900, random.nextInt(8) + 5, pictures);
                            fishes[2] = new Fish(10 + random.nextInt(500), 1100, random.nextInt(8) + 5, pictures);
                            fishes[3] = new Fish(140 + random.nextInt(500), 1000, random.nextInt(8) + 5, pictures);
                            fishes[4] = new Fish(90 + random.nextInt(500), 1200, random.nextInt(8) + 5, pictures);

                            Paint p = new Paint();
                            canvas.drawBitmap(bg40, 0, 0, p);

                        }

                        if (percentage >= 0 && percentage <= 20) {
                            bg20 = Bitmap.createScaledBitmap(bg2, halfWidth2, 1440, true);
                            pictures = BitmapFactory.decodeResource(getResources(),R.drawable.fishdead);
                            fishes[0] = new Fish(200, 1300, 0, pictures);
                            fishes[1] = new Fish(300, 1100, 0, pictures);
                            fishes[2] = new Fish(10, 1100, 0, pictures);
                            fishes[3] = new Fish(400, 1000, 0, pictures);
                            fishes[4] = new Fish(450, 1200, 0, pictures);

                            Paint p = new Paint();
                            canvas.drawBitmap(bg20, 0, 0, p);
                        }

                        for (int i = 0; i < 5; i++) {
                            Matrix matrix = new Matrix();//отрисовка с помощью матрицы
                            matrix.postScale(0.1f, 0.1f);//масштабирование картинки
                            matrix.postTranslate(fishes[i].getX(), fishes[i].getY());//получаем координаты

                            canvas.drawBitmap(fishes[i].pictures, matrix, null);//рисуем
                        }

                    }
                } finally {
                    if (canvas != null) //Разблокируем полотно и выводим его на экран
                        holder.unlockCanvasAndPost(canvas);
                }
                handler.removeCallbacks(drawRunner); //убрать прорисовку из очереди
                if (visible) {
                    handler.postDelayed(drawRunner, 70); //поставить прорисовку в
                    //  очередь через 70 мс.
                }
            }
        }
}




