package org.terasology.MalevolentTempests.world.Updrafts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generator.plugin.RegisterPlugin;


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

        for (Vector3i position: chunkRegion.getRegion()) {

            if (updraftFacet.getWorld(position.getX(), position.getZ())) {
                for (int wx = position.getX(); wx < position.getX() + 3; wx++) {
                    for (int wz = position.getZ(); wz < position.getZ() + 3; wz++) {
                        if(position.getY() < 495 &&
                                ((position.getY() % 3 == 0) || ((-1 *position.getY()) % 3 == 0)))
                            chunk.setBlock(ChunkMath.calcBlockPos(wx, position.getY(), wz), updraft);
                    }
                }
            }
        }
    }
}
