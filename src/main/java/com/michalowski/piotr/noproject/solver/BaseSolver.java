package com.michalowski.piotr.noproject.solver;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Getter
abstract class BaseSolver {
    List<Integer> timeValues;
    int size;
    BigDecimal accuracy;
    BigDecimal fiParameter;
    BigDecimal nParameter;
    BigDecimal expectedTime;

    public BaseSolver(List<Integer> timeValues, double accuracy) {
        this.timeValues = timeValues;
        this.size = timeValues.size();
        this.accuracy = BigDecimal.valueOf(accuracy);
        solve();
    }

    protected abstract void solve();

    protected boolean isAccuracySufficient(BigDecimal leftEquationValue, BigDecimal rightEquationValue) {
        BigDecimal relativeError = getRelativeCalculationError(leftEquationValue, rightEquationValue);
        return relativeError.compareTo(accuracy) < 1;
    }

    private BigDecimal getRelativeCalculationError(BigDecimal first, BigDecimal second) {
        BigDecimal difference = first.subtract(second);
        difference = difference.abs();
        return first.compareTo(second) >= 0 ?
                difference.divide(first, MathContext.DECIMAL128) : difference.divide(second, MathContext.DECIMAL128);
    }
}
