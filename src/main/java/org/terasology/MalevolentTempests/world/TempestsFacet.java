package org.terasology.MalevolentTempests.world;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet3D;

public class TempestsFacet extends BaseBooleanFieldFacet3D {

    private int minCloudHeight = 512;
    private int cloudThickness = 4;

    public TempestsFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }


    public int getMinCloudHeight() {
        return minCloudHeight;
    }

    public int getCloudThickness() {
        return cloudThickness;
    }

    public int getMaxCloudHeight() {
        return minCloudHeight + cloudThickness;
    }

}