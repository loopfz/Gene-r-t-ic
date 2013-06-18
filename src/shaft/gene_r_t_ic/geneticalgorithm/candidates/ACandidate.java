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
package shaft.gene_r_t_ic.geneticalgorithm.candidates;

import shaft.gene_r_t_ic.geneticalgorithm.ICandidate;

/**
 *
 * @author Thomas Schaffer <thomas.schaffer@epitech.eu>
 */
public abstract class ACandidate implements ICandidate {
    
    protected double _fitness;
    protected int _length;
    private static int _fitnessSign = 1;
    
    protected ACandidate(int length) {
        _length = length;
    }
    
    public int getLength() {
        return _length;
    }
    
    @Override
    public void setFitness(double fitness) {
        _fitness = fitness;
    }
    
    @Override
    public double getFitness() {
        return _fitness;
    }
    
    public void usePositiveFitness() {
        _fitnessSign = 1;
    }
    
    public void useNegativeFitness() {
        _fitnessSign = -1;
    }

    @Override
    public int compareTo(ICandidate o) {
        if (getFitness() > o.getFitness()) {
            return _fitnessSign;
        } else if (o.getFitness() > getFitness()) {
            return -1 * _fitnessSign;
        }
        return 0;
    }
}
