#include <jni.h>
#include "liblagrange_LagrangeJNI.h"
#include <stdio.h>

// Función para calcular el término base de Lagrange
double termino_base(double x, double x_puntos[], int i, int n) {
    double resultado = 1.0;
    for (int j = 0; j < n; j++) {
        if (j != i) {
            resultado *= (x - x_puntos[j]) / (x_puntos[i] - x_puntos[j]);
        }
    }
    return resultado;
}

// Implementación de la función nativa
JNIEXPORT jdouble JNICALL Java_liblagrange_LagrangeJNI_interpolar
  (JNIEnv *env, jobject obj, jobjectArray puntos, jdouble x) {

    // Obtener el array de X (primera fila)
    jdoubleArray x_array = (*env)->GetObjectArrayElement(env, puntos, 0);
    jdouble *x_puntos = (*env)->GetDoubleArrayElements(env, x_array, NULL);
    jsize n_x = (*env)->GetArrayLength(env, x_array);

    // Obtener el array de Y (segunda fila)
    jdoubleArray y_array = (*env)->GetObjectArrayElement(env, puntos, 1);
    jdouble *y_puntos = (*env)->GetDoubleArrayElements(env, y_array, NULL);
    jsize n_y = (*env)->GetArrayLength(env, y_array);

    // Verificar que tienen la misma longitud
    if (n_x != n_y) {
        printf("Error: Los arrays X e Y tienen diferentes longitudes\n");
        return 0.0;
    }

    // Calcular interpolación de Lagrange
    double resultado = 0.0;
    for (int i = 0; i < n_x; i++) {
        resultado += y_puntos[i] * termino_base(x, x_puntos, i, n_x);
    }

    // Liberar memoria
    (*env)->ReleaseDoubleArrayElements(env, x_array, x_puntos, 0);
    (*env)->ReleaseDoubleArrayElements(env, y_array, y_puntos, 0);

    return resultado;
}
