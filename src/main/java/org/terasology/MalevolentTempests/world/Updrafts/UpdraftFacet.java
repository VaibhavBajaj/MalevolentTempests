package org.terasology.MalevolentTempests.world.Updrafts;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet2D;
import org.terasology.world.generation.facets.base.BaseBooleanFieldFacet3D;

public class UpdraftFacet  extends BaseBooleanFieldFacet3D {

    private static BaseBooleanFieldFacet2D updraftLoc;

    public UpdraftFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);

    }

    public void setPos(int x, int y) {
        updraftLoc.set(x, y, true);
    }

    public boolean getPos(int x, int y) {
        return updraftLoc.get(x, y);
    }

}
