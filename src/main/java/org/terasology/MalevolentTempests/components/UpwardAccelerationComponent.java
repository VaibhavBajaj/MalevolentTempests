package org.terasology.MalevolentTempests.components;

import org.terasology.entitySystem.Component;

public class UpwardAccelerationComponent implements Component{

    public boolean isEnabled = false;

    public void setEnabled (boolean bool) {
        isEnabled = bool;
    }

    public boolean getEnabled() {
        return isEnabled;
    }

}
