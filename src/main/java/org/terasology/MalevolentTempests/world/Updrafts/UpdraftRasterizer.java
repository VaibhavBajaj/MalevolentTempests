package org.terasology.MalevolentTempests.world.Updrafts;

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

    private Block updraftA;
    private Block updraftB;
    private Block updraft;

    @Override
    public void initialize() {
        updraftA = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:updraftGenA");
        updraftB = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:updraftGenB");
        updraft = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:updraft");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        UpdraftFacet updraftFacet = chunkRegion.getFacet(UpdraftFacet.class);
        int ctr = 0;

        for (Vector3i position: chunkRegion.getRegion()) {

            if (updraftFacet.getWorld(position)) {
                for (int wx = position.getX(); wx < position.getX() + 3; wx++) {
                    for (int wz = position.getZ(); wz < position.getZ() + 3; wz++) {
                        if(ctr % 2 == 0) {
                            chunk.setBlock(ChunkMath.calcBlockPos(wx, position.getY(), wz), updraftA);
                        }
                        else {
                            chunk.setBlock(ChunkMath.calcBlockPos(wx, position.getY(), wz), updraftB);
                        }
                        ctr++;
                        for (int wy = position.getY() + 1; wy < 520; wy++) {
                            chunk.setBlock(ChunkMath.calcBlockPos(wx, wy, wz), updraft);
                        }
                    }
                }
            }
            ctr %= 2;
        }
    }
}
