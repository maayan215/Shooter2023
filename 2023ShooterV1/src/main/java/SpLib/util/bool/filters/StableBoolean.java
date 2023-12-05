package SpLib.util.bool.filters;

import edu.wpi.first.wpilibj.Timer;

/** Stable Boolean checks if a boolean value is stable for x timer in a row. */
public class StableBoolean extends BooleanFilterBase {

    //#region Private Members
    private final double m_timeThreshold;
    private Timer m_timer;
    //#endregion

    //#region Public Methods
    public StableBoolean(double timeThreshold) {
        super(false);
        m_timeThreshold = timeThreshold;
        m_timer = new Timer();
        m_timer.start();
    }
    //#endregion

    //#region Public Methods
    /** Returns true if the input is stable as true for x time in a row. 
     * @return Is true for x time in a row
    */
    @Override
    public boolean get(boolean input) {
        if (!m_prevValue && input) {
            reset();
        }
        m_prevValue = input;
        return input && m_timer.hasElapsed(m_timeThreshold);
    }

    @Override
    public void reset() {
        super.reset();
        m_timer.reset();
    }
    //#endregion
    
}