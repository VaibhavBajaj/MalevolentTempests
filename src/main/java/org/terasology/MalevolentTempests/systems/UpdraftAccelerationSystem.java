package org.terasology.MalevolentTempests.systems;

import org.terasology.MalevolentTempests.components.UpdraftAccelerationComponent;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.characters.CharacterImpulseEvent;
import org.terasology.logic.characters.CharacterMoveInputEvent;
import org.terasology.logic.characters.CharacterMovementComponent;
import org.terasology.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3f;
import org.terasology.registry.In;

/**
 * Causes character to accelerate upward when on updraft Blocks.
 */

@RegisterSystem
public class UpdraftAccelerationSystem extends BaseComponentSystem {

    private final float acceleration = 1;
    /* maxVelocity can be changed to go faster or slower. The faster one goes, the more damage he receives on landing */
    private final float maxVelocity = 40;

    @In
    private EntityManager entityManager;

    @ReceiveEvent
    public void onCharacterMovement(CharacterMoveInputEvent event, EntityRef player) {
        if (player.getComponent(LocationComponent.class) == null ||
                player.getComponent(LocationComponent.class).getWorldPosition() == null) {
            return;
        }

        /* Returns playerPosition on map. */
        Vector3f playerPosition = new Vector3f(player.getComponent(LocationComponent.class).getWorldPosition());

        /* Iterates through all updraft Blocks. */
        for (EntityRef block : entityManager.getEntitiesWith(UpdraftAccelerationComponent.class, LocationComponent.class)) {
            Vector3f blockPos = block.getComponent(LocationComponent.class).getWorldPosition();
            if (blockPos != null && playerPosition != null) {
                /* If player is on or very near an updraft Block */
                if (Math.round(playerPosition.getX()) == blockPos.getX()
                        && Math.round(playerPosition.getZ()) == blockPos.getZ()) {
                    /* Make sure player does not exceed maxVelocity */
                    if (player.getComponent(CharacterMovementComponent.class).getVelocity().getY() > maxVelocity) {
                        return;
                    }

                    /* Push player higher up! */
                    Vector3f impulse = new
                            Vector3f(0, acceleration, 0);
                    player.send(new CharacterImpulseEvent(impulse));
                }
            }
        }
    }
}
