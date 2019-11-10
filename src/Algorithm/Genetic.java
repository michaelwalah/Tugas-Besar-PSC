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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.Entry.comparingByValue;
import java.util.Random;
import java.util.Set;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Michael Walah
 * @author Qolbi Fathurrohim
 * @author William Walah
 */
public class Genetic {

    private Map<Integer, Double> fitness;
    private City[] cityList;
    private Individu elitsm;
    private Individu[] population, parent;
    private Random rd;
    protected static final double CHANCE_MUTATE = 0.3;

    public Genetic() {
        rd = new Random();
    }

    public void run(ArrayList<Object> cityInput) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.cityList = new City[cityInput.size()];
        int length = 0;
        for (Object o : cityInput) {
            this.cityList[length] = (City) o;
            length++;
        }
        System.out.print("Insert total population: ");
        int population = Integer.parseInt(br.readLine());
        System.out.print("Insert desired kids per couple: ");
        int totalParent = (int) Math.ceil((population * 1.0) / (Integer.parseInt(br.readLine()) * 1.0)) + 1;
        this.parent = new Individu[totalParent];
        this.population = new Individu[population];
        int a = 10000;
        //1. generate first generation
        this.generatePopulation();
        for (int i = 0; i < this.population.length; i++) {
            this.population[i].countTotal(cityList);
        }
        double max = Double.MAX_VALUE;
        int[] path = null;
        while (a > 0) {
            System.out.print(a+": ");
            int idx = this.getMax();
            double temp_res = this.population[idx].getTotalDistance();
            if(max>temp_res){
                max = temp_res;
                path = this.population[idx].getSolution().clone();
            }
            System.out.println(max);
            //2. calculate Fitness Function
            this.fitness = this.getFitness();
            //3. selection
            this.selection();
            //4. crossover
            Individu[] child = this.getChild();
            //5. error correction
            this.correctError(child);
            //6. mutation
            this.mutation(child);
            child[rd.nextInt(child.length)] = this.elitsm;
            //System.out.println(this.elitsm.getTotalDistance());
            this.population = child;
            a--;
        }
        System.out.println(max);
        for (int i = 0; i < path.length-1; i++) {
            System.out.print(path[i]+", ");
        }
        System.out.println(path[path.length-1]);
    }
    
    public int getMax(){
        int index = -1;
        double max = Double.MAX_VALUE;
        for (int i = 0; i < this.population.length; i++) {
            if(this.population[i].getTotalDistance()<max){
                max = this.population[i].getTotalDistance();
                index = i;
            }
        }
        return index;
    }

    public void generatePopulation() {
        for (int i = 0; i < population.length; i++) {
            this.population[i] = new Individu(cityList.length);
            this.population[i].createSolution();
        }
    }

    public void printPopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i].print();
        }
    }

    public Map<Integer, Double> getFitness() {
        HashMap<Integer, Double> fitness = new HashMap<>();
        for (int i = 0; i < population.length; i++) {
            this.population[i].countTotal(cityList);
            fitness.put(i, (1.0 / this.population[i].getTotalDistance()));
        }
        //dibawah berikut ini merupakan kode salinan dari:
        //https://www.javacodegeeks.com/2017/09/java-8-sorting-hashmap-values-ascending-descending-order.html
        Map<Integer, Double> res = fitness.entrySet().
                stream().
                sorted(comparingByValue()).
                collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));
        return res;
    }

    public void selection() {
        Double max = 0.0;
        int j = 0;
        for (Entry<Integer, Double> entry : this.fitness.entrySet()) {
            if (j == this.fitness.size() - 1) {
                this.elitsm = this.population[entry.getKey()];
            }
            max += entry.getValue();
            j++;
        }
        Double[] prob = new Double[this.population.length];
        int index = 0;
        for (Entry<Integer, Double> entry : this.fitness.entrySet()) {
            if (index == 0) {
                prob[index] = entry.getValue() / max;
            } else {
                prob[index] = entry.getValue() / max;
                prob[index] += prob[index - 1];
            }
            index++;
        }
        for (int i = 0; i < parent.length; i++) {
            double rnd = rd.nextDouble();
            index = 0;
            for (Entry<Integer, Double> entry : this.fitness.entrySet()) {
                if (prob[index] > rnd) {
                    index = entry.getKey();
                    break;
                }
                index++;
            }
            //System.out.println(index);
            parent[i] = population[index];
        }
    }

    public Individu[] getChild() {
        Individu[] child = new Individu[this.population.length];
        int cutPoint = rd.nextInt(this.cityList.length - 2) + 1;
        int index = 0;
        for (int i = 0; i < parent.length - 1; i++) {
            int[] solution1 = new int[this.cityList.length];
            int[] solution2 = new int[solution1.length];
            int[] p1 = this.parent[i].getSolution();
            int[] p2 = this.parent[i + 1].getSolution();
            for (int j = 0; j < solution1.length; j++) {
                if (j != cutPoint) {
                    solution1[j] = p1[j];
                    solution2[solution2.length - 1 - j] = p1[j];
                } else {
                    solution1[j] = p2[j];
                    solution2[solution2.length - 1 - j] = p2[j];
                }
            }
            if (index < child.length) {
                child[index] = new Individu(this.cityList.length);
                child[index].set(solution1);
            }
            if (index + 1 < child.length) {
                child[index + 1] = new Individu(this.cityList.length);
                child[index + 1].set(solution2);
            }
            index = index + 2;
        }
        return child;
    }

    public void correctError(Individu[] child) {
        ArrayList<Integer> superb = new ArrayList<>();
        for (int j = 0; j < this.cityList.length; j++) {
            superb.add(j);
        }
        for (int i = 0; i < child.length; i++) {
            ArrayList<Integer> temp = new ArrayList<>(superb);
            int[] solution = child[i].getSolution();
            Set<Integer> checkList = new HashSet<>();
            for (int j = 0; j < solution.length; j++) {
                if (!checkList.contains(solution[j])) {
                    temp.remove(new Integer(solution[j]));
                } else {
                    int index = rd.nextInt(temp.size());
                    solution[j] = temp.get(index);
                    temp.remove(index);
                }
                checkList.add(solution[j]);
            }
            child[i].countTotal(cityList);
        }
    }

    public void mutation(Individu[] child) {
        for (int i = 0; i < child.length; i++) {
            double prob = rd.nextDouble();
            if (prob >= CHANCE_MUTATE) {
                int a = 1000;
                double max = child[i].getTotalDistance();
                int[] bestSolution = null;
                while (a > 0) {
                    int[] solution = child[i].getSolution().clone();
                    int index1 = rd.nextInt(solution.length);
                    int index2 = rd.nextInt(solution.length);
                    while (index2 == index1) {
                        index2 = rd.nextInt(solution.length);
                    }
                    int temp = solution[index1];
                    solution[index1] = solution[index2];
                    solution[index2] = temp;
                    double res = 0.0;
                    int k = 0;
                    for (k = 0; k < solution.length - 1; k++) {
                        res += cityList[solution[k]].calculateDistance(cityList[solution[k + 1]]);
                    }
                    res += cityList[solution[k]].calculateDistance(cityList[solution[0]]);

                    if (max < res) {
                        max = res;
                        bestSolution = solution.clone();
                    }
                    a--;
                }
                if (bestSolution != null) {
                    child[i].set(bestSolution);
                    child[i].countTotal(cityList);
                }
            }
        }
    }
}
