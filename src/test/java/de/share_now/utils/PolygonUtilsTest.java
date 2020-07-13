package de.share_now.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.test.context.junit4.SpringRunner;

import static de.share_now.utils.PolygonUtils.isInsidePoly;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class PolygonUtilsTest {

    @Test
    public void testPolyInsideTrue() {

        assertTrue(isInsidePoly(getCoordinates(),
                9.1371735,
                48.790337));
    }

    @Test
    public void testPolyInsideFalse() {

        assertFalse(isInsidePoly(getCoordinates(),
                9.1871735,
                48.790337));
    }

    private Coordinate[] getCoordinates() {
        Coordinate[] points = {
                new Coordinate(9.137248, 48.790411),
                new Coordinate(9.137248, 48.790263),
                new Coordinate(9.13695, 48.790263),
                new Coordinate(9.137248, 48.790411)};
        return points;
    }
}
