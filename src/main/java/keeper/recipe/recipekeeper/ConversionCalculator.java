package keeper.recipe.recipekeeper;

import java.util.ArrayList;

/*
* Conversion calculator that takes input from the currently displayed recipe and translates
* it into other measurement types. Will also take user input for on the fly conversions however
* this feature has not been added yet.
* */

public class ConversionCalculator {

    CommonDataContainer CDC = new CommonDataContainer();

    public void popupCalc(String convert, String to){

        String selected = convert;
        int y = selected.indexOf(' ') + 1;
        selected = selected.substring(y, selected.indexOf(' ', y));
        String ingredName = convert.substring(convert.indexOf(' ', y));
        switch(selected){
            case("tsp"):{
                CDC.setPopupConversionValue(tspOps(convert, to) + " " + to + ingredName);
                break;}
            case("tbsp"):{
                CDC.setPopupConversionValue(tbspOps(convert, to) + " " + to + ingredName);
                break;}
            case("floz"):{
                CDC.setPopupConversionValue(flozOps(convert, to) + " " + to + ingredName);
                break;}
            case("cup"):{
                CDC.setPopupConversionValue(cupOps(convert, to) + " " + to + ingredName);
                break;}
            case("pnt"):{
                CDC.setPopupConversionValue(pntOps(convert, to) + " " + to + ingredName);
                break;}
            case("qrt"):{
                CDC.setPopupConversionValue(qrtOps(convert, to) + " " + to + ingredName);
                break;}
            case("gm"):{
                CDC.setPopupConversionValue(gmOps(convert, to) + " " + to + ingredName);
                break;}
            case("oz"):{
                CDC.setPopupConversionValue(ozOps(convert, to) + " " + to + ingredName);
                break;}
            case("lbs"):{
                CDC.setPopupConversionValue(lbsOps(convert, to) + " " + to + ingredName);
                break;}
            default:{break;}
        }
    }

    public double stringParse(String convert){
        double value = 0;
        int whole;
        double numer;
        double denom;
        if(!convert.contains("-") && !convert.contains("/")){
            whole = Integer.parseInt(convert.substring(0,convert.indexOf(' ')));
            value = whole;
        }
        if(convert.contains("-") && convert.contains("/")){
            whole = Integer.parseInt(convert.substring(0,convert.indexOf('-')));
            numer = Integer.parseInt(convert.substring(convert.indexOf('-') + 1, convert.indexOf('/')));
            denom = Integer.parseInt(convert.substring(convert.indexOf('/') + 1, convert.indexOf(' ')));
            value = numer / denom + whole;
        }
        if(convert.contains("/") && !convert.contains("-")) {
            numer = Integer.parseInt(convert.substring(0, convert.indexOf('/')));
            denom = Integer.parseInt(convert.substring(convert.indexOf('/') + 1, convert.indexOf(' ')));
            value = numer / denom;
        }


        return value;
    }

    public double tspOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to){
            case("tbsp"):{
                result = value * 0.33333333;
                break;}
            case("floz"):{
                result = value * 0.166667;
                break;}
            case("cup"):{
                result = value * 0.0205372;
                break;}
            case("pnt"):{
                result = value * 0.0104167;
                break;}
            case("qrt"):{
                result = value * 0.00520833;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double tbspOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to){
            case("tsp"):{
                result = value * 3;
                break;}
            case("floz"):{
                result = value * 0.5;
                break;}
            case("cup"):{
                result = value * 0.0616115;
                break;}
            case("pnt"):{
                result = value * 0.03125;
                break;}
            case("qrt"):{
                result = value * 0.015625;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double flozOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to){
            case("tsp"):{
                result = value * 6;
                break;}
            case("tbsp"):{
                result = value * 2;
                break;}
            case("cup"):{
                result = value * 0.123223;
                break;}
            case("pnt"):{
                result = value * 0.0625;
                break;}
            case("qrt"):{
                result = value * 0.0625;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double cupOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to){
            case("tsp"):{
                result = value * 48.70;
                break;}
            case("tbsp"):{
                result = value * 16.25;
                break;}
            case("floz"):{
                result = value * 8;
                break;}
            case("pnt"):{
                result = value * 0.5;
                break;}
            case("qrt"):{
                result = value * 0.25;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double pntOps(String convert, String to){
        double result = 0;
        double value = stringParse(convert);

        switch(to){
            case("tsp"):{
                result = value * 96;
                break;}
            case("tbsp"):{
                result = value * 32;
                break;}
            case("floz"):{
                result = value * 16;
                break;}
            case("cup"):{
                result = value * 2;
                break;}
            case("qrt"):{
                result = value * 0.5;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double qrtOps(String convert, String to){
        double result = 0;
        double value = stringParse(convert);

        switch(to){
            case("tsp"):{
                result = value * 192;
                break;}
            case("tbsp"):{
                result = value * 64;
                break;}
            case("floz"):{
                result = value * 32;
                break;}
            case("cup"):{
                result = value * 4;
                break;}
            case("pnt"):{
                result = value * 2;
                break;}
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double gmOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to) {
            case ("oz"): {
                result = value * 0.035274;
                break;
            }
            case ("lbs"): {
                result = value * 0.0022046249999752;
                break;
            }
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double ozOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to) {
            case ("gm"): {
                result = value * 28.349553705630622602;
                break;
            }
            case ("lbs"): {
                result = value * 0.062500067418750054893;
                break;
            }
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public double lbsOps(String convert, String to){
        double result;
        double value = stringParse(convert);

        switch(to) {
            case ("gm"): {
                result = value * 453.592;
                break;
            }
            case ("oz"): {
                result = value * 16;
                break;
            }
            default:{
                result = 0;
                break;}
        }
        return result;
    }

    public void packageArrayForDisplay(ArrayList<String> arrayToString){
        String displayString = "";
        for(int i = 0; i < arrayToString.size(); i++){
            displayString = displayString + arrayToString.get(i).toString() + "\n";
        }
    }

}
