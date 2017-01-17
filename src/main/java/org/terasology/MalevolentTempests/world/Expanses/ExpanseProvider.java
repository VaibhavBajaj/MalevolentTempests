package org.terasology.MalevolentTempests.world.Expanses;

import org.terasology.MalevolentTempests.world.TempestsFacet;
import org.terasology.MalevolentTempests.world.Updrafts.UpdraftFacet;
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

/*
 * Marks random [5-10]x[5w-10] block sets as true in the expanseFacet.
 */

@RegisterPlugin
@Produces(UpdraftFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 4)))
public class ExpanseProvider implements FacetProviderPlugin {

    private Noise noise;
    private int totalRegionTypes = 2;
    private int minDimension = 5;
    private int maxDimension = 10;

    @Override
    public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }

    @Override
    public void process(GeneratingRegion region) {

        int zDimension = ((int)(Math.random() * (maxDimension - minDimension))) + minDimension;
        int xDimension = ((int)(Math.random() * (maxDimension - minDimension))) + minDimension;

        Border3D border = region.getBorderForFacet(TempestsFacet.class).extendBy(4, 4, 4);
        ExpanseFacet facet = new ExpanseFacet(region.getRegion(), border);
        UpdraftFacet updraftFacet = new UpdraftFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (BaseVector2i position : surfaceHeightFacet.getWorldRegion().contents()) {
            if (noise.noise(position.getX(), position.getY()) > 0.99) {
                /* Stores validity of region where expanse is to be spawned. */
                boolean regionValid = true;
                /* Generates a number between -1 and (-1 * totalRegionTypes). */
                int regionType = (((int)(Math.random() * totalRegionTypes)) * -1) - 1;

                /* Check that this region is withing spawning area and not occupied by an updraft. */
                for (int wx = position.getX(); wx < position.getX() + xDimension; wx++) {
                    for (int wz = position.getY(); wz < position.getY() + zDimension; wz++) {
                        if (!facet.getWorldRegion().contains(wx, wz)) {
                            regionValid = false;
                            break;
                        }
                        else if (updraftFacet.getWorld(wx, wz)) {
                            regionValid = false;
                            break;
                        }
                    }
                }

                /*
                 * If region is valid, mark it for spawn [-1:empty, -2:thin-cloud]
                 * I chose negative values because no other values in facet have negative values.
                 */
                if(regionValid) {
                    for (int wx = position.getX(); wx < position.getX() + xDimension; wx++) {
                        for (int wz = position.getY(); wz < position.getY() + zDimension; wz++) {
                            facet.setWorld(wx, wz, regionType);
                        }
                    }
                }
            }
        }

        region.setRegionFacet(ExpanseFacet.class, facet);
    }
}