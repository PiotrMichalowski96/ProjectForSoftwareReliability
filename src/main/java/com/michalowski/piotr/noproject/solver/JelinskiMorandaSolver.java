package com.michalowski.piotr.noproject.solver;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class JelinskiMorandaSolver extends BaseSolver {

    public JelinskiMorandaSolver(List<Integer> timeValues, double accuracy) {
        super(timeValues, accuracy);
    }

    @Override
    protected void solve() {
        nParameter = solveNParameter();
        fiParameter = solveFiEquation();
        expectedTime = solveExpectedTime();
    }

    private BigDecimal solveNParameter() {
        BigDecimal tempNParameter = BigDecimal.valueOf(size);
        BigDecimal leftEquation;
        BigDecimal rightEquation;

        do {
            tempNParameter = tempNParameter.add(BigDecimal.ONE);
            leftEquation = solveLeftSideEquation(tempNParameter);
            rightEquation = solveRightSideEquation(tempNParameter);
        } while (!isAccuracySufficient(leftEquation, rightEquation));
        return tempNParameter;
    }

    private BigDecimal solveExpectedTime() {
        BigDecimal minuend = nParameter;
        BigDecimal subtrahend = new BigDecimal(size + 1);

        BigDecimal difference = minuend.subtract(subtrahend);
        BigDecimal denominator = fiParameter.multiply(difference);

        return BigDecimal.ONE.divide(denominator, MathContext.DECIMAL128);
    }

    private BigDecimal solveFiEquation() {
        BigDecimal numerator = BigDecimal.valueOf(size);
        BigDecimal nSumOfT = nParameter.multiply(sumOfTimeValues());
        BigDecimal denominator = nSumOfT.subtract(sumOfMultipliedTimeValues());

        return numerator.divide(denominator, MathContext.DECIMAL128);
    }

    private BigDecimal solveLeftSideEquation(BigDecimal tempNParameter) {
        BigDecimal result = BigDecimal.ZERO;

        for (int i = 0; i < size; i++) {
            BigDecimal denominator = tempNParameter.subtract(BigDecimal.valueOf(i));
            BigDecimal fraction = BigDecimal.ONE.divide(denominator, MathContext.DECIMAL128);
            result = result.add(fraction);
        }
        return result;
    }

    private BigDecimal solveRightSideEquation(BigDecimal tempNParameter) {
        BigDecimal sumOfTimeValues = sumOfTimeValues();
        BigDecimal aSize = BigDecimal.valueOf(size);

        BigDecimal numerator = aSize.multiply(sumOfTimeValues);
        BigDecimal denominator = tempNParameter.multiply(sumOfTimeValues);
        BigDecimal subtrahend = sumOfMultipliedTimeValues();

        denominator = denominator.subtract(subtrahend);
        return numerator.divide(denominator, MathContext.DECIMAL128);
    }

    private BigDecimal sumOfMultipliedTimeValues() {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < size; i++) {
            BigDecimal componentOfSeries = BigDecimal.valueOf(i * timeValues.get(i));
            result = result.add(componentOfSeries);
        }
        return result;
    }

    private BigDecimal sumOfTimeValues() {
        int sum = timeValues.stream().mapToInt(Integer::intValue).sum();
        return BigDecimal.valueOf(sum);
    }
}
