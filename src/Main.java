
import Algorithm.AntColony;
import Algorithm.Genetic;
import Model.City;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael Walah
 * @author Qolbi Fathurrohim
 * @author William Walah
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Halo! Tolong dipilih antara \"Ant Colony\"(A) atau \"Genetic\"(G) yaa:");
        System.out.print("Input:  ");
        String alg = br.readLine();
        String input;
        ArrayList<Object> cityInput = new ArrayList<>();
        System.out.println("City Input: ");
        while((input = br.readLine())!=null){
            if(input.equals("EOF")){
                System.out.println("Input process completed");
                break;
            }
            String[] buffer = input.split(" ");
            cityInput.add(new City(Integer.parseInt(buffer[1]),Integer.parseInt(buffer[2])));
        }
        switch(alg){
            case "A": //Ant Colony
                AntColony aco = new AntColony();
                aco.run(cityInput);
                break;
            case "G": //Genetic
                Genetic gen = new Genetic();
                gen.run(cityInput);
                break;
            default: 
                break;
        }
    }
}
