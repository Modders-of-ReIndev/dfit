package ru.marduk.dfit.client.handler;

import ru.marduk.dfit.client.gui.TooltipComponent;

public abstract class ITooltipResultHandler<T> {
    abstract void handle(T obj, TooltipComponent pTooltip);

    public boolean equals(Object object) {
        Class<T> type = (Class<T>)( new Object()).getClass();
        return type.isInstance(object);
    }
}
