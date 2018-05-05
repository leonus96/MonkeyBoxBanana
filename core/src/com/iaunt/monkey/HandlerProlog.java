package com.iaunt.monkey;

/**
 * Created by alanger on 01/05/18.
 */

import org.jpl7.*;

import java.lang.String;

public class HandlerProlog {

    public static String actions;

    public static String translate(char c){
        String resp = "";
        switch(c){
            case 'a' :  resp = "Ir a caja 1";
                break;
            case 'b' :  resp = "Ir a caja 2";
                break;
            case 'c' :  resp = "Llevar caja 1 a caja 2";
                break;
            case 'd' :  resp = "Llevar caja 2 a caja 1";
                break;
            case 'e' :  resp = "Llevar caja 1 debajo de Bananas";
                break;
            case 'f' :  resp = "Llevar caja 2 debajo de Bananas";
                break;
            case 'g' :  resp = "Montar caja 1 sobre caja 2";
                break;
            case 'h' :  resp = "Montar caja 2 sobre caja 1";
                break;
            case 'i' :  resp = "Trepar caja 1";
                break;
            case 'j' :  resp = "Trepar caja 2";
                break;
            default  :  resp = String.valueOf(c) + "Traduccion No Encontrada";
                break;
        }

        return resp;
    }

    public static Boolean filtrar(){
        Boolean flag;
        String temp="";
        flag =(actions.length()>0) ? true : false;
        for(int i=0;i<actions.length();i++){
            if(actions.charAt(i) >=97 && actions.charAt(i)<=106)
            {
                temp = actions.charAt(i) + temp;
            }
        }
        actions = temp;
        return flag;
    }

    public static Boolean startActions(){
        Boolean flag;
        String pl   = "consult('monkeyBoxBanana.pl')";
        String cons = "solucion(A)";
        Query q     = new Query(pl);
        flag = q.hasSolution() ? true : false ;
        if(flag){
            q   = new Query(cons);
            actions= String.valueOf(q.oneSolution().get("A"));
            filtrar();
        }
        return flag;
    }


    public static void main (String[] arg) throws InterruptedException {

        startActions();

        for(int i=0;i<actions.length();i++){
            System.out.println((i+1)+": "+translate(actions.charAt(i)));
        }


    }
}
