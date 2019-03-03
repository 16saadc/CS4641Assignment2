package opt.test;

import java.util.Arrays;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;

import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.SingleCrossOver;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

/**
 * A test using the flip flop evaluation function
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class FlipFlopTest {
    /** The n value */
    private static int N = 80;
    
    public static void main(String[] args) {
        for (int n = 40; n <= 200; n += 20) {
            N = n;
            double[] trials = new double[5];
            int index = 0;
            for (int trial = 0; trial < 5; trial++) {


                int[] ranges = new int[N];
                Arrays.fill(ranges, 2);
                EvaluationFunction ef = new FlipFlopEvaluationFunction();
                Distribution odd = new DiscreteUniformDistribution(ranges);
                NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
                MutationFunction mf = new DiscreteChangeOneMutation(ranges);
                CrossoverFunction cf = new SingleCrossOver();
                Distribution df = new DiscreteDependencyTree(.1, ranges);
                HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
                GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

                RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);
                FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 200000);
                fit.train();
                trials[index++] = ef.value(rhc.getOptimal());
//                System.out.println(ef.value(rhc.getOptimal()));
//
//                SimulatedAnnealing sa = new SimulatedAnnealing(100, .95, hcp);
//                fit = new FixedIterationTrainer(sa, 200000);
//                double start = System.nanoTime(), end, trainingTime;
//                fit.train();
//                end = System.nanoTime();
//                trainingTime = end - start;
//                trainingTime /= Math.pow(10, 9);
//                System.out.println("Training time: " + trainingTime);
//                trials[index++] = ef.value(sa.getOptimal());
//                System.out.println(ef.value(sa.getOptimal()));
//
//                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 20, gap);
//                fit = new FixedIterationTrainer(ga, 1000);
//                double start = System.nanoTime(), end, trainingTime;
//                fit.train();
//                end = System.nanoTime();
//                trainingTime = end - start;
//                trainingTime /= Math.pow(10, 9);
//                System.out.println("Training time: " + trainingTime);
//                trials[index++] = ef.value(ga.getOptimal());
//                System.out.println(ef.value(ga.getOptimal()));

//                MIMIC mimic = new MIMIC(200, 5, pop);
//                fit = new FixedIterationTrainer(mimic, 1000);
//                double start = System.nanoTime(), end, trainingTime;
//                fit.train();
//                end = System.nanoTime();
//                trainingTime = end - start;
//                trainingTime /= Math.pow(10, 9);
//                System.out.println("Training time: " + trainingTime);
//                trials[index++] = ef.value(mimic.getOptimal());
                //System.out.println(ef.value(mimic.getOptimal()));
            }
            double average = 0;
            for (double x : trials) {
                average += x;
            }
            average /= trials.length;

            System.out.println(average);
        }
    }
}
