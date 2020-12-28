package rayTracer;

public class Dielectric extends Material {
    double indexOfRefraction;
    Vector3 albedo;

    public Dielectric(double indexOfRefraction){
        this.indexOfRefraction = indexOfRefraction;
        this.albedo = new Vector3(1,1,1);
    }

    public Dielectric(Vector3 albedo, double indexOfRefraction){
        this.indexOfRefraction = indexOfRefraction;
        this.albedo = albedo;
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        attenuation.changeValues(albedo);
        double refractionRatio = record.frontFace ? (1/indexOfRefraction) : indexOfRefraction;

        Vector3 unitDirection = rIn.direction.unitVector();
        double cosTheta = Math.min(unitDirection.multiply(-1).dot(record.normal), 1);
        double sinTheta = Math.sqrt(1 - cosTheta*cosTheta);

        boolean cannotRefract = refractionRatio*sinTheta > 1;
        Vector3 direction;
        if(cannotRefract || reflectance(cosTheta, refractionRatio) > Math.random()){
            direction = unitDirection.reflect(record.normal);
        }
        else{
            direction = unitDirection.refract(record.normal, refractionRatio);
        }

        scattered.changeValues(record.point, direction);
        return true;
    }

    private double reflectance(double cosine, double refractionRatio){
        double r0 = (1-refractionRatio) / (1+refractionRatio);
        r0 = r0*r0;
        return r0 + (1-r0)*Math.pow((1-cosine), 5);
    }
}
