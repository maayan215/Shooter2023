package SpLib.util.bool.filters;

/** Defines an inerface to create a Boolean Filter, that gets a boolean as an input and returns a manipulated boolean value. */
public abstract interface IBooleanFilter {

    /**
     * Updates the filter with a new boolean, and returns the manipulated value.
     * @param input boolean input
     * @return Manipulated boolean value, based on the filter.
     */
    public boolean get(boolean input);

    /**
     * Resets the filter to its initial state.
     */
    public void reset();
    
}