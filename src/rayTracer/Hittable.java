package rayTracer;

//Abstract class for handling rendered objects
public abstract class Hittable {

    abstract boolean hit(Ray r, double tMin, double tMax, HitRecord record);

}