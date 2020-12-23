package rayTracer;

public class Lambertian extends Material {
    Vector3 albedo;

    public Lambertian(Vector3 albedo){
        this.albedo = albedo;
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 scatterDirection = record.normal.add(Vector3.randomUnitVector());

        if(scatterDirection.nearZero()){
            scatterDirection = record.normal;
        }

        scattered.changeValues(record.point, scatterDirection);
        attenuation.changeValues(albedo);
        return true;
    }
}
