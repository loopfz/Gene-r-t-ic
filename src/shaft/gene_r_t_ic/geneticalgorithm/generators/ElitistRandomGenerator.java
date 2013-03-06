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
package shaft.gene_r_t_ic.geneticalgorithm.generators;

import java.util.LinkedList;
import java.util.List;
import shaft.gene_r_t_ic.geneticalgorithm.ICandidate;
import shaft.gene_r_t_ic.geneticalgorithm.IPopulationGenerator;
import shaft.gene_r_t_ic.geneticalgorithm.ISelector;

/**
 *
 * @author Thomas Schaffer <thomas.schaffer@epitech.eu>
 */
public class ElitistRandomGenerator implements IPopulationGenerator {
    
    private int _numElitism;
    private int _numRandom;
    private int _popSize;
    
    public ElitistRandomGenerator(int percentElitism, int percentRandom, int popSize) {
        _popSize = popSize;
        _numElitism = (_popSize * percentElitism) / 100;
        _numRandom = (_popSize * percentRandom) / 100;
    }
    
    @Override
    public List<ICandidate> initialPopulation(ICandidate firstInstance) {
        List<ICandidate> newPop;
        newPop = new LinkedList<>();
        
        for (int i = 0; i < _popSize; i++) {
            newPop.add(firstInstance.newRandomCandidate());
        }
        return newPop;
    }
    
    @Override public List<ICandidate> newPopulation(ISelector selector) {
        List<ICandidate> newPop;
        newPop = new LinkedList<>();
        
        for (int i = 0; i < _popSize - _numRandom - _numElitism; i++) {
            ICandidate cand1 = selector.select();
            ICandidate cand2 = selector.select();
            ICandidate offspring = cand1.mutation().apply(cand1.crossover().apply(cand1, cand2));
            newPop.add(offspring);
        }
        for (int i = 0; i < _numRandom; i++) {
            newPop.add(newPop.get(0).newRandomCandidate());
        }
        for (int i = 0; i < _numElitism; i++) {
            newPop.add(selector.popBestCandidate());
        }
        return newPop;
    }
}
