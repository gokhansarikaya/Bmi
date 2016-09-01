package cse.homework.gkhn.bmi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Gkhn on 29.10.2015.
 */
public class MainScreen extends Activity{
    Button calculatebutton;
    EditText weight,height,decimal;
    TextView result,error;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainscreen);

        calculatebutton = (Button) findViewById(R.id.buttoncalculate);
        weight = (EditText) findViewById(R.id.editTextweight);
        height = (EditText) findViewById(R.id.editTextheight);
        result = (TextView) findViewById(R.id.textViewresult);
        error = (TextView) findViewById(R.id.textViewerror);
        decimal = (EditText) findViewById(R.id.editTextdecimal);

        calculatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check null
                if(((weight.getText()).toString()).length() <= 0 || ((height.getText()).toString()).length() <=0){
                    result.setText("");
                    error.setText(R.string.blankerror);
                }
                //check 0
                else if((  Integer.parseInt(((weight.getText()).toString()))==0  && Integer.parseInt(decimal.getText().toString()) == 0 )|| Integer.parseInt(((height.getText()).toString())) ==0){
                    result.setText("");
                    error.setText(R.string.zeroerror);
                }
                //no exception /calculate BMI
                else{
                    error.setText("");
                    double w = Double.parseDouble(((weight.getText()).toString()));
                    double h = Double.parseDouble(((height.getText()).toString()))/100;
                    String dec= "0."+decimal.getText().toString();
                    double d= Double.parseDouble(dec);
                    w=w+d;
                    double r = (1.0*w) / (1.0*h*h); //BMI VALUE
                    BigDecimal bd = new BigDecimal(r);
                    bd = bd.setScale(1, RoundingMode.HALF_UP);
                    double bmivalue=bd.doubleValue(); //BMI VALUE AFTER DECIMAL ADJUSTMENT

                            if(bd.doubleValue() < 18.5){
                                result.setText("Your BMI: "+bmivalue+"\n" + "Underweight");
                            }else if (bd.doubleValue() >= 18.5 && bd.doubleValue() <= 24.9){
                                result.setText("Your BMI: "+bmivalue+"\n" + "Normal");
                            }else if (bd.doubleValue() >= 25 && bd.doubleValue() <= 29.9){
                                result.setText("Your BMI: "+bmivalue+"\n" + "Overweight");
                            }else if(bd.doubleValue() >= 30.0){
                                result.setText("Your BMI: "+bmivalue+"\n" + "Obese");
                            }
                }
            }
        });//end of listener
    }//end on OnCreate@Override

    //close keyboard when touch screen
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}//end of MainScreen
