package org.terasology.MalevolentTempests.world;

import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.ArrayList;
import java.util.List;


@RegisterPlugin
public class TempestsRasterizer implements WorldRasterizerPlugin{

    private Block darkCloud;
    private Block reddishDarkCloud;

    @Override
    public void initialize() {
        darkCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:darkCloud");
        reddishDarkCloud = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:reddishDarkCloud");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {

        TempestsFacet tempestsFacet = chunkRegion.getFacet(TempestsFacet.class);

        for (Vector3i position: chunkRegion.getRegion()) {
            if (tempestsFacet.getWorld(position)
                    && position.getY() >= tempestsFacet.getMinCloudHeight() + (tempestsFacet.getCloudThickness() / 2)) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), darkCloud);
            }
            else {
                chunk.setBlock(ChunkMath.calcBlockPos(position), reddishDarkCloud);
            }
        }
    }
}
