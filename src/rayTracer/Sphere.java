package rayTracer;

public class Sphere extends Hittable {

    Vector3 center;
    double radius;

    public Sphere(Vector3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    boolean hit(Ray r, double tMin, double tMax, HitRecord record) {
        Vector3 oc = r.origin.subtract(center);
        double a = r.direction.lengthSquared();
        double halfB = oc.dot(r.direction);
        double c = oc.lengthSquared() - radius*radius;
        double discriminant = halfB*halfB - a*c;

        //checks if rey hits
        if(discriminant < 0){
            return false;
        }

        double sqrtD = Math.sqrt(discriminant);
        double root = (-halfB - sqrtD) / a;

        //Check if hit point is within acceptable range
        if(root < tMin || root > tMax){
            root = (-halfB + sqrtD) / a;
            if(root < tMin || root > tMax){
                return false;
            }
        }

        record.t = root;
        record.point = r.at(record.t);
        Vector3 outwardNormal = record.point.subtract(center).divide(radius);
        record.setFaceNormal(r, outwardNormal);

        return true;
    }
}