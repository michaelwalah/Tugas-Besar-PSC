/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import Model.ACO.Ant;
import Model.City;
import Model.ACO.Pheromone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author William Walah
 */
public class AntColony {
    private City[] city;
    private int alpha;
    private int beta;
    private Pheromone pher;
    private Ant[] agents;
    
    public AntColony(){
    }
    
    public void run(ArrayList<Object> cityInput) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.city = new City[cityInput.size()];
        int length = 0;
        for(Object o: cityInput){
            this.city[length] = (City) o;
            length++;
        }
        this.pher = new Pheromone(cityInput.size());
        System.out.print("Please insert total of agents: ");
        String input = br.readLine();
        this.agents = new Ant[Integer.parseInt(input)];
        System.out.print("Please insert the amount of Alpha: ");
        input = br.readLine();
        this.alpha = Integer.parseInt(input);
        System.out.print("Please insert the amount of Beta: ");
        input = br.readLine();
        this.beta = Integer.parseInt(input);
        System.out.print("Please insert the amount of iteration: ");
        int iteration = Integer.parseInt(br.readLine());
        for (int i = 0; i < this.agents.length; i++) {
            this.agents[i] = new Ant(this.city.length);
        }
        BigDecimal result = new BigDecimal(Integer.MAX_VALUE);
        String res = "";
        while(iteration>0){
            System.out.println("Iteration: "+iteration);
            for (int i = 0; i < this.agents.length; i++) {
                //System.out.println("Agent number: "+(i+1));
                this.agents[i].run(this.city,this.pher,this.alpha,this.beta);
                BigDecimal temp = this.agents[i].getResultDistance();
                if(result.compareTo(temp)>0){
                    result = temp;
                    res = this.agents[i].getMove();
                }
            }
            this.pher.updatePher(city, agents);
            for (int i = 0; i < this.agents.length; i++) {
                this.agents[i].reset();
            }
            System.out.println("successfuly reset");
            iteration--;
        }
        System.out.println("Best result: "+res);
        System.out.println("Best distance: "+result);
    }
}
