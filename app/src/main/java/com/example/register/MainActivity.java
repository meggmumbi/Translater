package com.example.register;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  EditText text;
  Button bTranslate;
  Button exit;
  RecyclerView recyclerView;
    List<String> wordlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText)findViewById(R.id.Word2T);
        bTranslate=(Button)findViewById(R.id.buttonTranslate);
        exit=(Button)findViewById(R.id.buttonExit);
        recyclerView=(RecyclerView)findViewById(R.id.r);

        wordlist = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TranslaterAdapter translationAdapter = new TranslaterAdapter(this, wordlist);
        recyclerView.setAdapter(translationAdapter);

        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();

            }
        });

        bTranslate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String input = text.getText().toString();

                if(TextUtils.isEmpty(input))
                {
                    Toast.makeText(MainActivity.this, "Enter a word for translation", Toast.LENGTH_LONG).show();
                    return;
                }

                List<String> listofwords = translationSearch(input);
                translationAdapter.addList(listofwords);

            }
        });
    }

    private List<String> translationSearch(String word)
    {
        String line = "";
        List<String> wordList = new ArrayList<>();

        try
        {
            InputStream inputStream = getAssets().open("dictionary.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while((line = bufferedReader.readLine()) != null)
            {
                List<String> words = Arrays.asList(line.split(","));

                if(words.size() == 2)
                {
                    if(word.toLowerCase().equals(words.get(1).toLowerCase()))
                    {
                        wordList.add(words.get(0));
                    }
                }

                if(wordList.size() == 10)
                {
                    break;
                }
            }
            bufferedReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return wordList;
    }



    }




