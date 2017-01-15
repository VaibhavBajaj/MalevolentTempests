package org.terasology.MalevolentTempests.world.Updraft;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet3D;

public class UpdraftFacet  extends BaseBooleanFieldFacet3D {

    public UpdraftFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
