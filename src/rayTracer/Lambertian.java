package rayTracer;

public class Lambertian extends Material {
    //Vector3 albedo;
    Texture albedo;

    public Lambertian(Vector3 albedo){
        this.albedo = new SolidColor(albedo);
    }

    public Lambertian(Texture albedo){
        this.albedo = albedo;
    }

    @Override
    boolean scatter(Ray rIn, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 scatterDirection = record.normal.add(Vector3.randomUnitVector());

        if(scatterDirection.nearZero()){
            scatterDirection = record.normal;
        }

        scattered.changeValues(record.point, scatterDirection);
        attenuation.changeValues(albedo.colorValue(record.u, record.v, record.point));
        return true;
    }
}
