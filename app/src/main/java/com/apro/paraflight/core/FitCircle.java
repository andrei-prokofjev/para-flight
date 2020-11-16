package com.apro.paraflight.core;

class FitCircle {


    /**
     * Pratt method (Newton style)
     *
     * @param points containing n (<i>x</i>, <i>y</i>) coordinates
     * @return double[] containing (<i>x</i>, <i>y</i>) centre and radius
     */
    public static double[] prattNewton(final double[][] points) {
        final int nPoints = points.length;
        if (nPoints < 3)
            throw new IllegalArgumentException("Too few points");
        final double[] centroid = Centroid.getCentroid(points);
        double Mxx = 0, Myy = 0, Mxy = 0, Mxz = 0, Myz = 0, Mzz = 0;

        for (double[] point : points) {
            final double Xi = point[0] - centroid[0];
            final double Yi = point[1] - centroid[1];
            final double Zi = Xi * Xi + Yi * Yi;
            Mxy += Xi * Yi;
            Mxx += Xi * Xi;
            Myy += Yi * Yi;
            Mxz += Xi * Zi;
            Myz += Yi * Zi;
            Mzz += Zi * Zi;
        }
        Mxx /= nPoints;
        Myy /= nPoints;
        Mxy /= nPoints;
        Mxz /= nPoints;
        Myz /= nPoints;
        Mzz /= nPoints;

        final double Mz = Mxx + Myy;
        final double Cov_xy = Mxx * Myy - Mxy * Mxy;
        final double Mxz2 = Mxz * Mxz;
        final double Myz2 = Myz * Myz;

        final double A2 = 4 * Cov_xy - 3 * Mz * Mz - Mzz;
        final double A1 = Mzz * Mz + 4 * Cov_xy * Mz - Mxz2 - Myz2 - Mz * Mz * Mz;
        final double A0 = Mxz2 * Myy + Myz2 * Mxx - Mzz * Cov_xy - 2 * Mxz * Myz * Mxy + Mz * Mz * Cov_xy;
        final double A22 = A2 + A2;

        final double epsilon = 1e-12;
        double ynew = 1e+20;
        final int IterMax = 20;
        double xnew = 0;
        for (int iter = 0; iter <= IterMax; iter++) {
            final double yold = ynew;
            ynew = A0 + xnew * (A1 + xnew * (A2 + 4 * xnew * xnew));
            if (Math.abs(ynew) > Math.abs(yold)) {
                System.out.println("Newton-Pratt goes wrong direction: |ynew| > |yold|");
                xnew = 0;
                break;
            }
            final double Dy = A1 + xnew * (A22 + 16 * xnew * xnew);
            final double xold = xnew;
            xnew = xold - ynew / Dy;
            if (Math.abs((xnew - xold) / xnew) < epsilon) {
                break;
            }
            if (iter >= IterMax) {
                System.out.println("Newton-Pratt will not converge");
                xnew = 0;
            }
            if (xnew < 0) {
                System.out.println("Newton-Pratt negative root:  x= " + xnew);
                xnew = 0;
            }
        }
        final double det = xnew * xnew - xnew * Mz + Cov_xy;

        final double x = (Mxz * (Myy - xnew) - Myz * Mxy) / (det * 2);
        final double y = (Myz * (Mxx - xnew) - Mxz * Mxy) / (det * 2);
        final double r = Math.sqrt(x * x + y * y + Mz + 2 * xnew);

        return new double[]{x + centroid[0], y + centroid[1], r};
    }


    /**
     * Taubin method (Newton Style)
     *
     * @param points containing n (<i>x</i>, <i>y</i>) coordinates
     * @return double[] containing (<i>x</i>, <i>y</i>) centre and radius
     */
    public static double[] taubinNewton(final double[][] points) {
        final int nPoints = points.length;
        if (nPoints < 3)
            throw new IllegalArgumentException("Too few points");
        final double[] centroid = Centroid.getCentroid(points);
        double Mxx = 0, Myy = 0, Mxy = 0, Mxz = 0, Myz = 0, Mzz = 0;
        for (double[] point : points) {
            final double Xi = point[0] - centroid[0];
            final double Yi = point[1] - centroid[1];
            final double Zi = Xi * Xi + Yi * Yi;
            Mxy += Xi * Yi;
            Mxx += Xi * Xi;
            Myy += Yi * Yi;
            Mxz += Xi * Zi;
            Myz += Yi * Zi;
            Mzz += Zi * Zi;

        }
        Mxx /= nPoints;
        Myy /= nPoints;
        Mxy /= nPoints;
        Mxz /= nPoints;
        Myz /= nPoints;
        Mzz /= nPoints;

        final double Mz = Mxx + Myy;
        final double Cov_xy = Mxx * Myy - Mxy * Mxy;
        final double A3 = 4 * Mz;
        final double A2 = -3 * Mz * Mz - Mzz;
        final double A1 = Mzz * Mz + 4 * Cov_xy * Mz - Mxz * Mxz - Myz * Myz - Mz * Mz * Mz;
        final double A0 = Mxz * Mxz * Myy + Myz * Myz * Mxx - Mzz * Cov_xy - 2 * Mxz * Myz * Mxy + Mz * Mz * Cov_xy;
        final double A22 = A2 + A2;
        final double A33 = A3 + A3 + A3;

        double xnew = 0;
        double ynew = 1e+20;
        final double epsilon = 1e-12;
        final double iterMax = 20;

        for (int iter = 0; iter < iterMax; iter++) {
            final double yold = ynew;
            ynew = A0 + xnew * (A1 + xnew * (A2 + xnew * A3));
            if (Math.abs(ynew) > Math.abs(yold)) {
                System.out.println("Newton-Taubin goes wrong direction: |ynew| > |yold|");
                xnew = 0;
                break;
            }
            final double Dy = A1 + xnew * (A22 + xnew * A33);
            final double xold = xnew;
            xnew = xold - ynew / Dy;
            if (Math.abs((xnew - xold) / xnew) < epsilon) {
                break;
            }
            if (iter >= iterMax) {
                System.out.println("Newton-Taubin will not converge");
                xnew = 0;
            }
            if (xnew < 0.) {
                System.out.println("Newton-Taubin negative root: x = " + xnew);
                xnew = 0;
            }
        }
        final double[] centreRadius = new double[3];
        final double det = xnew * xnew - xnew * Mz + Cov_xy;
        final double x = (Mxz * (Myy - xnew) - Myz * Mxy) / (det * 2);
        final double y = (Myz * (Mxx - xnew) - Mxz * Mxy) / (det * 2);
        centreRadius[0] = x + centroid[0];
        centreRadius[1] = y + centroid[1];
        centreRadius[2] = Math.sqrt(x * x + y * y + Mz);

        return centreRadius;
    }

}
