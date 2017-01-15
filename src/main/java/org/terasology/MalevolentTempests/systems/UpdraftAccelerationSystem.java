package org.terasology.MalevolentTempests.systems;

import org.terasology.MalevolentTempests.components.UpwardAccelerationComponent;
import org.terasology.MalevolentTempests.world.Updrafts.UpdraftFacet;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;
import org.terasology.logic.characters.CharacterImpulseEvent;
import org.terasology.logic.characters.CharacterMovementComponent;
import org.terasology.logic.location.LocationComponent;
import org.terasology.math.geom.Vector3f;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;

public class UpdraftAccelerationSystem extends BaseComponentSystem implements UpdateSubscriberSystem{

    @In
    private EntityManager entityManager;
    private boolean isEnabled;
    private float acc = 20;

    public void checkEnabled() {

        UpdraftFacet facet = CoreRegistry.get(UpdraftFacet.class);

        for (EntityRef entity : entityManager.getEntitiesWith(UpwardAccelerationComponent.class)) {
            Vector3f position = entity.getComponent(LocationComponent.class).getWorldPosition();
            if(facet.getPos(Math.round(position.getX()), Math.round(position.getZ()))) {
                entity.getComponent(UpwardAccelerationComponent.class).setEnabled(true);
            }
        }
    }

    @Override
    public void update(float delta) {

        for (EntityRef entity : entityManager.getEntitiesWith(UpwardAccelerationComponent.class)) {
            if(entity.getComponent(UpwardAccelerationComponent.class).getEnabled()) {
                float yVelocity = entity.getComponent(CharacterMovementComponent.class).getVelocity().y;
                entity.send(new CharacterImpulseEvent(new Vector3f(0, yVelocity + acc, 0)));
            }
        }
    }
}
