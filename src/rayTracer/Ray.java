package rayTracer;

public class Ray {
    Vector3 origin, direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 at(double t){
        return origin.add(direction.multiply(t));
    }

}
