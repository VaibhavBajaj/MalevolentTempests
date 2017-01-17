package org.terasology.MalevolentTempests.world;

import org.terasology.MalevolentTempests.world.Expanses.ExpanseFacet;
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


/*
 * Rasterizes clouds and rasterizes expanses.
 */

@RegisterPlugin
public class TempestsRasterizer implements WorldRasterizerPlugin{

    private Block lightCloud;
    private Block darkCloud;
    private Block thinCloud;
    private Block water;

    @Override
    public void initialize() {
        lightCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:lightCloud");
        darkCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:darkCloud");
        thinCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:thinCloud");
        water = CoreRegistry.get(BlockManager.class).getBlock("Core:water");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        TempestsFacet tempestsFacet = chunkRegion.getFacet(TempestsFacet.class);
        /* updraftFacet is needed to pinpoint places with updrafts and remove blocks from above them. */
        UpdraftFacet updraftFacet = chunkRegion.getFacet(UpdraftFacet.class);
        ExpanseFacet expanseFacet = chunkRegion.getFacet(ExpanseFacet.class);

        for (Vector3i position: chunkRegion.getRegion()) {

            /*
            * I use the tempestsFacet to confirm that this area is within the range of the cloud height.
            * The position can only be negative if our provider marked this area as something.
            */
            if(tempestsFacet.getWorld(position) && expanseFacet.getWorld(position.getX(), position.getZ()) < 0) {
                if (expanseFacet.getWorld(position.getX(), position.getZ()) == -1) {
                    /* Empty */
                    continue;
                }
                else if (expanseFacet.getWorld(position.getX(), position.getZ()) == -2) {
                    /* ThinCloud */
                    if (position.getY() == tempestsFacet.getMinCloudHeight()) {
                        chunk.setBlock(ChunkMath.calcBlockPos(position), water);
                    }
                    else if (position.getY() < tempestsFacet.getMaxCloudHeight()) {
                        chunk.setBlock(ChunkMath.calcBlockPos(position), thinCloud);
                    }
                    else if (Math.random() > 0.69) {
                        chunk.setBlock(ChunkMath.calcBlockPos(position), thinCloud);
                    }
                }
            }
            /* If the space is above an updraft, do not make anything here. */
            else if (tempestsFacet.getWorld(position) && !updraftFacet.getWorld(position.getX(), position.getZ())) {

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
