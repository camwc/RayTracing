package rayTracer;

public abstract class Material {
    abstract boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered);
}
