package org.terasology.MalevolentTempests.world.Updrafts;

import org.terasology.MalevolentTempests.world.TempestsFacet;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.WhiteNoise;
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
@Produces(UpdraftFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 4)))
public class UpdraftProvider implements FacetProviderPlugin {

    private Noise noise;

    @Override
    public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(TempestsFacet.class).extendBy(1, 0, 4);
        UpdraftFacet facet = new UpdraftFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (BaseVector2i position : surfaceHeightFacet.getWorldRegion().contents()) {
            int surfaceHeight = (int) (surfaceHeightFacet.getWorld(position));
            if (facet.getWorldRegion().encompasses(position.getX(), surfaceHeight, position.getY())
                    && surfaceHeight >= facet.getWorldRegion().minY()
                    && surfaceHeight <= facet.getWorldRegion().maxY()
                    && noise.noise(position.getX(), position.getY()) > 0.9996) {
                facet.setWorld(position.getX(), surfaceHeight, position.getY(), true);
                facet.setPos(position.getX(), position.getY());
            }
        }

        region.setRegionFacet(UpdraftFacet.class, facet);
    }
}
