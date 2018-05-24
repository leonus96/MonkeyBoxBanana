package com.iaunt.monkey;

/**
 * Created by alanger on 01/05/18.
 */

import org.jpl7.*;

import java.lang.Integer;
import java.lang.String;

public class HandlerProlog {

    public static String actions;

    private static String pm  = "7";
    private static String pc1 = "2";
    private static String pc2 = "12";
    private static String pb = "8";


    private static int xpm;
    private static int xpc1;
    private static int xpc2 ;

    public static String getPb(){
        return pb;
    }

    public static void setPb(String pb_){
        pb = pb_;
    }

    public static String getPm(){
        return pm;
    }

    public static void setPm(String pm_){
         pm = pm_;
    }

    public static String getPc1(){
        return pc1;
    }
    public static void setPc1(String pc1_){
        pc1 = pc1_;
    }

    public static String getPc2(){
        return pc2;
    }
    public static void setPc2(String pc2_){
        pc2 = pc2_;
    }




    public static String translate(char c){
        String resp = "";
        switch(c){
            case 'a' :  resp = "Moverse Derecha";
                        xpm++;
                break;
            case 'b' :  resp = "Moverse Izquierda";
                        xpm--;
                break;
            case 'c' :  resp = "Levantar c1";

                break;
            case 'd' :  resp = "Levantar c2";
                break;
            case 'e' :  resp = "Transportar c1 Derecha";
                        xpc1++;
                        xpm++;
                break;
            case 'f' :  resp = "Transportar c1 Izquierda";
                        xpm--;
                        xpc1--;
                break;
            case 'g' :  resp = "Transportar c2 Derecha";
                        xpm++;
                        xpc2++;
                    break;
            case 'h' :  resp = "Transportar c2 Izquierda";
                        xpm--;
                        xpc2--;
                    break;
            case 'i' :  resp = "Soltar c1";
                break;
            case 'j' :  resp = "Soltar c2";
                break;
            case 'k' :  resp = "Apilar c1 sobre c2";
                break;
            case 'l' :  resp = "Apilar c2 sobre c1";
                break;
            case 'm' :  resp = "Escalar c1";
                break;
            case 'n' :  resp = "Escalar c2";
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
            if(actions.charAt(i) >=97 && actions.charAt(i)<=110)
            {
                temp = actions.charAt(i) + temp;
            }
        }
        actions = temp;
        return flag;
    }

    public static Boolean startActions(){
        Boolean flag;
        xpm = Integer.valueOf(pm);
        xpc1 = Integer.valueOf(pc1);
        xpc2 = Integer.valueOf(pc2);

        String pl   = "consult('MonkeyBoxBanana.pl')";
        //String cons = "solucion(A,V)";
        String cons ="sgte(e("+pm+",0,"+pc1+","+pc2+","+pb+",3,3,3),[e("+pm+",0,"+pc1+","+pc2+","+pb+",3,3,3)],V,[],A)";
        System.out.println(cons);
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
            System.out.println((i+1)+": "+translate(actions.charAt(i))+" - "+xpm+" "+xpc1+" "+xpc2);

        }


    }
}
