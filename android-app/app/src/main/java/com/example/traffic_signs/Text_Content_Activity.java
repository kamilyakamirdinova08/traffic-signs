package com.example.traffic_signs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Text_Content_Activity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView text_content;
    private Typeface face1; //for font
    private ImageView iContent;
    private SharedPreferences def_pref;
    private int category=0;
    private int position=0;
    private int [] array_signs = {R.string.twenty,R.string.thirty,R.string.fifty,R.string.sixty,R.string.s4,R.string.s5,R.string.s6,R.string.s7,R.string.s8,
            R.string.s9,R.string.s10,R.string.s11,R.string.s12,R.string.s13,R.string.s14,R.string.s15,R.string.s16,R.string.s17,R.string.s18,R.string.s19,
            R.string.s20,R.string.s21,R.string.s22,R.string.s23,R.string.s24,R.string.s25,R.string.s26,R.string.s27,R.string.s28,R.string.s29,R.string.s30,
            R.string.s31,R.string.s32,R.string.s33,R.string.s34,R.string.s35,R.string.s36,R.string.s37,R.string.s38,R.string.s39,R.string.s40,R.string.s41,R.string.s42};
    //private int[] array_camera = {R.string.camera};
    //private int[] array_video = {R.string.video};
    private int[] array_pdd = {R.string.odin,R.string.dva,R.string.p3,R.string.p4,R.string.p5,R.string.p6,R.string.p7,
            R.string.p8,R.string.p9,R.string.p10,R.string.p11,R.string.p12,R.string.p13,R.string.p14,R.string.p15,R.string.p16,R.string.p17,R.string.p18,R.string.p19,
            R.string.p20,R.string.p21,R.string.p22,R.string.p23,R.string.p24,R.string.p25,R.string.p25_2,R.string.p26,R.string.p27};

    private int[] array_shtraf = {R.string.one, R.string.two,R.string.three,R.string.four,R.string.five,R.string.six,R.string.seven,R.string.s180_1,R.string.s180_2,R.string.s181_1,R.string.s182_1,R.string.s182_2,R.string.s182_3,R.string.s182_4,R.string.s182_5,R.string.s182_6,R.string.s182_6,R.string.s183_1,R.string.s183_2,R.string.s184_1,R.string.s184_2,R.string.s184_3,
            R.string.primechanie,R.string.s185_1,R.string.s186_1,R.string.s186_2,R.string.s187_1,R.string.s187_2,R.string.s187_3,R.string.s187_4,R.string.s188_1,R.string.s188_2,R.string.s188_3,R.string.s188_4,R.string.s188_5,R.string.primechnie2,R.string.s189_1,R.string.s189_2,R.string.s_190,R.string.s191,R.string.s_192_1,R.string.s_192_2,R.string.s_192_3,R.string.s_192_4,R.string.primechanie3,R.string.s193_1,R.string.s193_1_1,
            R.string.s193_2,R.string.s193_2_1,R.string.s193_3,R.string.s193_4,R.string.s193_4_1,R.string.s193_5,R.string.s193_6,R.string.s194_1,R.string.s194_2,R.string.s194_3,R.string.s195_1,R.string.s195_2,R.string.s195_3,R.string.s195_4,R.string.s196,R.string.s197,R.string.s198_1,R.string.s198_2,R.string.s198_3,R.string.s199_1,
            R.string.s199_2,R.string.s199_3,R.string.s199_4,R.string.s199_5,R.string.s199_6,R.string.s199_7, R.string.s200_1,R.string.s200_2,R.string.s200_3,R.string.s200_4,R.string.s200_5,R.string.s200_6,R.string.s200_7,R.string.s449_1,R.string.s449_2
    };
    private int[] array_about = {R.string.about};
    private int[] array_image_signs = {R.mipmap.twenty,R.mipmap.thirty,R.mipmap.fifty,R.mipmap.s_sixty_,
            R.mipmap.s_seventy,R.mipmap.s_eighty,R.mipmap.s_ogr_80,R.mipmap.s_100,
            R.mipmap.s_120,R.mipmap.s_obgon_zapret,R.mipmap.s_obgon_gruz_zapret,R.mipmap.s_peresechenie,
            R.mipmap.s_glavnaya_d,R.mipmap.s_ustupi_dorogu,R.mipmap.s_stop,R.mipmap.s_dvijeniye_zapret,
            R.mipmap.s_16_dvij_gr_zapret,R.mipmap.s_17_stop,R.mipmap.s_18_vnimaniye, R.mipmap.s_19_nalevo,
            R.mipmap.s_20_napravo,R.mipmap.s_21_krutaya_doroga,R.mipmap.s_22_nerovnaya_doroga, R.mipmap.s_23_skolzkaya_doroga,
            R.mipmap.s_24_sujeniye_dorogi,R.mipmap.s_25_dorojniye_raboty,R.mipmap.s_26_svetoforniye_regulirov, R.mipmap.s_27_peshejod,
            R.mipmap.s_28_deti,R.mipmap.s_29_velik,R.mipmap.s_30_snejinka, R.mipmap.s_31_dikiye_zveri,
            R.mipmap.s_32,R.mipmap.s_33,R.mipmap.s_34, R.mipmap.s_35,
            R.mipmap.s_36,R.mipmap.s_37,R.mipmap.s_38, R.mipmap.s_39,
            R.mipmap.s_40,R.mipmap.s_41,R.mipmap.s_42
    };
    private int[] array_image_shtraf = {R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white, R.drawable.shtraf_white,
            R.drawable.shtraf_white, R.drawable.shtraf_white};
    private String[] array_title_signs;
    private String[] array_title_pdd;
    private String[] array_title_shtraf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_content);
        init();
        array_title_shtraf = getResources().getStringArray(R.array.shtraf);
        array_title_pdd = getResources().getStringArray(R.array.pdd);
        array_title_signs = getResources().getStringArray(R.array.signs);
        reciveIntent();
    }

    private void reciveIntent() {
        Intent i = getIntent();
        if (i != null)
        {
            category = i.getIntExtra("category",0);
            position = i.getIntExtra("position",0);
        }
        switch (category)
        {
            case 0:
                text_content.setText(array_signs[position]);
                iContent.setImageResource(array_image_signs[position]);
                actionBar.setTitle(array_title_signs[position]);
                break;
            case 1:
                //  text_content.setText(array_camera[position]);


                break;
            case 2:
                //  text_content.setText(array_video[position]);
                break;
            case 3:
                text_content.setText(array_pdd[position]);
                actionBar.setTitle(array_title_pdd[position]);
                break;
            case 4:
                text_content.setText(array_shtraf[position]);
                actionBar.setTitle(array_title_shtraf[position]);
                break;
            case 5:
                text_content.setText(array_about[position]);
                break;
        }
    }
    private void init()
    {
        def_pref = PreferenceManager.getDefaultSharedPreferences(this);

        text_content = findViewById(R.id.text_main_content);
        iContent = findViewById(R.id.imageContent);
        face1 = Typeface.createFromAsset(this.getAssets(), "fonts/NotoSansMono.ttf");
        text_content.setTypeface(face1);
        actionBar = getSupportActionBar();
        String text = def_pref.getString("main_text_size","Средний");
        if(text != null)
        {
            switch (text)
            {
                case "Большой шрифт":
                    text_content.setTextSize(24);
                    break;
                case "Средний шрифт":
                    text_content.setTextSize(14);
                    break;
                case "Маленький шрифт":
                    text_content.setTextSize(10);
                    break;
            }
        }


    }

}
