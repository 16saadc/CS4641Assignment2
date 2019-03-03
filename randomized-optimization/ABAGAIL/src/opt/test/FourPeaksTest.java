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
 * Copied from ContinuousPeaksTest
 * @version 1.0
 */
public class FourPeaksTest {
    /** The n value */
    private static int N = 200;
    /** The t value */
    private static final int T = N / 5;
    
    public static void main(String[] args) {

        for (int n = 50; n <= 400; n += 50) {
            N = n;
            double[] values = new double[3];
            int index = 0;
            for (int trial = 0; trial < 3; trial++) {


                int[] ranges = new int[N];
                Arrays.fill(ranges, 2);
                EvaluationFunction ef = new FourPeaksEvaluationFunction(T);
                Distribution odd = new DiscreteUniformDistribution(ranges);
                NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
                MutationFunction mf = new DiscreteChangeOneMutation(ranges);
                CrossoverFunction cf = new SingleCrossOver();
                Distribution df = new DiscreteDependencyTree(.1, ranges);
                HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
                GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
                ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
                FixedIterationTrainer fit;



//            RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);
//            fit = new FixedIterationTrainer(rhc, i);
//            fit.train();
//            System.out.println(ef.value(rhc.getOptimal()));



//
//            SimulatedAnnealing sa = new SimulatedAnnealing(1E11, .95, hcp);
//            fit = new FixedIterationTrainer(sa, 45000);
//            fit.train();
//            values[index++] = ef.value(sa.getOptimal());
//


//                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 10, gap);
//                fit = new FixedIterationTrainer(ga, i);
//                fit.train();
//                //System.out.println(ef.value(ga.getOptimal()));
//                values[index++] = ef.value(ga.getOptimal());
//
                MIMIC mimic = new MIMIC(200, 20, pop);
                fit = new FixedIterationTrainer(mimic, 6000);

                fit.train();

                //System.out.println("MIMIC: " + ef.value(mimic.getOptimal()));
                values[index++] = ef.value(mimic.getOptimal());
            }
            double avg = 0;
            for (double x : values) {
                avg += x;
            }

            avg /= values.length;

            System.out.println(avg);
        }

    }
}
