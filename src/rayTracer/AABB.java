package rayTracer;

public class AABB {
    Vector3 minimum;
    Vector3 maximum;

    public AABB(){}

    public AABB(Vector3 minimum, Vector3 maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    boolean hit(Ray r, double tMin, double tMax){
        for(int a = 0; a < 3; a++){
            double invD = 1.0f / r.direction.e[a];
            double t0 = (minimum.e[a] - r.origin.e[a]) * invD;
            double t1 = (maximum.e[a] - r.origin.e[a]) * invD;
            if(invD < 0.0f){
                double s = t0;
                t0 = t1;
                t1 = s;
            }
            tMin = Math.max(t0, tMin);
            tMax = Math.min(t1, tMax);
            if(tMax <= tMin){
                return false;
            }
        }
        return true;
    }

    public static AABB surroundingBox(AABB box0, AABB box1){
        Vector3 small = new Vector3(Math.min(box0.minimum.x, box1.minimum.x),
                Math.min(box0.minimum.y, box1.minimum.y),
                Math.min(box0.minimum.z, box1.minimum.z));
        Vector3 big = new Vector3(Math.max(box0.maximum.x, box1.maximum.x),
                Math.max(box0.maximum.y, box1.maximum.y),
                Math.max(box0.maximum.z, box1.maximum.z));

        return new AABB(small, big);
    }

    public void changeValues(AABB box){
        this.maximum = box.maximum;
        this.minimum = box.minimum;
    }

}
