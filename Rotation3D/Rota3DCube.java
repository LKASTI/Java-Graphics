public class Rota3DCube {
    private static double r11= 0, r12 = 0, r13 = 0, 
                   r21 = 0, r22 = 0, r23 = 0,
                   r31 = 0, r32 = 0, r33 = 0, 
                   r41 = 0, r42 = 0, r43 = 0;

    // Copied from Chapter 3.9 of
    //    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
    //       Chichester: John Wiley.
    private static class Point3D{
        float x, y, z;
        Point3D(double x, double y, double z){
            this.x = (float) x; 
            this.y = (float) y;
            this.z = (float) z;
        }
    }

    private static void setXtransform(double x, double y, double z, double alpha){
        r11 = 1; r12 = 0; r13 = 0;
        r21 = 0; r22 = Math.cos(alpha); r23 = Math.sin(alpha);
        r31 = 0; r32 = -Math.sin(alpha); r33 = Math.cos(alpha);
        r41 = 0; r42 = y-(y * Math.cos(alpha)) + (z * Math.sin(alpha)); r43 = z - (z * Math.cos(alpha)) - (y * Math.sin(alpha));
    }
    private static void setYtransform(double x, double y, double z, double alpha){
        r11 = Math.cos(alpha); r12 = 0; r13 = -Math.sin(alpha);
        r21 = 0; r22 = 1; r23 = 0;
        r31 = Math.sin(alpha); r32 = 0; r33 = Math.cos(alpha);
        r41 = x-(x * Math.cos(alpha)) - (z * Math.sin(alpha)); r42 = 0; r43 = z - (z * Math.cos(alpha)) + (x * Math.sin(alpha));
    }
    private static void setZtransform(double x, double y, double z, double alpha){
        r11 = Math.cos(alpha); r12 = Math.sin(alpha); r13 = 0;
        r21 = -Math.sin(alpha); r22 = Math.cos(alpha); r23 = 0;
        r31 = 0; r32 = 0; r33 = 1;
        r41 = x-(x * Math.cos(alpha)) + (y * Math.sin(alpha)); r42 = y - (y * Math.cos(alpha)) - (x * Math.sin(alpha)); r43 = 0;
    }

    private static Point3D rot(Point3D p){
        return new Point3D(
            p.x * r11 + p.y * r21 + p.z * r31,
            p.x * r12 + p.y * r22 + p.z * r32,
            p.x * r13 + p.y * r23 + p.z * r33);
    }

    private static Point3D combinedTransformation(Point3D p){
        return new Point3D(p.x * r11 + p.y * r21 + p.z * r31 + r41,
                           p.x * r12 + p.y * r22 + p.z * r32 + r42,
                           p.x * r13 + p.y * r23 + p.z * r33 + r43);
    }

    public static void main(String[] args){
        /* Test Case */
        // a1= 2, a2 = 2, a3 = 2, angle = 45, yaxis
        if (args.length != 5)
            System.out.println("5 arguments must be entered");
        else{
            Point3D a = new Point3D(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            double alpha = Double.parseDouble(args[3]) * (Math.PI/180);
            String axis = args[4];

            switch(axis){
                case "x":
                    setXtransform(a.x, a.y, a.z, alpha);
                    break;
                case "y":
                    setYtransform(a.x, a.y, a.z, alpha);
                    break;
                case "z":
                    setZtransform(a.x, a.y, a.z, alpha);
                    break;
            }

             /* Cube vertices */
            Point3D[] v = {
                new Point3D(0, 0, 0), new Point3D(1, 0, 0),
                new Point3D(1, 0, 1), new Point3D(0, 0, 1),
                new Point3D(0, 1, 1), new Point3D(1, 1, 1),
                new Point3D(1, 1, 0), new Point3D(0, 1, 0)};
    
            System.out.println("Vertices of cube:");
            System.out.println("    Before rotation    After rotation");
    
            // Perform transformations on each vertice
            for(int i = 0; i < v.length; i++){
                Point3D p = v[i];
                Point3D new_p = combinedTransformation(p);
                System.out.println(i + ":  " + 
                p.x + " " + p.y + " " + p.z + "        " + 
                new_p.x + " " + new_p.y + " " + new_p.z);
            }
            
        }
    }
}
