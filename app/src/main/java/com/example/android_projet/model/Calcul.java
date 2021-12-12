package com.example.android_projet.model;

import java.util.List;

public class Calcul{
    int op1;
    char operande;
    int op2;
    char[] operandes = {
        'x','+','-'
    };



    public void generateCalcul(int difficulty){
        int range = (int)Math.pow(10,difficulty);
        op1 = (int)(Math.random() * (range));
        op2 = (int)(Math.random() * (range));
        operande = operandes[(int)(Math.random() * (operandes.length))];

    }

    public int getResult(){
        switch(operande){
            case 'x':
                return op1 * op2;
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
        }
        return 0;
    }

    @Override
    public String toString(){
        return op1 + " "+operande+" "+op2;
    }
}
