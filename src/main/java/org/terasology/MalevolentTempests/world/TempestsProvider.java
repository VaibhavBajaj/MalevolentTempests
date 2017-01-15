package org.terasology.MalevolentTempests.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProviderPlugin;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

@RegisterPlugin
@Produces(TempestsFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class))
public class TempestsProvider implements FacetProviderPlugin {

    private Logger logger = LoggerFactory.getLogger(TempestsProvider.class);

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(TempestsFacet.class);
        TempestsFacet facet = new TempestsFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (BaseVector2i position : surfaceHeightFacet.getWorldRegion().contents()) {
            int surfaceHeight = (int) surfaceHeightFacet.getWorld(position);

            if (surfaceHeight >= facet.getMinCloudHeight() &&
                    surfaceHeight <= facet.getMaxCloudHeight()) {
                facet.setWorld(position.getX(), surfaceHeight, position.getY(), true);
            }
        }

        region.setRegionFacet(TempestsFacet.class, facet);
    }
}
