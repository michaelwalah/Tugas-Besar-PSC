/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Genetic;

import Model.City;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author William Walah
 */
public class Individu {
    private int[] solution;
    private double totalDistance;
    
    public Individu(int size){
        this.solution = new int[size];
        this.totalDistance = 0.0;
    }
    
    
    
    public double getTotalDistance(){return this.totalDistance;}
    
    public void set(int[] solution){this.solution=solution;}
    
    public int[] getSolution(){return this.solution;}
    
    public void createSolution(){
        ArrayList<Integer> arl = new ArrayList<>();
        for (int i = 0; i < solution.length; i++) {
            arl.add(i);
        }
        Random r = new Random();
        int i = 0;
        while(!arl.isEmpty()){
           this.solution[i] = arl.remove(r.nextInt(arl.size()));
           i++;
        }
    }
    
    public void countTotal(City[] cityList){
        double res = 0.0;
        int i = 0;
        for (i = 0; i < solution.length-1; i++) {
            res += cityList[solution[i]].calculateDistance(cityList[solution[i+1]]);
        }
        res += cityList[solution[i]].calculateDistance(cityList[solution[0]]);
        this.totalDistance = res;
    }
    
    public void print(){
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i]+" ");
        }
        System.out.println("");
    }
}
