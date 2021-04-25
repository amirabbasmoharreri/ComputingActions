package com.abbasmoharreri.computingactions.control;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {


    private boolean flag = false;
    private Pattern pattern;
    private Matcher matcher;


    public Regex() {

    }


    public boolean intRegex(String s) {

        pattern = Pattern.compile( "^[0-9]$" );
        matcher = pattern.matcher( s );

        return matcher.matches();
    }

    public boolean emailRegex(String s) {

        pattern = Pattern.compile( "" );
        matcher = pattern.matcher( s );

        return matcher.matches();
    }


    public boolean dateRegex(String s) {

        pattern = Pattern.compile( "^(\\d{2,4})-(\\d{1,2})-(\\d{1,2})" );
        matcher = pattern.matcher( s );

        return matcher.matches();
    }

}
