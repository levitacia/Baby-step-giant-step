import java.util.*;

public class BSGS {
    public static void main(String[] args) {
        BSGS();
    }

    public static Point addPoints(Point P, Point Q, int p, int a) {
        if (P.getX() == 0 && P.getY() == 0) {
            return Q;
        }
        if (Q.getX() == 0 && Q.getY() == 0) {
            return P;
        }

        Point R = new Point(0, 0);

        int m;

        if(P.equals(Q)) {
            m = (3 * P.getX() * P.getX() + a) * modInverse(2 * P.getY(), p);
            m = m % p;
        } else {
            int diffX = P.getX() - Q.getX();
            if(diffX < 0) diffX = diffX + p;

            int diffY = P.getY() - Q.getY();
            if(diffY < 0) diffY = diffY + p;

            m = (diffY * modInverse(diffX, p)) % p;
        }
        if(m < 0) m = m + p;

        int tmp = (m * m - P.getX() - Q.getX())%p;
        if(tmp < 0) tmp += p;
        R.setX(tmp);
        tmp = (m * (P.getX() - R.getX()) - P.getY())%p;
        if(tmp < 0) tmp += p;
        R.setY(tmp);
        return R;
    }



    public static void BSGS (){
        int p = 97;
        int a = 15;
        int b = -13;
        int m = (int) Math.sqrt(order(a, b, p)) + 1; //корень порядка

        Point target = new Point(82, 49);
        Point P = new Point(15, 80);
        Point R = addPoints(P, P, p, a);
        Point inf = new Point(0, 0);

        Map<Integer, Point> babySteps = new HashMap<>();
        babySteps.put(0, inf);
        babySteps.put(1, P);

        for(int i = 2; i < m - 1; i++){
            R = addPoints(P, R, p, a);
            babySteps.put(i, R);
        }

        Point minusP = P;
        for(int i = 2; i <= m; i++){ //увеличить на 1 М
            minusP = addPoints(minusP, P, p, a);
        }
        minusP.setY(p - minusP.getY());
        Point Q = target;
        int x = 0;
        for(int i = 0; i <= m; i++){
            for(int j = 0; j < 9; j++){
                if(Q.equals(babySteps.get(j))){
                    x = i * m + j;
                    System.out.println(x);
                }
            }
            Q = addPoints(Q, minusP, p, a);
        }
    }
    public static int order(int A, int B, int p){
        int test1 = 0;
        int test2 = 0;
        int nCount = 1;
        for(int x = 0; x < p; x++) {
            for(int y = 0; y < p; y++) {
                test1 = ((int)Math.pow(x, 3)  + A*x + B) % p;
                test2 = ((int)Math.pow(y, 2)) % p ;

                if(test1 == test2) {
                    nCount++;
                }
            }
        }
        return nCount;
    }
    public static int modInverse(int a, int p) {
        int res = 0;
        for(int i = 0; i < p; i++){
            if((a * i)%p == 1){
                res = i;
                break;
            }
        }
        return res;
    }
}