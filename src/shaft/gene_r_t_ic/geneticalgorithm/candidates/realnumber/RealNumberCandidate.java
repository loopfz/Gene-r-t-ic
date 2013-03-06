/*
 * The MIT License
 *
 * Copyright 2013 Thomas Schaffer <thomas.schaffer@epitech.eu>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package shaft.gene_r_t_ic.geneticalgorithm.candidates.realnumber;

import shaft.gene_r_t_ic.geneticalgorithm.ICandidate;
import shaft.gene_r_t_ic.geneticalgorithm.ICrossoverOp;
import shaft.gene_r_t_ic.geneticalgorithm.IMutationOp;
import shaft.gene_r_t_ic.geneticalgorithm.candidates.*;

/**
 *
 * @author Thomas Schaffer <thomas.schaffer@epitech.eu>
 */
public class RealNumberCandidate extends ACandidate {

    private double[] _genotype;
    private int _numVal;
    private static CandidateEvaluator<RealNumberCandidate> _evaluator;
    private static ICrossoverOp _crossover = new RealNumberUniformCrossover(0.8);
    private static IMutationOp _mutation = new RealNumberMutation(0.01, 10);

    public RealNumberCandidate(int numVal) {
        _numVal = numVal;
        _genotype = new double[numVal];
    }
    
    public static void setUniformCrossover(double probability) {
        _crossover = new RealNumberUniformCrossover(probability);
    }
    
    public static void setMutation(double probability, int variancePercent) {
        _mutation = new RealNumberMutation(probability, variancePercent);
    }

    public static void setEvaluator(CandidateEvaluator<RealNumberCandidate> evaluator) {
        _evaluator = evaluator;
    }
    
    public double getValue(int i) {
        return _genotype[i];
    }

    @Override
    public void evaluate() {
        if (_evaluator == null) {
            throw new UnsupportedOperationException("No evaluator set.");
        }

        _fitness = _evaluator.evaluate(this);
    }

    @Override
    public ICandidate newRandomCandidate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ICrossoverOp crossover() {
        return _crossover;
    }

    @Override
    public IMutationOp mutation() {
        return _mutation;
    }

    private static class RealNumberUniformCrossover implements ICrossoverOp {
        
        private double _proba;
        
        public RealNumberUniformCrossover(double probability) {
            _proba = probability;
        }

        @Override
        public ICandidate apply(ICandidate cand1, ICandidate cand2) {
            if (!(cand1 instanceof RealNumberCandidate)
                    || !(cand2 instanceof RealNumberCandidate)) {
                throw new UnsupportedOperationException("Operator cannot operate on these ICandidates' real types.");
            }
            
            if (Math.random() > _proba) {
                if (Math.random() < 0.5) {
                    return cand1;
                }
                return cand2;
            }

            RealNumberCandidate real1, real2;
            real1 = (RealNumberCandidate) cand1;
            real2 = (RealNumberCandidate) cand2;

            int numVals = real1._numVal;
            RealNumberCandidate child = new RealNumberCandidate(numVals);

            for (int i = 0; i < numVals; i++) {
                double val;
                if (Math.random() < 0.5) {
                    val = real1._genotype[i];
                } else {
                    val = real2._genotype[i];
                }
                child._genotype[i] = val;
            }
            return child;
        }
    }

    private static class RealNumberMutation implements IMutationOp {
        
        private double _proba;
        private int _variance;
        
        public RealNumberMutation(double probability, int variancePercent) {
            _proba = probability;
            _variance = variancePercent;
        }
        
        @Override
        public ICandidate apply(ICandidate candidate) {
            if (!(candidate instanceof RealNumberCandidate)) {
                throw new UnsupportedOperationException("Operator cannot operate on this ICandidate's real type.");
            }

            RealNumberCandidate realCand = (RealNumberCandidate) candidate;

            for (int i = 0; i < realCand._numVal; i++) {
                if (Math.random() <= _proba) {

                    double coef = Math.random() - Math.random();

                    realCand._genotype[i] += (realCand._genotype[i] / _variance) * coef;
                }
            }
            return realCand;
        }
    }
}