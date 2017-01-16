package org.terasology.MalevolentTempests.world;

import org.terasology.MalevolentTempests.world.Updrafts.UpdraftFacet;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generator.plugin.RegisterPlugin;


/**
 * Rasterizes clouds.
 */

@RegisterPlugin
public class TempestsRasterizer implements WorldRasterizerPlugin{

    private Block lightCloud;
    private Block darkCloud;
    private Block water;

    @Override
    public void initialize() {
        lightCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:lightCloud");
        darkCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:darkCloud");
        water = CoreRegistry.get(BlockManager.class).getBlock("Core:water");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        TempestsFacet tempestsFacet = chunkRegion.getFacet(TempestsFacet.class);
        /* updraftFacet is needed to pinpoint places with updrafts and remove blocks from above them. */
        UpdraftFacet updraftFacet = chunkRegion.getFacet(UpdraftFacet.class);

        for (Vector3i position: chunkRegion.getRegion()) {
            if (tempestsFacet.getWorld(position)) {

                /* emptySpace is used to set a 3x3 region above the updraft as air. */
                boolean emptySpace = false;

                for (int wx = position.getX() - 3; wx < position.getX(); wx++) {
                    for (int wz = position.getZ() - 3; wz < position.getZ(); wz++) {
                        if(updraftFacet.getWorld(wx, wz))
                            emptySpace = true;
                    }
                }

                /* If the space is above an updraft, do not make anything here. */
                if(emptySpace)
                    continue;

                if (position.getY() == tempestsFacet.getMinCloudHeight()) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), water);
                }
                else if (position.getY() < tempestsFacet.getMaxCloudHeight() - 1) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), darkCloud);
                }
                else if (position.getY() == tempestsFacet.getMaxCloudHeight() - 1) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), lightCloud);
                }
                else if (Math.random() > 0.69) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), lightCloud);
                }
            }
        }
    }
}
