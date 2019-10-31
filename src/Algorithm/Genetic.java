/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import Model.City;
import Model.Genetic.Individu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author William Walah
 */
public class Genetic {
    private int[] bestMove;
    private BigDecimal[] fitness;
    private int populationSize;
    private City[] cityList;
    private Individu elitsm;
    private Individu[] population,parent;
    
    public Genetic(){
    }
    
    public void run(ArrayList<Object> cityInput) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.cityList = new City[cityInput.size()];
        int length = 0;
        for(Object o: cityInput){
            this.cityList[length] = (City) o;
            length++;
        }
        this.bestMove = new int[length];
        System.out.print("Insert total population: ");
        int population = Integer.parseInt(br.readLine());
        System.out.print("Insert desired kids per couple: ");
        int totalParent = (int) Math.ceil((population*1.0)/(Integer.parseInt(br.readLine())*1.0));
        this.parent = new Individu[totalParent];
    }
    
    public BigDecimal[] getFitness(){
        return null;
    }
    
    public Individu[] selection(){
        return null;
    }
    
    public Individu[] getChild(){
        return null;
    }
    
    public void correctError(){
        
    }
}
