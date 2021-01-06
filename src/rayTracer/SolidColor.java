package rayTracer;

public class SolidColor extends Texture {

    private Vector3 colorValue;

    public SolidColor(Vector3 colorValue) {
        this.colorValue = colorValue;
    }

    public SolidColor(double r, double g, double b){
        this.colorValue = new Vector3(r,g,b);
    }

    @Override
    public Vector3 colorValue(double u, double v, Vector3 p) {
        return colorValue;
    }
}
