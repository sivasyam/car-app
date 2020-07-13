package de.share_now.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class PolygonUtils {
    private static final Logger logger = LoggerFactory.getLogger(PolygonUtils.class);

    /**
     * Method to check whether the given points are inside polygon or not using JTS library
     */
    public static boolean isInsidePoly(Coordinate[] points, double x, double y) {

        GeometryFactory geometryFactory = new GeometryFactory();
        LinearRing linearRing = geometryFactory.createLinearRing(points);
        Polygon polygon = geometryFactory.createPolygon(linearRing, null);
        Coordinate cord = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(cord);
        if (polygon.contains(point)) {
            // point is contained within polygon
            return true;
        } else {
            // point is NOT contained within polygon
            return false;
        }
    }
}
