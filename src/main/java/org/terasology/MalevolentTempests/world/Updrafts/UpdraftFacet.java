package org.terasology.MalevolentTempests.world.Updrafts;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet2D;

/*
 * This facet uses a 2D field for ease of spawning updrafts above any place marked true in field.
 */

public class UpdraftFacet extends BaseBooleanFieldFacet2D {

    /* The distance between two successive updrafts. */
    private final int updraftDistance = 3;

    public UpdraftFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);

    }

    public int getUpdraftDistance() {
        return updraftDistance;
    }

}
