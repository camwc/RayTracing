package rayTracer;

public class Utility {
    public static double getRandDouble(double min, double max){
        return min + (max-min)*Math.random();
    }

    public static int getRandInt(int min, int max){
        return (int)getRandDouble(min, max+1);
    }

    //Fast acos
    //https://developer.download.nvidia.com/cg/acos.html
    public static double acos(double x){
        double negate = (x < 0)?(1):(0);
        x = Math.abs(x);
        double ret = -0.0187293;
        ret = ret * x;
        ret = ret + 0.0742610;
        ret = ret * x;
        ret = ret - 0.2121144;
        ret = ret * x;
        ret = ret + 1.5707288;
        ret = ret * Math.sqrt(1.0-x);
        ret = ret - 2 * negate * ret;
        return negate * 3.14159265358979 + ret;
    }

    //Fast atan2
    //https://developer.download.nvidia.com/cg/atan2.html
    public static double atan2(double y, double x) {
        double t0, t1, t2, t3, t4;

        t3 = Math.abs(x);
        t1 = Math.abs(y);
        t0 = Math.max(t3, t1);
        t1 = Math.min(t3, t1);
        t3 = 1.0 / t0;
        t3 = t1 * t3;

        t4 = t3 * t3;
        t0 =         - 0.013480470;
        t0 = t0 * t4 + 0.057477314;
        t0 = t0 * t4 - 0.121239071;
        t0 = t0 * t4 + 0.195635925;
        t0 = t0 * t4 - 0.332994597;
        t0 = t0 * t4 + 0.999995630;
        t3 = t0 * t3;

        t3 = (Math.abs(y) > Math.abs(x)) ? 1.570796327 - t3 : t3;
        t3 = (x < 0) ?  3.141592654 - t3 : t3;
        t3 = (y < 0) ? -t3 : t3;

        return t3;
    }


}
