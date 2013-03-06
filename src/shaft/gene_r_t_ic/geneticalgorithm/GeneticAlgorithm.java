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
    
    public GeneticAlgorithm(ISelector selector, IPopulationGenerator generator,
            ICandidate firstInstance) {
        _selector = selector;
        _generator = generator;
        _pop = _generator.initialPopulation(firstInstance);
        _generationCounter = 0;
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
    
    public int generation() {
        evaluatePopulation();
        _selector.loadPopulation(_pop);
        _pop = _generator.newPopulation(_selector);
        _selector.unloadPopulation();
        _popEval = false;
        return ++_generationCounter;
    }
    
    public ICandidate getBestCandidate() {
        evaluatePopulation();
        
        ICandidate ret = null;
        for (ICandidate cand : _pop) {
            if (ret == null || cand.getFitness() > ret.getFitness()) {
                ret = cand;
            }
        }
        return ret;
    }
}
