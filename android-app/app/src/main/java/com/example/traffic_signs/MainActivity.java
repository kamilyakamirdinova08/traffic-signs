package com.example.traffic_signs;

import android.Manifest; //Түзмөктүн ресурстарына жетүүнү талап кылган операцияларды аткарууга уруксат суроо үчүн
import android.app.Activity; //Колдонуучу интерфейсин түзүү үчүн негизги класс
import android.content.Intent; //Башка иш-аракеттерди же кызматтарды иштетүү үчүн
import android.content.pm.PackageManager; //Колдонмонун уруксаттарын текшерүү үчүн
import android.graphics.Bitmap; //Сүрөттөр менен иштөө үчүн
import android.os.Bundle; //Колдонмонун компоненттеринин ортосунда маалыматтарды өткөрүү үчүн
import android.provider.MediaStore;
import android.view.Menu; //Колдонмо менюсун түзүү үчүн
import android.view.MenuItem; //Меню элементтерин басуу үчүн
import android.view.View; //Колдонуучу интерфейси менен иштөө үчүн
import android.widget.AdapterView; //Тизмедеги нерсени тандоодо окуяларды башкаруу үчүн
import android.widget.Button; //Баскычтарды түзүү үчүн
import android.widget.ListView; //Элементтердин тизмесин көрсөтүү үчүн
import android.widget.Toast;

import androidx.annotation.NonNull; //Кодду аннотациялоо үчүн аннотациялар
import androidx.appcompat.app.ActionBarDrawerToggle; //Жогорку тулбарда навигация тилкесин түзүү үчүн
import androidx.appcompat.app.AppCompatActivity; //Колдонмодогу бардык иш-аракеттер үчүн негизги класс
import androidx.appcompat.widget.Toolbar; //Колдонмодо тулбар түзүү үчүн
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat; //Тартылуучу панель менен иштөө үчүн
import androidx.drawerlayout.widget.DrawerLayout; //Тартылуучу панель түзүү үчүн

import com.example.traffic_signs.databinding.ActivityMainBinding; // activity_main.xml макетти Activity класска байлоо үчүн
import com.example.traffic_signs.settings.SettingsActivity; //Колдонмонун жөндөөлөрү үчүн Activity
import com.example.traffic_signs.utils.AboutActivity; //Activity колдонмо жөнүндө маалымат
import com.example.traffic_signs.utils.CustumArrayAdapter;
import com.example.traffic_signs.utils.ListItemClass; //Тизмедеги элементин билдирген Класс
import com.example.traffic_signs.utils.Material;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList; //Массивдер менен иштөө үчүн
import java.util.Collections; //Маалымат жыйнактары менен иштөө үчүн
import java.util.List; //Для работы с коллекциями данных
//import ai.onnxruntime.OrtEnvironment;
//import ai.onnxruntime.OrtSession;
//import ai.onnxruntime.OnnxTensor;
//import ai.onnxruntime.OrtException;
import java.util.*;
import java.nio.FloatBuffer; //Float типтеги маалымат буферлери менен иштөө үчүн
import android.net.Uri; //URI ресурстар менен иштөө үчүн
import java.io.IOException; //Киргизүү / чыгаруу өзгөчөлүктөрүн иштетүү үчүн
import android.app.Dialog; //Диалог кутучаларын түзүү үчүн
import android.widget.ImageView; //Сүрөттөрдү көрсөтүү үчүн
import android.widget.TextView; //Текстти көрсөтүү үчүн
import android.app.AlertDialog; //Билдирүүлөр менен диалог кутучаларын түзүү
import android.content.DialogInterface; //Диалог терезелериндеги окуяларды башкаруу үчүн
import android.util.Log; //Журналга мүчүлүштүктөрдү оңдоо билдирүүлөрүн жазуу үчүн
import androidx.core.content.ContextCompat; //Колдонмонун контекстине жана Activity контекстине жетүү үчүн






