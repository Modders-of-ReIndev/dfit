package ru.marduk.dfit.client.gui.line;

public interface ITooltipLineComponent {
    /**
     * Renders the component according to the supplied parameters
     *
     * @param x x coordinate relative to tooltip position
     * @param y y coordinate relative to tooltip position
     * @param w maximum line width
     * @param h maximum line height
     */
    void render(int x, int y, int w, int h);
    /**
     * Returns the width of the rendered object
     * The computing the width part should be done in the constructor
     *
     * @return the width of the component
     */
    default int width() {
        return -1;
    }
}
