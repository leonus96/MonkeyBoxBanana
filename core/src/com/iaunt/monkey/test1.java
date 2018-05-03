package com.iaunt.monkey;

/**
 * Created by alanger on 01/05/18.
 */

import org.jpl7.*;

import java.lang.*;

public class test1 {


    public static void main (String[] arg) throws InterruptedException {
        System.out.println("Hola");

        String t1 = "consult('laberinto.pl')";
        Query q1;
            q1 = new Query(t1);
        String solu1 = "";
        char p = 'x';
        System.out.println( t1 + " " + (q1.hasSolution() ? "correcto" : "fallo") );
        String t4 = "solucion(A)";
        Query q4 = new Query(t4);
        System.out.println( "Solucion para t4 " + t4 );
        String sol=String.valueOf(q4.oneSolution().get("A"));

        for(int i=0;i<sol.length();i++){
            if(sol.charAt(i) >=97 && sol.charAt(i)<=122)
            {
                solu1 = solu1+sol.charAt(i);
            }
        }

        //procesa solu1
        for(int i=solu1.length()-1;i>=0;i--)
        {

            switch(solu1.charAt(i)){

                case 'i': System.out.println("IR A LA IZQUIERDA");

                    break;

                case 'd': System.out.println("IR A LA DERECHA");

                    break;

                case 's': System.out.println("IR ARRIBA");

                    break;

                case 'b': System.out.println("IR ABAJO");
                    break;
            }
        }



    }
}