public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    //    private Map<Integer, String> classes = new HashMap<>();
    public static HashMap<Integer, String> classes = new HashMap<>();
    private ListView list;
    private String[] array, arraySecName;
    private CustumArrayAdapter adapter;
    private Toolbar toolbar;
    private int category_index;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int PERMISSON_CODE = 100;
    private static final int VIDEO_REQUEST_CODE = 456;
    private static final int GALLERY_REQUEST_CODE = 789;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private Bitmap imageBitmap;
    float[] floatArray;



    private int[] array_signs_color = new int[]{R.mipmap.twenty, R.mipmap.thirty, R.mipmap.fifty, R.mipmap.s_sixty_, R.mipmap.s_seventy, R.mipmap.s_eighty, R.mipmap.s_ogr_80,
            R.mipmap.s_100, R.mipmap.s_120, R.mipmap.s_obgon_zapret, R.mipmap.s_obgon_gruz_zapret, R.mipmap.s_peresechenie,
            R.mipmap.s_glavnaya_d, R.mipmap.s_ustupi_dorogu, R.mipmap.s_stop, R.mipmap.s_dvijeniye_zapret,
            R.mipmap.s_16_dvij_gr_zapret, R.mipmap.s_17_stop, R.mipmap.s_18_vnimaniye, R.mipmap.s_19_nalevo,
            R.mipmap.s_20, R.mipmap.s_21_krutaya_doroga, R.mipmap.s_22_nerovnaya_doroga, R.mipmap.s_23_skolzkaya_doroga,
            R.mipmap.s_24_sujeniye_dorogi, R.mipmap.s_25_dorojniye_raboty, R.mipmap.s_26_svetoforniye_regulirov, R.mipmap.s_27_peshejod,
            R.mipmap.s_28_deti, R.mipmap.s_29_velik, R.mipmap.s_30_snejinka, R.mipmap.s_31_dikiye_zveri,
            R.mipmap.s_32, R.mipmap.s_33, R.mipmap.s_34, R.mipmap.s_35,
            R.mipmap.s_36, R.mipmap.s_37, R.mipmap.s_38, R.mipmap.s_39,
            R.mipmap.s_40, R.mipmap.s_41, R.mipmap.s_42};
    private int[] array_shtraf = new int[]{R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white};
    private int[] array_pdd = new int[]{R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd,R.drawable.pdd,
            R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd, R.drawable.pdd};
    private List<ListItemClass> listItemMain;
    private ListItemClass listItem;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        list = findViewById(R.id.list);
        listItemMain = new ArrayList<>();
        fillArray(R.string.menu_signs, getResources().getStringArray(R.array.signs), getResources().getStringArray(R.array.signs_2), array_signs_color, 0);
        adapter = new CustumArrayAdapter(this, R.layout.list_view_item_1, listItemMain, getLayoutInflater());
        list.setAdapter(adapter);

        toolbar.setTitle(R.string.menu_signs);
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this); //нажимаем в меню
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Найти кнопку в текущем макете и установить обработчик событий
// Предполагается, что 'buttonn' находится в result_layout.xml

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Text_Content_Activity.class); //Дружище,открой мне это активити
                intent.putExtra("category", category_index);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // Инициализация переменной classes
        classes = new HashMap<>();
        classes.put(0, "Ограничение максимальной скорости (20km/h)");
        classes.put(1, "Ограничение максимальной скорости (30km/h)");
        classes.put(2, "Ограничение максимальной скорости (50km/h)");
        classes.put(3, "Ограничение максимальной скорости (60km/h)");
        classes.put(4, "Ограничение максимальной скорости (70km/h)");
        classes.put(5, "Ограничение максимальной скорости (80km/h)");
        classes.put(6, "Конец зоны ограничение максимальной скорости (80km/h)");
        classes.put(7, "Ограничение максимальной скорости (100km/h)");
        classes.put(8, "Ограничение максимальной скорости (120km/h)");
        classes.put(9, "Обгон запрещен");
        classes.put(10, "Обгон грузовым автомобилям запрещен");
        classes.put(11, "Пересечение со второстепенной дорогой");
        classes.put(12, "Круговое движение");
        classes.put(13, "Уступи дорогу");
        classes.put(14, "Движение без остановки запрещено");
        classes.put(15, "Движение запрещено");
        classes.put(16, "Движение грузовых автомобилей запрещено");
        classes.put(17, "Движение направо");
        classes.put(18, "Прочие опасности");
        classes.put(19, "Опасный поворот (левый)");
        classes.put(20, "Опасный поворот (правый)");
        classes.put(21, "Опасные повороты");
        classes.put(22, "Неровная дорога");
        classes.put(23, "Скользкая дорога");
        classes.put(24, "Сужение дороги");
        classes.put(25, "Дорожные работы");
        classes.put(26, "Светофорное регулирование");
        classes.put(27, "Пешеходный переход");
        classes.put(28, "Дети");
        classes.put(29, "Пересечение с велосипедной дорожкой");
        classes.put(30, "Гололедица");
        classes.put(31, "Дикие животные");
        classes.put(32, "Конец зоны всех ограничений");
        classes.put(33, "Въезд запрещен");
        classes.put(34, "Движение налево");
        classes.put(35, "Движение прямо");
        classes.put(36, "Движение прямо или направо");
        classes.put(37, "Движение прямо или налево");
        classes.put(38, "Объезд препятствия справа");
        classes.put(39, "Объезд препятствия слева");
        classes.put(40, "Главная дорога");
        classes.put(41, "Конец зоны запрещения обгона");
        classes.put(42, "Конец зоны запрещения обгона грузовым автомобилям");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sign) {
            fillArray(R.string.menu_signs, getResources().getStringArray(R.array.signs), getResources().getStringArray(R.array.signs_2), array_signs_color, 0);
        }
         else if (id == R.id.camera) {
//            showImageSourceDialog();
//            Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
//            resultIntent.putExtra("captured_image", imageBitmap);
//            startActivity(resultIntent);\
        //            String url = "https://web.telegram.org/a/#6867647010";
        String botUsername = "TrafficMyBot";
        String url = "https://t.me/" + botUsername;
        // Создайте Intent с ACTION_VIEW и передайте в него URI для открытия в браузере
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // Запустите браузер с заданным URL
        startActivity(intent);
    }

       else if (id == R.id.video) {
            //            String url = "https://web.telegram.org/a/#6867647010";
            String url =  "https://www.google.com/maps/dir//50.906,34.793992/@50.906,34.793992,16z?hl=ru&entry=ttu";
            // Создайте Intent с ACTION_VIEW и передайте в него URI для открытия в браузере
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // Запустите браузер с заданным URL
            startActivity(intent);

        } else if (id == R.id.pdd) {
            fillArraythree(R.string.menu_pdd, getResources().getStringArray(R.array.pdd), array_pdd, 3);
        } else if (id == R.id.shtraf) {
            fillArraythree(R.string.menu_shtraf, getResources().getStringArray(R.array.shtraf), array_shtraf, 4);
        }
        else if (id == R.id.material) {
            Intent intent = new Intent(MainActivity.this, Material.class);
            startActivity(intent);
            return true;
        }
