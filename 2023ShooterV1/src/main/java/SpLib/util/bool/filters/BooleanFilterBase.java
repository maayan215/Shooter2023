package SpLib.util.bool.filters;

/** Base class of a Boolean Filter */
public abstract class BooleanFilterBase implements IBooleanFilter {

    //#region Private Members
    private boolean m_initialValue;
    protected boolean m_prevValue;
    //#endregion
    
    //#region Constructors
    public BooleanFilterBase(boolean initialValue) {
        m_initialValue = initialValue;
        m_prevValue = m_initialValue;
    }
    //#endregion

    //#region Public Methods
    @Override
    public void reset() {
        m_prevValue = m_initialValue;
    }
    //#endregion

}