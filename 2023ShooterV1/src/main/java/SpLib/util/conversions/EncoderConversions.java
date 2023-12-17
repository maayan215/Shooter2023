package SpLib.util.conversions;

public class EncoderConversions {
    
    private EncoderConversions() {
        throw new AssertionError("utility class");
    }

    //#region Length
    /**
     * @param ticks Encoder Ticks
     * @param gearRatio Gear Ratio between Encoder and Mechanism
     * @param diameter Spool Diameter (Meters)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Length in Meters
     */
    public static double ticksToMeters(double ticks, double gearRatio, double diameter, double encoderResolution) {
        double spoolRotations = ticks / (gearRatio * encoderResolution);
        double length = spoolRotations * Math.PI * diameter;
        return length;
    }

    /**
     * @param length Length in Meters
     * @param gearRatio Gear Ratio between Encoder and Mechanism
     * @param diameter Spool Diameter (Meters)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Length in Ticks
     */
    public static double metersToTicks(double length, double gearRatio, double diameter, double encoderResolution) {
        double spoolRotations = length / (Math.PI * diameter);
        double ticks = spoolRotations * gearRatio * encoderResolution;
        return ticks;
    }
    //#endregion
    
    //#region Velocity
    /**
     * @param ticks Encoder Ticks
     * @param gearRatio Gear Ratio between Encoder and Mechanism
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Degrees of rotation of mechanism
     */
    public static double ticksToDegrees(double ticks, double gearRatio, double encoderResolution) {
        return ticks * (360.0 / (gearRatio * encoderResolution));
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Encoder and Mechanism
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Encoder Ticks
     */
    public static double degreesToTicks(double degrees, double gearRatio, double encoderResolution) {
        double ticks =  degrees / (360.0 / (gearRatio * encoderResolution));
        return ticks;
    }

    /**
     * @param velocityTicks Encoder Velocity in Ticks per 100ms
     * @param k_FlywheelGearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return RPM of Mechanism
     */
    public static double ticksPer100msToRPM(double velocityTicks, Double k_FlywheelGearRatio, double encoderResolution) {
        double encoderRPM = velocityTicks * (600.0 / encoderResolution);        
        double mechanismRPM = encoderRPM / k_FlywheelGearRatio;
        return mechanismRPM;
    }

    /**
     * @param RPM RPM of mechanism
     * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Encoder Velocity in Ticks per 100ms
     */
    public static double RPMToTicksPer100ms(double RPM, double gearRatio, double encoderResolution) {
        double encoderRPM = RPM * gearRatio;
        double ticksPer100ms = encoderRPM * (encoderResolution / 600.0);
        return ticksPer100ms;
    }

    /**
     * @param velocityTicksPer100ms Encoder Velocity Ticks per 100ms
     * @param circumference Circumference of Wheel (Meter2
     * 321s)
     * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Velocity in Meters per Second
     */
    public static double ticksPer100msToMPS(double velocityTicksPer100ms, double circumference, double gearRatio, double encoderResolution) {
        double wheelRPM = ticksPer100msToRPM(velocityTicksPer100ms, gearRatio, encoderResolution);
        return UnitsConversions.RPMToMPS(wheelRPM, circumference);
    }

    /**
     * @param velocity Velocity MPS (Meters per Second)
     * @param circumference Circumference of Wheel (Meters)
     * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
     * @param encoderResolution Encoder's Ticks Per Revolution (also known as PPR)
     * @return Encoder Velocity in Ticks per 100ms
     */
    public static double MPSToTicksPer100ms(double velocity, double circumference, double gearRatio, double encoderResolution) {
        double wheelRPM = UnitsConversions.MPSToRPM(velocity, circumference);
        return  RPMToTicksPer100ms(wheelRPM, gearRatio, encoderResolution);
    }
    //#endregion

    /**Encoder conversions for using Falcon Internal Encoder */
    public static class FalconConversions {
        /**Falcon internal encoder resolution */
        public static final double k_falconResolution = 2048.0;

        //#region Length
        /**
         * @param ticks Encoder Ticks
         * @param gearRatio Gear Ratio between Encoder and Mechanism
         * @param diameter Spool Diameter (Meters)
         * @return Length in Meters
         */
        public static double ticksToMeters(double ticks, double gearRatio, double diameter) {
           return EncoderConversions.ticksToMeters(ticks, gearRatio, diameter, k_falconResolution);
        }

        /**
         * @param length Length in Meters
         * @param gearRatio Gear Ratio between Encoder and Mechanism
         * @param diameter Spool Diameter (Meters)
         * @return Length in Ticks
         */
        public static double metersToTicks(double length, double gearRatio, double diameter) {
            return EncoderConversions.metersToTicks(length, gearRatio, diameter, k_falconResolution);
        }
        //#endregion

        //#region Velocity
        /**
         * @param ticks Encoder Ticks
         * @param gearRatio Gear Ratio between Encoder and Mechanism
         * @return Degrees of rotation of mechanism
         */
        public static double ticksToDegrees(double ticks, double gearRatio) {
            return EncoderConversions.ticksToDegrees(ticks, gearRatio, k_falconResolution);
        }

        /**
         * @param degrees Degrees of rotation of Mechanism
         * @param gearRatio Gear Ratio between Encoder and Mechanism
         * @return Encoder Ticks
         */
        public static double degreesToTicks(double degrees, double gearRatio) {
            return EncoderConversions.degreesToTicks(degrees, gearRatio, k_falconResolution);
        }

        /**
         * @param velocityTicks Encoder Velocity in Ticks per 100ms
         * @param k_FlywheelGearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
         * @return RPM of Mechanism
         */
        public static double ticksPer100msToRPM(double velocityTicks, Double k_FlywheelGearRatio) {
            return EncoderConversions.ticksPer100msToRPM(velocityTicks, k_FlywheelGearRatio, k_falconResolution);
        }

        /**
         * @param RPM RPM of mechanism
         * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
         * @return Encoder Velocity in Ticks per 100ms
         */
        public static double RPMToTicksPer100ms(double RPM, double gearRatio) {
            return EncoderConversions.RPMToTicksPer100ms(RPM, gearRatio, k_falconResolution);
        }

        /**
         * @param velocityTicksPer100ms Encoder Velocity Ticks per 100ms
         * @param circumference Circumference of Wheel (Meters)
         * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
         * @return Velocity in Meters per Second
         */
        public static double ticksPer100msToMPS(double velocityTicksPer100ms, double circumference, double gearRatio) {
            return EncoderConversions.ticksPer100msToMPS(velocityTicksPer100ms, circumference, gearRatio, k_falconResolution);
        }

        /**
         * @param velocity Velocity MPS (Meters per Second)
         * @param circumference Circumference of Wheel (Meters)
         * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
         * @return Encoder Velocity in Ticks per 100ms
         */
        public static double MPSToTicksPer100ms(double velocity, double circumference, double gearRatio) {
            return EncoderConversions.MPSToTicksPer100ms(velocity, circumference, gearRatio, k_falconResolution);
        }
        //#endregion

    }

    /**Encoder conversions for using CTRE Magnetic Encoder */
    public static class CTREMagneticEncoderConversions {
        /**CTRE Magnetic Encoder resolution */
        public static final double k_ctreMagneticEncoderResolution = 4096.0;
            
            //#region Length
            /**
             * @param ticks Encoder Ticks
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @param diameter Spool Diameter (Meters)
             * @return Length in Meters
             */
            public static double ticksToMeters(double ticks, double gearRatio, double diameter) {
                return EncoderConversions.ticksToMeters(ticks, gearRatio, diameter, k_ctreMagneticEncoderResolution);
            }
    
            /**
             * @param length Length in Meters
            * @param gearRatio Gear Ratio between Encoder and Mechanism
            * @param diameter Spool Diameter (Meters)
            * @return Length in Ticks
            */
            public static double metersToTicks(double length, double gearRatio, double diameter) {
                return EncoderConversions.metersToTicks(length, gearRatio, diameter, k_ctreMagneticEncoderResolution);
            }
            //#endregion

            //#region Velocity
            /**
             * @param ticks Encoder Ticks
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @return Degrees of rotation of mechanism
             */
            public static double ticksToDegrees(double ticks, double gearRatio) {
                return EncoderConversions.ticksToDegrees(ticks, gearRatio, k_ctreMagneticEncoderResolution);
            }

            /**
             * @param degrees Degrees of rotation of Mechanism
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @return Encoder Ticks
             */
            public static double degreesToTicks(double degrees, double gearRatio) {
                return EncoderConversions.degreesToTicks(degrees, gearRatio, k_ctreMagneticEncoderResolution);
            }

            /**
             * @param velocityTicks Encoder Velocity in Ticks per 100ms
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return RPM of Mechanism
             */
            public static double ticksPer100msToRPM(double velocityTicks, double gearRatio) {
                return EncoderConversions.ticksPer100msToRPM(velocityTicks, gearRatio, k_ctreMagneticEncoderResolution);
            }

            /**
             * @param RPM RPM of mechanism
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return Encoder Velocity in Ticks per 100ms
             */
            public static double RPMToTicksPer100ms(double RPM, double gearRatio) {
                return EncoderConversions.RPMToTicksPer100ms(RPM, gearRatio, k_ctreMagneticEncoderResolution);
            }

            /**
             * @param velocityTicksPer100ms Encoder Velocity Ticks per 100ms
             * @param circumference Circumference of Wheel (Meters)
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return Velocity in Meters per Second
             */
            public static double ticksPer100msToMPS(double velocityTicksPer100ms, double circumference, double gearRatio) {
                return EncoderConversions.ticksPer100msToMPS(velocityTicksPer100ms, circumference, gearRatio, k_ctreMagneticEncoderResolution);
            }

            /**
             * @param velocity Velocity MPS (Meters per Second)
             * @param circumference Circumference of Wheel (Meters)
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return Encoder Velocity in Ticks per 100ms
             */
            public static double MPSToTicksPer100ms(double velocity, double circumference, double gearRatio) {
                return EncoderConversions.MPSToTicksPer100ms(velocity, circumference, gearRatio, k_ctreMagneticEncoderResolution);
            }
            //#endregion

    }

    /**Encoder conversions for using REV default unit system (REV returns position in Rotations and velocity in RPM)*/
    public static class REVConversions {
        /** REV default positional units are Rotations */
        public static final double k_rotationResolution = 1.0;

            //#region Length
            /**
             * @param ticks Encoder Ticks
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @param diameter Spool Diameter (Meters)
             * @return Length in Meters
             */
            public static double ticksToMeters(double ticks, double gearRatio, double diameter) {
                return EncoderConversions.ticksToMeters(ticks, gearRatio, diameter, k_rotationResolution);
            }
    
            /**
             * @param length Length in Meters
            * @param gearRatio Gear Ratio between Encoder and Mechanism
            * @param diameter Spool Diameter (Meters)
            * @return Length in Ticks
            */
            public static double metersToTicks(double length, double gearRatio, double diameter) {
                return EncoderConversions.metersToTicks(length, gearRatio, diameter, k_rotationResolution);
            }
            //#endregion
       
            //#region Velocity
            /**
             * @param rotations Encoder Rotations
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @return Degrees of rotation of mechanism
             */
            public static double rotationsToDegrees(double rotations, double gearRatio) {
                return EncoderConversions.ticksToDegrees(rotations, gearRatio, k_rotationResolution);
            }

            /**
             * @param degrees Degrees of rotation of Mechanism
             * @param gearRatio Gear Ratio between Encoder and Mechanism
             * @return Encoder Rotations
             */
            public static double degreesToRotations(double degrees, double gearRatio) {
                return EncoderConversions.degreesToTicks(degrees, gearRatio, k_rotationResolution);
            }

            /**
             * @param rpm Encoder RPM
             * @param circumference Circumference of Wheel (Meters)
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return Mechanism Velocity in Meters per Second
             */
            public static double RPMToMPS(double rpm, double circumference, double gearRatio) {
                double mechanismRPM = rpm / (gearRatio * k_rotationResolution);
                return UnitsConversions.RPMToMPS(mechanismRPM, circumference);
            }

            /**
             * @param velocity Mechanism Velocity MPS (Meters per Second)
             * @param circumference Circumference of Wheel (Meters)
             * @param gearRatio Gear Ratio between Encoder and Mechanism (set to 1 for Encoder RPM)
             * @return Encoder Velocity in Rotations per 100ms
             */
            public static double MPSToRPM(double velocity, double circumference, double gearRatio) {
                double encoderMPS = velocity * gearRatio * k_rotationResolution;
                return UnitsConversions.MPSToRPM(encoderMPS, circumference);
            } 
            //#endregion

    }

}