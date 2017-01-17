package org.terasology.MalevolentTempests.world.Expanses;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFieldFacet2D;

/*
 * This facet uses a 2D field for ease of spawning expanses above any place marked -1, -2 in field.
 * Expanses can be of two types: empty, thin-cloud (Same as empty except there is thick cloud there, not air)
 * For any additions, simply add the block, blockTile, add a number to totalRegionTypes in ExpanseProvider and
 * set that block in Rasterizer.
 */

public class ExpanseFacet extends BaseFieldFacet2D {

    public ExpanseFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);

    }
}
