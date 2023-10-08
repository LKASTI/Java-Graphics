package Rotation3D;
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

    private static void setXaxis(double degrees){
        r11 = 1; r12 = 0; r13 = 0;
        r21 = 0; r22 = Math.cos(degrees); r23 = Math.sin(degrees);
        r31 = 0; r32 = -Math.sin(degrees); r33 = Math.cos(degrees);
        
    }
    private static void setYaxis(double degrees){
        r11 = Math.cos(degrees); r12 = 0; r13 = -Math.sin(degrees);
        r21 = 0; r22 = 1; r23 = 0;
        r31 = Math.sin(degrees); r32 = 0; r33 = Math.cos(degrees);
    }
    private static void setZaxis(double degrees){
        r11 = Math.cos(degrees); r12 = Math.sin(degrees); r13 = 0;
        r21 = -Math.sin(degrees); r22 = Math.cos(degrees); r23 = 0;
        r31 = 0; r32 = 0; r33 = 1;
    }

    private static Point3D rot(Point3D p){
        return new Point3D(
            p.x * r11 + p.y * r21 + p.z * r31 + r41,
            p.x * r12 + p.y * r22 + p.z * r32 + r42,
            p.x * r13 + p.y * r23 + p.z * r33 + r43);
    }

    private static Point3D combinedTransformation(Point3D p, Point3D a){
        // Translate to origin
        p = new Point3D(p.x - a.x, p.y - a.y, p.z - a.z);
        // Rotate 
        p = rot(p);
        // Translate back
        p = new Point3D(p.x + a.x, p.y + a.y, p.z + a.z);

        return p;
    }

    public static void main(String[] args){
        /* Test Case */
        // a1= 2, a2 = 2, a3 = 2, angle = 45, yaxis
        if (args.length != 5)
            System.out.println("5 arguments must be entered");
        else{
            Point3D a = new Point3D(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            double alpha = Double.parseDouble(args[3]);
            String axis = args[4];

            switch(axis){
                case "x":
                    setXaxis(alpha);
                    break;
                case "y":
                    setYaxis(alpha);
                    break;
                case "z":
                    setZaxis(alpha);
                    break;
            }

             /* Cube vertices */
            Point3D[] v = {
                new Point3D(0, 0, 0), new Point3D(1, 0, 0),
                new Point3D(1, 1, 0), new Point3D(0, 1, 0),
                new Point3D(0, 0, 1), new Point3D(1, 0, 1),
                new Point3D(1, 1, 1), new Point3D(0, 1, 1)};
    
            System.out.println("Vertices of cube:");
            System.out.println("    Before rotation    After rotation");
    
            // Perform transformations on each vertice
            for(int i = 0; i < v.length; i++){
                Point3D p = v[i];
                Point3D new_p = combinedTransformation(a, p);
                System.out.println(i + ":  " + 
                p.x + " " + p.y + " " + p.z + "        " + 
                new_p.x + " " + new_p.y + " " + new_p.z);
            }
            
        }
    }
}
