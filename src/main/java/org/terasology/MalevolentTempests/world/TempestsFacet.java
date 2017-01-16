package org.terasology.MalevolentTempests.world;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet3D;

/*
 * This facet stores the positions of the cloud in a boolean 3D field.
*/

public class TempestsFacet extends BaseBooleanFieldFacet3D {

    private final int minCloudHeight = 512;
    private final int cloudThickness = 4;

    public TempestsFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

    /*
     * Returns minCLoudHeight
     */
    public int getMinCloudHeight() {
        return minCloudHeight;
    }

    /*
     * Returns minCLoudHeight + cloudThickness. This is the maxCloudHeight
     */
    public int getMaxCloudHeight() {
        return minCloudHeight + cloudThickness;
    }

}
