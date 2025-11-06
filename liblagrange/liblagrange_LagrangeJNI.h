#include <jni.h>
extern "C" {
/*
 * Class:     liblagrange_LagrangeJNI
 * Method:    interpolar
 * Signature: ([[DD)D
 */
JNIEXPORT jdouble JNICALL Java_liblagrange_LagrangeJNI_interpolar
  (JNIEnv *, jobject, jobjectArray, jdouble);

}
