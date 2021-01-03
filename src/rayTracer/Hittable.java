package rayTracer;

//Abstract class for handling rendered objects
public abstract class Hittable {
    Material material;

    abstract boolean hit(Ray r, double tMin, double tMax, HitRecord record);
    abstract boolean boundingBox(AABB outputBox);

}
