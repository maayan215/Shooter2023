package SpLib.util.conversions;

import edu.wpi.first.math.util.Units;

/** For More units conversions, see {@link Units} */
public class UnitsConversions {
    
    private UnitsConversions() {
        throw new AssertionError("utility class");
    }
    
    //#region Length
    // Only Conversions *To* Meters are implemented, as we want to work only at SI.

    public static double inchesToMeters(double inches) {
        return inches * 0.0254;
    } 

    public static double feetToMeters(double ft) {
        return inchesToMeters(ft * 12.0);
    }

    public static double centimetersToMeters(double cm) {
        return cm / 100.0;
    }

    public static double milimetersToMeters(double mm) {
        return mm / 1000.0;
    }
    //#endregion
    
    //#region Velocity
    /**
     * @param velocity Linear Velocity
     * @param circumference Wheel circumference (need to be in the same unit measurement as velocity)
     * @return RPM (Revolutions per Minute)
     */
    public static double MPSToRPM(double velocity, double circumference) {
        double rpm = ((velocity * 60) / circumference);
        return rpm;
    }

    /**
     * @param rpm RPM (Revolutions per Minute)
     * @param circumference Wheel circumference (in desired unit measurement)
     * @return Linear Velocity (in the same unit measurement as circumference)
     */
    public static double RPMToMPS(double rpm, double circumference) {
        double velocity = (rpm * circumference) / 60;
        return velocity;
    }

    /**
     * @param rpm RPM (Revolutions per Minute)
     * @return RPS (Radians per Second)
     */
    public static double RPMToRPS(double rpm) {
        return rpm * 2.0 * Math.PI / 60.0;
    }

    /**
     * @param rps RPS (Radians per Second)
     * @return RPM (Revolutions per Minute)
     */
    public static double RPSToRPM(double rps) {
        return rps * 60.0 / (2.0 * Math.PI);
    }

    /**
     * @param velocity Linear Velocity
     * @param circumference Wheel circumference (need to be in the same unit measurement as velocity)
     * @return RPS (Radians per Second)
     */
    public static double MPSToRPS(double velocity, double circumference) {
        double rpm = MPSToRPM(velocity, circumference);
        return RPMToRPS(rpm);
    }

    /**
     * @param rps RPS (Radians per Second)
     * @param circumference Wheel circumference (need to be in the same unit measurement as velocity)
     * @return Linear Velocity (in the same unit measurement as circumference)
     */
    public static double RPSToMPS(double rps, double circumference) {
        double rpm = RPSToRPM(rps);
        return RPMToMPS(rpm, circumference);
    }
    //#endregion

    //#region Time
    // Only Conversions *To* Seconds are implemented, as we want to work only at SI.
    
    public static double milisecondsToSeconds(double ms) {
        return ms * 0.001;
    }

    public static double minutesToSeconds(double seconds) {
        return seconds * 60.0;
    }

    public static double hoursToSeconds(double hours) {
        return minutesToSeconds(hours) * 60.0;
    } 
    //#endregion

    //#region Weight
    // Only Conversions *To* Kilograms are implemented, as we want to work only at SI.

    public static double poundToKg(double lbs) {
        return lbs * 0.45359237;
    }
    //#endregion
}