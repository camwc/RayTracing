package rayTracer;

public abstract class Texture {
    public abstract Vector3 colorValue(double u, double v, Vector3 p);
}
