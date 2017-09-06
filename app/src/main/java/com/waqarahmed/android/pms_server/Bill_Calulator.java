package com.waqarahmed.android.pms_server;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by waqar on 6/14/2016.
 */
public class Bill_Calulator {
    String amount;
    String bill;
    Double unit;
    double temp;
    double total = 0;


    public String getAmount(String Unit) {
        unit=Double.parseDouble(Unit);
        total = 0;
        temp = 0;

        if(unit >=1 && unit <=100){
            total = total + (unit * 5.79);
            return amount =Double.toString(total);
        }else if (unit >100 && unit<=200){
            temp = unit - 100;
            total = total + (100 * 5.79) + (temp * 8.11);
            return amount =Double.toString(total);
        }else if (unit >200 && unit<=300){
            temp = unit - 200;
            total = total + (200 * 8.11) + (temp * 10.20);
            return amount =Double.toString(total);
        }else if (unit >300 && unit<=700){
            temp = unit - 300;
            total = total + (300 * 10.20) + (temp * 16);
            return amount =Double.toString(total);
        }else if (unit >700){
            temp = unit - 700;
            total = total + (700 * 16) + (temp * 18);
            return amount =Double.toString(total);
        }
        return amount="0";
    }

    public String getBill(String unit_amount) {
       // bill=Double.toString(Double.parseDouble(unit_amount)*30);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        int day = localCalendar.get(Calendar.DATE);
        int exp_unit = (int) ((31 * unit) / day);
        double exp_total = 0;
        temp = 0;

        if(exp_unit >=1 && exp_unit <=100){
            exp_total = exp_total + (exp_unit * 5.79);
            return bill =Double.toString(exp_total);
        }else if (exp_unit >100 && exp_unit<=200){
            temp = exp_unit - 100;
            exp_total = exp_total + (100 * 5.79) + (temp * 8.11);
            return bill =Double.toString(exp_total);
        }else if (exp_unit >200 && exp_unit<=300){
            temp = exp_unit - 200;
            exp_total = exp_total + (200 * 8.11) + (temp * 10.20);
            return bill =Double.toString(exp_total);
        }else if (exp_unit >300 && exp_unit<=700){
            temp = exp_unit - 300;
            exp_total = exp_total + (300 * 10.20) + (temp * 16);
            return bill =Double.toString(exp_total);
        }else if (exp_unit >700){
            temp = exp_unit - 700;
            exp_total = exp_total + (700 * 16) + (temp * 18);
            return bill =Double.toString(exp_total);
        }
        return "0";
    }
}