// MainActivity.java
        else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.share) {
            String sub = "Your subject here";
            String msgBody = "Your message Body here";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, msgBody);
            startActivity(Intent.createChooser(intent, "Share via"));
            return true;
        } else if (id == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillArray(int title, String[] nameArray, String[] secName, int[] image, int index) {
        toolbar.setTitle(title);
        if (adapter != null) {
            adapter.clear();
        }
        for (int i = 0; i < nameArray.length; i++) {
            listItem = new ListItemClass();
            listItem.setNameS(nameArray[i]);
            listItem.setSecond_name(secName[i]);
            listItem.setImage_id(image[i]);

            listItemMain.add(listItem);
        }
        if (adapter != null) adapter.notifyDataSetChanged();
        category_index = index;
    }


    private void fillArraythree(int title, String[] nameArray, int[] image, int index) {
        toolbar.setTitle(title);
        if (adapter != null) {
            adapter.clear();
        }
        for (int i = 0; i < nameArray.length; i++) {
            listItem = new ListItemClass();
            listItem.setNameS(nameArray[i]);
            listItem.setImage_id(image[i]);
            listItemMain.add(listItem);
        }
        if (adapter != null) adapter.notifyDataSetChanged();
        category_index = index;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
//    private void openCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
//        } else {
//            Toast.makeText(this, "Камера не доступна на устройстве", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запрашиваем его
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Разрешение уже предоставлено, запускаем камеру
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Добавьте эту строку

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение предоставлено, запускаем камеру
                startCamera();
            } else {
                // Разрешение не предоставлено, обработайте ситуацию соответственно
                Toast.makeText(this, "Доступ к камере отклонен", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "Камера не доступна на устройстве", Toast.LENGTH_SHORT).show();
        }
    }


