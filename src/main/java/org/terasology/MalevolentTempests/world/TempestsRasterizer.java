package org.terasology.MalevolentTempests.world;

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

        for (Vector3i position: chunkRegion.getRegion()) {
            if (tempestsFacet.getWorld(position)) {
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
