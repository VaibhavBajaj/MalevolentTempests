package org.terasology.MalevolentTempests.world.Updrafts;

import org.terasology.MalevolentTempests.world.TempestsFacet;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generator.plugin.RegisterPlugin;

/*
 * Rasterizes updrafts based on areas marked true by updraftsProvider
 */

@RegisterPlugin
public class UpdraftRasterizer implements WorldRasterizerPlugin{

    private Block updraft;

    @Override
    public void initialize() {
        updraft = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:updraft");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        UpdraftFacet updraftFacet = chunkRegion.getFacet(UpdraftFacet.class);
        /* tempestsFacet is used to get maxCloudHeight. */
        TempestsFacet tempestsFacet = chunkRegion.getFacet(TempestsFacet.class);

        for (Vector3i position: chunkRegion.getRegion()) {
            if (updraftFacet.getWorld(position.getX(), position.getZ())) {
                /*
                 * Makes sure updraft continues till little less than maxCloudHeight (20m less).
                 * (position.getY() % 3 makes sure updrafts are set at demarcations of 3.
                 * Since mod (%) is only valid for positive value, -1 is multiplied to check negatives.
                 *
                 */
                if((position.getY() < (tempestsFacet.getMaxCloudHeight() - 20)) &&
                        ((position.getY() % 3 == 0) || ((-1 * position.getY()) % 3 == 0))) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), updraft);
                }
            }
        }
    }
}