//    private void showImageSourceDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Выберите источник изображения")
//                .setItems(new CharSequence[]{"Камера", "Галерея"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        switch (which) {
//                            case 0:
//                                // Выбрана камера
//                                openCamera();
//                                break;
//                            case 1:
//                                // Выбрана галерея
//                                openGallery();
//                                break;
//                        }
//                    }
//                });
//        builder.show();
//    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            // Вызываем метод для обработки изображения
//            processImageResult(imageBitmap);
//
//            Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
//            resultIntent.putExtra("captured_image", imageBitmap);
//            startActivity(resultIntent);
//
//        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            Uri selectedImageUri = data.getData();
//            try {
//                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//
//                // Вызываем метод для обработки изображения
//                processImageResult(selectedImage);
//                // Переход к ResultActivity после успешного выбора из галереи
//                Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
//                resultIntent.putExtra("captured_image", selectedImage);
//
//                // Make sure 'recognizedClass' is declared in the correct scope
//                // and contains the recognized class value
//                int recognizedClass = getRecognizedClass(floatArray); // replace with the actual recognized class value
//                resultIntent.putExtra("recognizedClass", recognizedClass);
//
//                startActivity(resultIntent);
//                // Переход к ResultActivity после успешного выбора из галереи
////                Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
////                resultIntent.putExtra("captured_image", selectedImage);
////                startActivity(resultIntent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    private void processImageResult(Bitmap image) {
//        try {
//            // Преобразование изображения к нужному размеру (80x85)
//            Bitmap resizedImage = Bitmap.createScaledBitmap(image, 80, 85, true);
//
//            // Преобразование изображения в ваши входные данные
//            float[] inputData = convertBitmapToFloatArray(resizedImage);
//            long[] inputShape = {1, 80, 85};  // Замените на соответствующие размеры для вашей модели
//
//            // Определение переменной OrtEnvironment
//            OrtEnvironment environment = OrtEnvironment.getEnvironment();
//
//            try {
//                OrtSession session = environment.createSession("../assets/modell.onnx");
//
//                // Создание тензора на основе входных данных
//                FloatBuffer floatBuffer = FloatBuffer.wrap(inputData);
//                OnnxTensor inputTensor = OnnxTensor.createTensor(environment, floatBuffer, inputShape);
//
//                // Выполнение предсказания
//                OrtSession.Result output = session.run(Collections.singletonMap("input", inputTensor));
//
//                // Получение значения тензора
//                Object tensorValue = output.get(0).getValue();
//
//                // Предполагая, что тензор - это массив float
//                if (tensorValue instanceof float[]) {
//                    float[] floatArray = (float[]) tensorValue;
//
//                    // Получение распознанного класса
//                    int recognizedClass = getRecognizedClass(floatArray);
//                    // Откройте ResultActivity и передайте туда необходимые данные
//                    TextView resultTextView = findViewById(R.id.textView2);
//                    // Обновление текста в TextView
//                    resultTextView.setText(classes.get(recognizedClass));
//                    Log.d("MyApp", "Recognized Class: " + recognizedClass);
//
//                    Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
//                    resultIntent.putExtra("recognizedClass", recognizedClass);
//                    startActivity(resultIntent);
//
//                    // Отобразить результаты распознавания
//                    showResultDialog(recognizedClass, image);
//                } else {
//                    // Обработка других типов тензоров, если это необходимо
//                }
//
//                // Ваши дальнейшие действия с результатами
//
//            } catch (OrtException e) {
//                e.printStackTrace();
//                // Обработка ошибок
//            } finally {
//                // Важно освободить ресурсы в блоке finally
//                environment.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Обработка ошибок
//        }
//    }
//
//
//    private void showResultDialog(int recognizedClass, Bitmap image) {
//        // Создание диалогового окна с результатами
//        Dialog resultDialog = new Dialog(this);
//        resultDialog.setContentView(R.layout.result_layout);
//
//        // Получение элементов layout
//        ImageView resultImageView = resultDialog.findViewById(R.id.imageView3);
//        TextView resultTextView = resultDialog.findViewById(R.id.textView2);
//        Button closeButton = resultDialog.findViewById(R.id.buttonn);
//
//        // Заполнение элементов данными
//        resultImageView.setImageBitmap(image);
//        resultTextView.setText("Распознанный класс: " + classes.get(recognizedClass));
//
//        // Обработка нажатия кнопки для закрытия диалогового окна и открытия ResultActivity
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resultDialog.dismiss(); // Закрытие диалогового окна при нажатии кнопки
//
//                // Откройте ResultActivity и передайте туда необходимые данные
//                Intent resultIntent = new Intent(MainActivity.this, ResultActivity.class);
//                resultIntent.putExtra("recognizedClass", recognizedClass);
//                startActivity(resultIntent);
//
//                // Закройте MainActivity, если это необходимо
//                finish();
//            }
//        });
//
//        // Отображение диалогового окна
//        resultDialog.show();
//    }
//
//    private int getRecognizedClass(float[] floatArray) {
//        // Здесь реализуйте вашу логику для определения класса на основе значений тензора
//        // Верните индекс распознанного класса
//        // Пример: рассмотрим, что максимальное значение в массиве соответствует индексу класса
//        int maxIndex = 0;
//        float maxValue = floatArray[0];
//        for (int i = 1; i < floatArray.length; i++) {
//            if (floatArray[i] > maxValue) {
//                maxIndex = i;
//                maxValue = floatArray[i];
//            }
//        }
//        return maxIndex;
//    }
//
//    // Метод для преобразования Bitmap в массив float[]
//    private float[] convertBitmapToFloatArray(Bitmap bitmap) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        int[] pixels = new int[width * height];
//
//        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//
//        float[] floatArray = new float[width * height * 3]; // 3, потому что у нас RGB
//
//        for (int i = 0; i < pixels.length; ++i) {
//            final int val = pixels[i];
//            floatArray[i * 3 + 0] = ((val >> 16) & 0xFF) / 255.0f; // Red
//            floatArray[i * 3 + 1] = ((val >> 8) & 0xFF) / 255.0f;  // Green
//            floatArray[i * 3 + 2] = (val & 0xFF) / 255.0f;         // Blue
//        }
//
//        return floatArray;
//    }
//
//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
}

