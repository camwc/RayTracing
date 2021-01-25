package rayTracer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.util.Arrays;

public class BVHNode extends Hittable{
    Hittable left;
    Hittable right;
    AABB box;

    public BVHNode(HittableList list){
        this(list.objects, 0, list.objects.size());
    }

    public BVHNode(ArrayList<Hittable> objects, int start, int end){
        ArrayList srcObjects = objects;

        int axis = Utility.getRandInt(0,2);
        Comparator<Hittable> comparator = new boxCompare(axis);

        int objectSpan = end - start;

        if(objectSpan == 1){
            left = right = objects.get(start);
        } else if(objectSpan == 2){
            if(comparator.compare(objects.get(start), objects.get(start+1)) < 0){
                left = objects.get(start);
                right = objects.get(start + 1);
            } else{
                left = objects.get(start + 1);
                right = objects.get(start);
            }
        } else{
            Hittable[] a = new Hittable[objects.size()];
            for(int i = 0; i < objects.size(); i++){
                a[i] = objects.get(i);
            }
            Arrays.sort(a, start, end, comparator);
            objects.clear();
            objects.addAll(Arrays.asList(a));

            int mid = start + objectSpan/2;
            left = new BVHNode(objects, start, mid);
            right = new BVHNode(objects, mid, end);
        }

        AABB boxLeft = new AABB();
        AABB boxRight = new AABB();

        if(!left.boundingBox(boxLeft) || !right.boundingBox(boxRight)){
            System.out.println("No bounding box in bvh_node constructor.");
        }

        box = AABB.surroundingBox(boxLeft, boxRight);

    }

    @Override
    boolean hit(Ray r, double tMin, double tMax, HitRecord record) {
        if(!box.hit(r,tMin, tMax)){
            return false;
        }

        boolean hitLeft = left.hit(r, tMin, tMax, record);
        boolean hitRight = right.hit(r, tMin, (hitLeft ? record.t : tMax), record);

        return hitLeft||hitRight;
    }

    @Override
    boolean boundingBox(AABB outputBox) {
        outputBox.changeValues(box);
        return true;
    }

    class boxCompare implements Comparator<Hittable>{

        int axis;

        public boxCompare(int axis){
            this.axis = axis;
        }


        @Override
        public int compare(Hittable a, Hittable b) {
            AABB boxA = new AABB();
            AABB boxB = new AABB();
            if(!a.boundingBox(boxA) || !b.boundingBox(boxB)){
                System.out.println("No bounding box in bvh_node constructor");
                System.exit(-1);
            }

            return (int)((boxB.minimum.e[axis] - boxA.minimum.e[axis])*2);
        }
    }
}
