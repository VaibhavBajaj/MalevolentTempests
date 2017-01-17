package org.terasology.MalevolentTempests.systems;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.EventPriority;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.inventory.ItemComponent;
import org.terasology.logic.inventory.events.DropItemEvent;

/**
 * Makes sure cloud blocks do not drop blocks when destroyed.
 */

@RegisterSystem
public class CloudBlockDropManager extends BaseComponentSystem{

    @ReceiveEvent (priority = EventPriority.PRIORITY_HIGH)
    public void onDropItemEvent(DropItemEvent event, EntityRef itemEntity, ItemComponent itemComponent) {

        /* If block to be dropped is a cloud, destroy it before it is dropped. */
        if (itemComponent.stackId.equals("block:MalevolentTempests:lightCloud") ||
                itemComponent.stackId.equals("block:MalevolentTempests:darkCloud") ||
                itemComponent.stackId.equals("block:MalevolentTempests:thinCloud")) {
            itemEntity.destroy();
        }
    }

}
