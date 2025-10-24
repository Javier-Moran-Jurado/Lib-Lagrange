#include <stdio.h>
#include <stdlib.h>
#include "lagrange.h"

int main(int argc, char** argv)
{
    // Puntos conocidos (x, y) - NUEVOS VALORES
    double x[] = {2, 4, 6, 8};
    double y[] = {5, 9, 7, 12};
    int n = 4;

    // Valor que queremos interpolar
    double x_eval = 5.0;
    double resultado;

    resultado = lagrangeInterpolation(x, y, n, x_eval);

    printf("El valor interpolado en x = %.2f es %.4f\n", x_eval, resultado);

    exit(EXIT_SUCCESS);
}

