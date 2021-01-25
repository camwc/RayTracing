package rayTracer;

//I do not understand any of this

public class Perlin {
    private static int pointCount = 256;
    private Vector3 ranVector[];
    private int[] permX, permY, permZ;

    public Perlin(){
        ranVector = new Vector3[pointCount];
        for(int i = 0; i < pointCount; i++){
            ranVector[i] = Vector3.random(-1, 1).unitVector();
        }

        permX = perlinGeneratePerm();
        permY = perlinGeneratePerm();
        permZ = perlinGeneratePerm();

    }

    double noise(Vector3 point) {
        double u = point.x - Math.floor(point.x);
        double v = point.y - Math.floor(point.y);
        double w = point.z - Math.floor(point.z);
        int i = (int) Math.floor(point.x);
        int j = (int) Math.floor(point.y);
        int k = (int) Math.floor(point.z);
        Vector3[][][] c = new Vector3[2][2][2];

        for (int di = 0; di < 2; di++) {
            for (int dj = 0; dj < 2; dj++) {
                for (int dk = 0; dk < 2; dk++) {
                    c[di][dj][dk] = ranVector[permX[(i + di) & 255] ^ permY[(j + dj) & 255] ^ permZ[(k + dk) & 255]];
                }
            }
        }

        return perlinInterp(c, u, v, w);
    }

    private static int[] perlinGeneratePerm(){
        int[] p = new int[pointCount];

        for(int i = 0; i < pointCount; i++){
            p[i] = i;
        }

        permute(p, pointCount);

        return p;
    }

    private static void permute(int[] p, int n){
        for(int i = n -1; i > 0; i--){
            int target = Utility.getRandInt(0,i);
            int tmp = p[i];
            p[i] = p[target];
            p[target] = tmp;

        }
    }

    //Wat
    private static double perlinInterp(Vector3 c[][][], double u, double v, double w){
        double uu = u*u*(3-2*u);
        double vv = v*v*(3-2*v);
        double ww = w*w*(3-2*w);

        double accum = 0.0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Vector3 weightV = new Vector3(u-i, v-j, w-k);
                    accum += (i*uu + (1-i)*(1-uu))
                            * (j*vv + (1-j)*(1-vv))
                            * (k*ww + (1-k)*(1-ww))
                            * c[i][j][k].dot(weightV);
                }
            }
        }
        return accum;
    }



}
