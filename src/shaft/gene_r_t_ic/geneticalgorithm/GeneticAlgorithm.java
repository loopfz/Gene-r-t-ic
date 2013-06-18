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
package shaft.gene_r_t_ic.geneticalgorithm;

import java.util.List;

/**
 *
 * @author Thomas Schaffer <thomas.schaffer@epitech.eu>
 */
public class GeneticAlgorithm {
    
    private List<ICandidate> _pop;
    private IPopulationGenerator _generator;
    private ISelector _selector;
    private int _generationCounter;
    private boolean _popEval;
    private IGenerationData _data;
    private double _meanFitness;
    private ICandidate _best;
    private ICandidate _worst;
    
    public GeneticAlgorithm(ISelector selector, IPopulationGenerator generator,
            ICandidate firstInstance) {
        _selector = selector;
        _generator = generator;
        _pop = _generator.initialPopulation(firstInstance);
        _generationCounter = 0;
        _data = new GAGenerationData();
    }
    
    public void evaluatePopulation() {
        if (_popEval) {
            return;
        }
        for (ICandidate cand : _pop) {
            cand.evaluate();
        }
        _popEval = true;
    }
    
    public void generation() {
        evaluatePopulation();
        _selector.loadPopulation(_pop);
        _pop = _generator.newPopulation(_selector);
        _selector.unloadPopulation();
        _popEval = false;
        _generationCounter++;
    }
    
    public IGenerationData generationData() {
        double totalFitness = 0;
        _best = null;
        _worst = null;
        
        evaluatePopulation();
        
        for (ICandidate cand : _pop) {
            if (_best == null ||  cand.compareTo(_best) > 0) {
                _best = cand;
            }
            if (_worst == null || cand.compareTo(_worst) < 0) {
                _worst = cand;
            }
            totalFitness += cand.getFitness();
        }
        
        _meanFitness = totalFitness / _pop.size();
        
        return _data;
    }
    
    private class GAGenerationData implements IGenerationData {

        @Override
        public int generation() {
            return _generationCounter;
        }

        @Override
        public ICandidate worstCandidate() {
            return _worst;
        }

        @Override
        public double meanFitness() {
            return _meanFitness;
        }

        @Override
        public ICandidate bestCandidate() {
            return _best;
        }
        
    }
}
