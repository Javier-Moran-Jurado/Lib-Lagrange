#include <stdio.h>
#include <stdlib.h>

double lagrangeInterpolation(double x[], double y[], int n, double x_eval) {
    double result = 0.0;

    for (int i = 0; i < n; i++) {
        double term = y[i];
        for (int j = 0; j < n; j++) {
            if (i != j) {
                term *= (x_eval - x[j]) / (x[i] - x[j]);
            }
        }
        result += term;
    }

    return result;
}
