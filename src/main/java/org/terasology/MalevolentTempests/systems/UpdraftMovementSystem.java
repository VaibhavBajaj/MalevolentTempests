package org.terasology.MalevolentTempests.systems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.MalevolentTempests.components.UpdraftComponent;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.delay.DelayManager;
import org.terasology.logic.delay.PeriodicActionTriggeredEvent;
import org.terasology.logic.location.LocationComponent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockComponent;
import org.terasology.world.block.BlockManager;

@RegisterSystem
public class UpdraftMovementSystem extends BaseComponentSystem {

    @In
    EntityManager entityManager;

    @In
    WorldProvider worldProvider;

    /* The code we use for swapUpdraftBlock events*/
    private static final String PERIODIC_UPDRAFT_MOVEMENT = "Move Updraft Periodically Upward";

    private Block updraft = CoreRegistry.get(BlockManager.class).getBlock("MalevolentTempests:updraft");
    private Block air = CoreRegistry.get(BlockManager.class).getBlock(BlockManager.AIR_ID);

    /* This will be used to control delay time of periodic event. */
    private DelayManager delayManager = CoreRegistry.get(DelayManager.class);
    private Logger logger = LoggerFactory.getLogger(UpdraftMovementSystem.class);

    /* This function creates an elevator like animation of the updrafts by sending periodic swapBlock events.*/
    @ReceiveEvent
    public void startUpdraftAnimation(OnPlayerSpawnedEvent event, EntityRef player) {
        if(!delayManager.hasPeriodicAction(player, PERIODIC_UPDRAFT_MOVEMENT)) {
            delayManager.addPeriodicAction(player, PERIODIC_UPDRAFT_MOVEMENT,
                    300, 300);
        }
    }

    @ReceiveEvent
    public void periodicUpdraftMovement(PeriodicActionTriggeredEvent event, EntityRef entity) {

        if (event.getActionId().equals(PERIODIC_UPDRAFT_MOVEMENT)) {
            for (EntityRef block : entityManager.getEntitiesWith(UpdraftComponent.class, LocationComponent.class)) {
                Vector3i blockPos = block.getComponent(BlockComponent.class).getPosition();
                logger.info("blockPos" + blockPos);
                if (blockPos != null) {
                    Vector3i newBlockPos = new Vector3i(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                    Block test = worldProvider.setBlock(blockPos, air);
                    logger.info("Block" + test);
                    test = worldProvider.setBlock(newBlockPos, updraft);
                    logger.info("Block" + test);
                }
            }
        }
    }
}
