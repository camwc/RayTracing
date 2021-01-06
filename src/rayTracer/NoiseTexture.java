package rayTracer;

public class NoiseTexture extends Texture{
    public Perlin noise;

    public NoiseTexture(){
        this.noise = new Perlin();
    }

    @Override
    public Vector3 colorValue(double u, double v, Vector3 p) {
        return new Vector3(1,1,1).multiply(noise.noise(p));
    }
}
