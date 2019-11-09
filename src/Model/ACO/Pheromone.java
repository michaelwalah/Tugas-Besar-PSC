package Model.ACO;


import Model.ACO.Ant;
import Model.City;
import Model.City;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author William Walah
 */
public class Pheromone {
    private double[][] pher;
    private double evap;
    
    public Pheromone(int length){
        this.initializePheromone(length);
        this.evap = 0.4;
    }
    
    public void initializePheromone(int length){
        pher = new double[length][length];
        for (int i = 0; i < pher.length; i++) {
            for (int j = 0; j < pher.length; j++) {
                if(i==j) pher[i][j] =-1;
                else pher[i][j] = 0.0001;
            }
        }
    }
    
    public double getPheromone(int x, int y){
        return this.pher[x][y];
    }
    
    public void updatePher(City[] city, Ant[] ant){
        this.updateEvap();
        for (int i = 0; i < ant.length; i++) {
            int[] moveList = ant[i].getList();
            BigDecimal res = ant[i].getResult();
            int j = 0;
            for (j = 0; j < moveList.length-1; j++) {
                this.update(moveList[j], moveList[j+1], res);
            }
            this.update(moveList[j], moveList[0], res);
        }
    }
    
    public void update(int x, int y, BigDecimal res){
        res = res.divide(BigDecimal.ONE,10,RoundingMode.HALF_UP);
        this.pher[x][y] += res.doubleValue();
        this.pher[y][x] += res.doubleValue();
    }
    
    public void updateEvap(){
        for (int i = 0; i < pher.length; i++) {
            for (int j = 0; j < pher.length; j++) {
                pher[i][j] *= this.evap;
            }
        }
    }
}
