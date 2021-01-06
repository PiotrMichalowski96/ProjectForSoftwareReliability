package com.michalowski.piotr.noproject.solver;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SchickWolvertonSolver extends BaseSolver {

    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    public SchickWolvertonSolver(List<Integer> timeValues, double accuracy) {
        super(timeValues, accuracy);
    }

    @Override
    protected void solve() {
        BigDecimal tConstant = solveSumOfSquareSeries(timeValues);
        nParameter = solveNParameter();
        fiParameter = solveFiEquation(nParameter, tConstant);
        expectedTime = solveExpectedTime();
    }

    private BigDecimal solveExpectedTime() {
        BigDecimal PI = BigDecimal.valueOf(Math.PI);
        BigDecimal sizeIncreasedByOne = BigDecimal.valueOf(size+1);
        BigDecimal subtract = nParameter.subtract(sizeIncreasedByOne);

        BigDecimal denominator = TWO.multiply(fiParameter).multiply(subtract);
        BigDecimal fractial = PI.divide(denominator, MathContext.DECIMAL128);
        return fractial.sqrt(MathContext.DECIMAL128);
    }

    private BigDecimal solveNParameter() {
        BigDecimal tempNParameter = BigDecimal.valueOf(size);
        BigDecimal tConstant = solveSumOfSquareSeries(timeValues);
        BigDecimal sumSeriesFromNEquation = solveSumSeriesFromNEquation(timeValues);
        BigDecimal leftEquation;
        BigDecimal rightEquation;

        do {
            tempNParameter = tempNParameter.add(BigDecimal.ONE);
            leftEquation  = solveTransformedEquation(tempNParameter, tConstant, sumSeriesFromNEquation);
            rightEquation = solveFiEquation(tempNParameter, tConstant);
        } while (!isAccuracySufficient(leftEquation, rightEquation));
        return tempNParameter;
    }

    private BigDecimal solveFiEquation(BigDecimal tempNParameter, BigDecimal tConstant) {
        BigDecimal sumSeries = BigDecimal.ZERO;
        BigDecimal fractial;
        for(int i = 0; i < size; i++) {
            BigDecimal subtrahend = BigDecimal.valueOf(i);
            BigDecimal difference = tempNParameter.subtract(subtrahend);
            BigDecimal denominator = difference.multiply(tConstant);
            fractial = BigDecimal.ONE.divide(denominator, MathContext.DECIMAL128);
            sumSeries = sumSeries.add(fractial);
        }
        return TWO.multiply(sumSeries);
    }

    private BigDecimal solveTransformedEquation(BigDecimal tempNParameter, BigDecimal tConstant, BigDecimal sumSeriesFromNEquation) {
        BigDecimal numerator = BigDecimal.valueOf(2*size);
        BigDecimal NT = tempNParameter.multiply(tConstant);
        BigDecimal denominator = NT.subtract(sumSeriesFromNEquation);
        return numerator.divide(denominator, MathContext.DECIMAL128);
    }

    private BigDecimal solveSumSeriesFromNEquation(List<Integer> numbers) {
        BigDecimal seriesSum = BigDecimal.ZERO;
        for(int i = 0; i < size; i++) {
            BigDecimal iValue = BigDecimal.valueOf(i);
            BigDecimal timeValue = BigDecimal.valueOf(numbers.get(i));
            BigDecimal timeValueSquare = timeValue.pow(2);
            BigDecimal addend = iValue.multiply(timeValueSquare);
            seriesSum = seriesSum.add(addend);
        }
        return seriesSum;
    }

    private BigDecimal solveSumOfSquareSeries(List<Integer> numbers) {
        return numbers.stream()
                .map(BigDecimal::valueOf)
                .map(value -> value.pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
