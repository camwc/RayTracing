package rayTracer;

public class Vector3 {
    double x,y,z;
    double[] e;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        e = new double[3];
        e[0] = x;
        e[1] = y;
        e[2] = z;
    }

    public Vector3 add(Vector3 v){
        return new Vector3(this.x+v.x,this.y+v.y,this.z +v.z);
    }

    public Vector3 subtract(Vector3 v){
        return new Vector3(this.x-v.x,this.y-v.y,this.z -v.z);
    }

    public Vector3 multiply(double t){
        return new Vector3(this.x*t,this.y*t,this.z*t);
    }

    public Vector3 multiply(Vector3 v){
        return new Vector3(this.x*v.x, this.y*v.y, this.z*v.z);
    }

    public Vector3 divide(double t){
        return this.multiply(1/t);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared(){
        return x*x + y*y + z*z;
    }

    public Vector3 unitVector(){
        return this.divide(this.length());
    }

    public double dot(Vector3 v){
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector3 cross(Vector3 v){
        return new Vector3(this.y*v.z - this.z*v.y, this.z*v.x - this.x*v.z, this.x*v.y - this.y*v.x);
    }

    public static Vector3 random(double min, double max){
        return new Vector3(min + (max-min) * Math.random(), min + (max-min) * Math.random(), min + (max-min) * Math.random());
    }

    public static Vector3 randomInUnitSphere(){
        while(true){
            Vector3 v = random(-1, 1);
            if(v.lengthSquared() >= 1){
                    continue;
            }
            return v;
        }
    }

    public static Vector3 randomUnitVector(){
        return randomInUnitSphere().unitVector();
    }

    public static Vector3 randomInUnitDisc(){
        while(true){
            Vector3 p = random(-1, 1);
            p.z = 0;
            if(p.lengthSquared() >= 1){
                continue;
            }
            return p;
        }
    }

    public Vector3 reflect(Vector3 normal){
        return this.subtract(normal.multiply(this.dot(normal)*2));
    }

    public Vector3 refract(Vector3 normal, double etaiOverEtat){
        double cosTheta = Math.min(this.multiply(-1).dot(normal), 1);
        double sinTheta;

        Vector3 rOutPerpendicular = normal.multiply(cosTheta).add(this).multiply(etaiOverEtat);
        Vector3 rOutParallel = normal.multiply(-Math.sqrt(Math.abs(1 - rOutPerpendicular.lengthSquared())));
        return rOutPerpendicular.add(rOutParallel);
    }

    public boolean nearZero(){
        double s = 1e-8;
        return (Math.abs(x) < s) && (Math.abs(y) < s) && (Math.abs(z) < s);
    }

    public void changeValues(Vector3 v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public String toString(){
        return x + ", " + y + ", " + z;
    }

    public String color(){
        return ((int)(x*255.999)) + " " + ((int)(y*255.999)) + " " + ((int)(z*255.999));
    }

}