//    private void processImageResult(Bitmap image) {
//        try {
//            OrtEnvironment environment = OrtEnvironment.getEnvironment();
//            OrtSession session = environment.createSession("../assets/model.onnx");
//
//            // Преобразование изображения к нужному размеру (80x85)
//            Bitmap resizedImage = Bitmap.createScaledBitmap(image, 80, 85, true);
//
//            // Преобразование изображения в ваши входные данные
//            float[] inputData = convertBitmapToFloatArray(resizedImage);
//            long[] inputShape = {1, 80, 85};  // Замените на соответствующие размеры для вашей модели
//
//            // Создание тензора на основе входных данных
//            OnnxTensor inputTensor = OnnxTensor.createTensor(DataType.FLOAT32, inputShape, FloatBuffer.wrap(inputData));
//
//            // Выполнение предсказания
//            OrtSession.Result output = session.run(Collections.singletonMap("input", inputTensor));
//
//            // Получение значения тензора
//            float[] resultData = output.get(0).getTensor().toFloatArray();
//
//            // Ваши дальнейшие действия с результатами
//
//            // Освобождение ресурсов
//            inputTensor.close();
//            output.close();
//            session.close();
//            environment.close();
//        } catch (OrtException e) {
//            e.printStackTrace();
//            // Обработка ошибок
//        }
//    }
//
//
//
//
//
//    // Метод для преобразования Bitmap в массив float[]
//    private float[] convertBitmapToFloatArray(Bitmap bitmap) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        int[] pixels = new int[width * height];
//
//        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//
//        float[] floatArray = new float[width * height * 3]; // 3, потому что у нас RGB
//
//        for (int i = 0; i < pixels.length; ++i) {
//            final int val = pixels[i];
//            floatArray[i * 3 + 0] = ((val >> 16) & 0xFF) / 255.0f; // Red
//            floatArray[i * 3 + 1] = ((val >> 8) & 0xFF) / 255.0f;  // Green
//            floatArray[i * 3 + 2] = (val & 0xFF) / 255.0f;         // Blue
//        }
//
//        return floatArray;
//    }
//
//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }

