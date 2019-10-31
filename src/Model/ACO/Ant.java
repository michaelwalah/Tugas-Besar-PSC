/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ACO;

import Model.City;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author William Walah
 */
public class Ant {
    private int[] moveList;
    private BigDecimal result;
    private Set<Integer> tabooList;
    private Random r;
    private int initial;
    
    public Ant(int length){
        this.result = new BigDecimal(0);
        this.moveList = new int[length];
        this.tabooList = new HashSet<Integer>();
        this.r = new Random();
    }
    
    public int[] getList(){
        return this.moveList;
    }
    
    public BigDecimal getResult(){return this.result;}
    
    public void run(City[] city, Pheromone pher, int alpha, int beta){
        int index = 1;
        //1. Pilih kota pertama
        this.initial = r.nextInt(city.length);
        this.moveList[0] = initial;
        this.tabooList.add(initial);
        //2. Semut mulai jalan
        while(this.tabooList.size()!=city.length){
            HashMap<Integer, BigDecimal> distance = new HashMap<>(); //nilai 1/jarak dari setiap tetangga
            int i = 0;
            //3. Catat kota yang dituju beserta distancenya terhadap initial
            for (i = 0; i < city.length; i++) {
                if(!this.tabooList.contains(i)){
                    BigDecimal dist = new BigDecimal(city[this.initial].calculateDistance(city[i]));
                    distance.put(i,dist);
                }
            }
            //System.out.println(distance.toString());
            //4. Hitung total probabilitas
            BigDecimal totalProb = this.calculateTotalProb(distance,pher,alpha,beta);
            BigDecimal[] indexProb = new BigDecimal[distance.size()];
            i = 0;
            //5. Hitung probabilitas masing - masing calon kota yang dapat dituju
            for (Map.Entry<Integer, BigDecimal> entry: distance.entrySet()) {
                indexProb[i] = new BigDecimal(0);
                BigDecimal one = new BigDecimal(1);
                one = one.divide(entry.getValue(),10,RoundingMode.HALF_UP);
                one = one.pow(alpha); 
                
                BigDecimal two = new BigDecimal(pher.getPheromone(this.initial, entry.getKey()));
                two = two.pow(beta);
                indexProb[i] = one.multiply(two);
                indexProb[i] = indexProb[i].divide(totalProb,10,RoundingMode.HALF_UP);
                if(i!=0) indexProb[i] = indexProb[i].add(indexProb[i-1]);
                i++;
            }
            double probValue = r.nextDouble();
            i = 0;
            //6. pilih kota berdasarkan nilai probabilitas
            for (Map.Entry<Integer, BigDecimal> entry: distance.entrySet()) {
                if(probValue < indexProb[i].doubleValue()){
                    this.initial = entry.getKey();
                    break;
                }
                i++;
            }
            //7. tambahkan result atau total jarak yang ditempuh, jejak jalan, dan set kembali posisi saat ini
            this.result = this.result.add(distance.get(this.initial));
            this.moveList[index] = this.initial;
            this.tabooList.add(this.initial);
            index++;
        }
    }
    
    public BigDecimal calculateTotalProb(HashMap<Integer, BigDecimal> dist, Pheromone pher, int alpha, int beta){
        BigDecimal res = new BigDecimal(0);
        for (Map.Entry<Integer, BigDecimal> entry: dist.entrySet()) {
            BigDecimal one = new BigDecimal(1);
            one = one.divide(entry.getValue(),10,RoundingMode.HALF_UP);
            one = one.pow(alpha);
            BigDecimal two = new BigDecimal(pher.getPheromone(this.initial, entry.getKey()));
            two = two.pow(beta);
            res = res.add(one.multiply(two)); 
        }
        return res;
    }
    
    public void reset(){
        this.moveList = new int[this.moveList.length];
        this.result = new BigDecimal(0);
        this.tabooList.clear();
    }
    
    public String getMove(){
        String res = "";
        for (int i = 0; i < moveList.length-1; i++) {
            res += moveList[i] + " - ";
        }
        res += moveList[moveList.length-1];
        return res;
    }
    
    public BigDecimal getResultDistance(){
        return this.result;
    }
}
