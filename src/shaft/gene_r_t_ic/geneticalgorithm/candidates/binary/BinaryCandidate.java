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
package shaft.gene_r_t_ic.geneticalgorithm.candidates.binary;

import shaft.gene_r_t_ic.geneticalgorithm.ICandidate;
import shaft.gene_r_t_ic.geneticalgorithm.ICrossoverOp;
import shaft.gene_r_t_ic.geneticalgorithm.IMutationOp;
import shaft.gene_r_t_ic.geneticalgorithm.candidates.*;

/**
 *
 * @author Thomas Schaffer <thomas.schaffer@epitech.eu>
 */
public class BinaryCandidate extends ACandidate {

    private byte[] _genotype;
    private int _length;
    private static CandidateEvaluator<BinaryCandidate> _evaluator;
    private static ICrossoverOp _crossover = new BinaryUniformCrossover(0.8);
    private static IMutationOp _mutation = new BinaryMutation(0.01);

    public BinaryCandidate(int length) {
        _length = length;
        _genotype = new byte[length];
    }
    
    public static void setUniformCrossover(double probability) {
        _crossover = new BinaryUniformCrossover(probability);
    }
    
    public static void setMutation(double probability) {
        _mutation = new BinaryMutation(probability);
    }

    public static void setEvaluator(CandidateEvaluator<BinaryCandidate> evaluator) {
        _evaluator = evaluator;
    }
    
    public byte getValue(int i) {
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

    private static class BinaryUniformCrossover implements ICrossoverOp {
        
        private double _proba;
        
        public BinaryUniformCrossover(double probability) {
            _proba = probability;
        }
        
        @Override
        public ICandidate apply(ICandidate cand1, ICandidate cand2) {
            if (!(cand1 instanceof BinaryCandidate)
                    || !(cand2 instanceof BinaryCandidate)) {
                throw new UnsupportedOperationException("Operator cannot operate on these ICandidates' real types.");
            }
            
            if (Math.random() > _proba) {
                if (Math.random() < 0.5) {
                    return cand1;
                }
                return cand2;
            }

            BinaryCandidate bin1, bin2;
            bin1 = (BinaryCandidate) cand1;
            bin2 = (BinaryCandidate) cand2;

            int length = bin1._length;
            BinaryCandidate child = new BinaryCandidate(length);

            for (int i = 0; i < length; i++) {
                byte val;
                if (Math.random() < 0.5) {
                    val = bin1._genotype[i];
                } else {
                    val = bin2._genotype[i];
                }
                child._genotype[i] = val;
            }
            return child;
        }
    }

    private static class BinaryMutation implements IMutationOp {
        
        private double _proba;
        
        public BinaryMutation(double probability) {
            _proba = probability;
        }
        
        @Override
        public ICandidate apply(ICandidate candidate) {
            if (!(candidate instanceof BinaryCandidate)) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            BinaryCandidate binCand = (BinaryCandidate) candidate;

            for (int i = 0; i < binCand._length; i++) {
                if (Math.random() <= _proba) {
                    binCand._genotype[i] = (binCand._genotype[i] == 0 ? (byte) 1 : (byte) 0);
                }
            }
            return binCand;
        }
    }
}
