/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Genetic;

/**
 *
 * @author William Walah
 */
public class Individu {
    private int[] solution;
    
    public Individu(){
        
    }
    
    public void set(int[] solution){this.solution=solution;}
    
    public int[] getSolution(){return this.solution;}
}
