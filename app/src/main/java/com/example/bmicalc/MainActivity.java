package com.example.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    EditText height,weight,ft;
    ProgressBar pbarResult;
    TextView eW,eH,up,down,adjusted,lblBmi;
    double w,h;
    String we,he,wP,hP;
    double feets;
    double adjust;
    int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weight=(EditText)findViewById(R.id.txtWeight);
        height=(EditText)findViewById(R.id.txtHeight);
        eW=(TextView) findViewById(R.id.txtErrorWeight);
        eH=(TextView) findViewById(R.id.txtErrorHeight);
        ft=(EditText)findViewById(R.id.txtHeightFt);
        up=(TextView)findViewById(R.id.lblWeightUp);
        down=(TextView)findViewById(R.id.lblWeightDown);
        adjusted=(TextView)findViewById(R.id.txtWeightAdjust);
        lblBmi=(TextView)findViewById(R.id.txtBmi);
        final Button calculateBmi =(Button)findViewById(R.id.btnCalculateBmi);
        pbarResult =(ProgressBar)findViewById(R.id.progressBar2);
//        Spinner spnrHeight=(Spinner)findViewById(R.id.lstHeight);



//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
//                R.array.strHeights, android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnrHeight.setAdapter(adapter2);
//        spnrHeight.getOnItemSelectedListener();

        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down.setVisibility(View.INVISIBLE);
                up.setVisibility(View.INVISIBLE);
                pbarResult.setProgress(0);
                pbarResult.getProgressDrawable().setColorFilter(
                        Color.parseColor("#3F51B5"), android.graphics.PorterDuff.Mode.SRC_IN);

                verify();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        hP=parent.getItemAtPosition(position).toString();
//        Toast toast=Toast. makeText(getApplicationContext()," selected", Toast. LENGTH_SHORT);
//        toast. setMargin(50,50);
//        toast. show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast toast=Toast. makeText(getApplicationContext(),"nothing selected", Toast. LENGTH_SHORT);
//        toast. setMargin(50,50);
        toast. show();
    }

    public void verify(){
        if(weight.getText().toString().equals("")){
            eW.setText("Please enter a weight");
//            eW.setTextColor("red");
            if(ft.getText().toString().equals("") && height.getText().toString().equals("")){
                eH.setText("please enter a height");
            }
        }else{
            try{
                convert();
            }catch (Exception e){
                Toast toast1=Toast. makeText(getApplicationContext(),"ex in verify else", Toast. LENGTH_SHORT);
                toast1. show();
            }



        }
    }

    public  void convert(){
//        Toast toast=Toast. makeText(getApplicationContext(),"Convert called", Toast. LENGTH_SHORT);
//        toast. show();
        w=Integer.parseInt(weight.getText().toString());
        h=Integer.parseInt(height.getText().toString());
         feets= (Double.parseDouble(ft.getText().toString())/3.281);
        Toast toast2=Toast. makeText(getApplicationContext(),"feets"+feets, Toast. LENGTH_SHORT);
//        toast2.show();
        hP="in";
        try {
        if( hP.equals("in")){

            h=h/39.37;
            calculate(w,(feets+h));
        }else{
            h=h/100;
            calculate(w,(feets+h));
        }
    }catch (Exception e){
        Toast toast1=Toast. makeText(getApplicationContext(),"ex in convert", Toast. LENGTH_SHORT);
        toast1. show();
    }



    }

    public void adjustCalculate(double x){

        double wei;
        if(x<25){
            wei =25-x;
            up.setVisibility(View.VISIBLE);
        }else {
            wei=x-25;
            down.setVisibility(View.VISIBLE);
        }

        adjust=wei*(feets+h)*(feets+h);
        adjusted.setText(new DecimalFormat("#.0").format(adjust));


    }

    public void calculate(double w,double h){

        double bmi=w/(h*h);
//        Toast.makeText(MainActivity.this,"bmi is : "+bmi,Toast.LENGTH_SHORT).show();
        lblBmi.setText(new DecimalFormat("#.0").format(bmi));
        double x=((bmi-15)/15)*100;
        if(bmi<=18.5){
            pbarResult.getProgressDrawable().setColorFilter(
                    Color.parseColor("#0A0EC9"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(18.5<bmi && bmi<=25.0){
            pbarResult.getProgressDrawable().setColorFilter(
                    Color.parseColor("#5FFF3B"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else{
            pbarResult.getProgressDrawable().setColorFilter(
                    Color.parseColor("#E00606"), android.graphics.PorterDuff.Mode.SRC_IN);
        }


        adjustCalculate(Double.parseDouble(new DecimalFormat("#.0").format(bmi)));
        showProgressBar(x);




    }
    public  void showProgressBar(final double x){
//        progressStatus=x;
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < x) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pbarResult.setProgress(progressStatus);
//                            textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }




}
