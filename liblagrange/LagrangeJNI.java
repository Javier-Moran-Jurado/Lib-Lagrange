package liblagrange;

public class LagrangeJNI {
    public native double interpolar(double[][] puntos, double x);

    static {
        System.loadLibrary("lagrange");
    }
}
