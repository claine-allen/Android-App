package com.example.steelzest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText txtamt,txtconsumption,txtperiod;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button aboutButton = findViewById(R.id.aboutButton);
        Button servicesButton = findViewById(R.id.servicesButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button quoteButton = findViewById(R.id.quoteButton);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);

       txtamt=findViewById(R.id.billamt);
       txtconsumption=findViewById(R.id.consumption);
       txtperiod=findViewById(R.id.period);

       Button calculate=findViewById(R.id.calculate);

        try{
            calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve input values from EditTexts.
                    String billAmountStr = txtamt.getText().toString();
                    String consumptionStr = txtconsumption.getText().toString();
                    String periodStr = txtperiod.getText().toString();

                    // Convert the input strings to numeric values (you can add validation here).
                    double billAmount = Double.parseDouble(billAmountStr);
                    double consumption = Double.parseDouble(consumptionStr);
                    double period = Double.parseDouble(periodStr);

                    // Perform your calculations here.
                    double unitsPerDay=consumption/period;
                    double systemCapacity = unitsPerDay/4;
                    double areaRequired = systemCapacity*10;
                    double annualUnitsGenerated=systemCapacity*4*300;

                    //System Cost=(System Capacity)*1000*unit cost   [1-3kW= ₹80]   [4-6kW= ₹75]   [7-8kW= ₹72]   [9-15kW= ₹68]   [>15kW= ₹62]
                    double systemCost=calcSystemCost(unitsPerDay,systemCapacity);

                    //Savings in a year= Total units generated*unit rate   [Residential=4.2 units]   [Commercial=6units]   [Industrial=8units]
                    //flag=1[residential], flag=2[commercial],   flag=3[industrial]
                    double annualSavings=calcAnnualSavings(annualUnitsGenerated,1);

                    double lifetimeValue = annualSavings*25;
                    double paybackPeriod = systemCost/annualSavings;

                    // Update TextViews with the results.
                    tv1.setText(String.valueOf(systemCapacity));
                    tv2.setText(String.valueOf(areaRequired));
                    tv3.setText(String.valueOf(systemCost));
                    tv4.setText(String.valueOf(annualSavings));
                    tv5.setText(String.valueOf(lifetimeValue));
                    tv6.setText(String.valueOf(paybackPeriod));
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"The button isn't working because of"+e,Toast.LENGTH_LONG).show();
            Log.e("MainActivity","error"+e);
        }

        // Set click listeners for buttons
        quoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuoteActivity();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutUsActivity();
            }
        });

        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServicesActivity();
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContactInfoActivity();
            }
        });
    }


    // Define methods for various actions


    private void openAboutUsActivity() {
        // Create an Intent to start the "About Us" activity
        Intent intent = new Intent(MainActivity.this, AboutUs.class);
        startActivity(intent);
    }

    private void openQuoteActivity() {
        // Create an Intent to start the "Quote" activity
        Intent intent = new Intent(MainActivity.this, Quote.class);
        startActivity(intent);
    }

    private void openServicesActivity() {
        // Create an Intent to start the "Services" activity
        Intent intent = new Intent(MainActivity.this, Services.class);
        startActivity(intent);
    }

    private void openContactInfoActivity() {
        // Create an Intent to start the "Contact Info" activity
        Intent intent = new Intent(MainActivity.this, ContactInfo.class);
        startActivity(intent);
    }



    //System Cost=(System Capacity)*1000*unit cost   [1-3kW= ₹80]   [4-6kW= ₹75]   [7-8kW= ₹72]   [9-15kW= ₹68]   [>15kW= ₹62]
    private double calcSystemCost(double unitsPerDay,double systemCapacity){
        double unitCost;
        if(unitsPerDay>=1 && unitsPerDay<=3){
            unitCost=80;
            return systemCapacity*1000*unitCost;
        }
        else if (unitsPerDay>=4 && unitsPerDay<=6) {
            unitCost=75;
           return systemCapacity*1000*unitCost;
        }
        else if (unitsPerDay>=7 && unitsPerDay<=8) {
            unitCost=72;
            return systemCapacity*1000*unitCost;
        }
        else if (unitsPerDay>=9 && unitsPerDay<=15) {
            unitCost=68;
            return systemCapacity*1000*unitCost;
        }
        else if (unitsPerDay>15) {
            unitCost=62;
            return systemCapacity*1000*unitCost;
        }
        return -1;
    }


    //Savings in a year= Total units generated*unit rate   [Residential=4.2 units]   [Commercial=6units]   [Industrial=8units]
    //flag=1[residential], flag=2[commercial],   flag=3[industrial]
    private double calcAnnualSavings(double annualUnitsGenerated,int flag){
        double unitRate=0;
        if(flag==1){
            unitRate=4.2;
           return annualUnitsGenerated*unitRate;
        }
        else if(flag==2){
            unitRate=6;
            return annualUnitsGenerated*unitRate;
        } else if (flag==3) {
            unitRate=8;
            return annualUnitsGenerated*unitRate;
        }
        return -1;
    }

}