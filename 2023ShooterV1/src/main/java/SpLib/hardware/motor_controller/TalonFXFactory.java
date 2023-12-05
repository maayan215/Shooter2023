package SpLib.hardware.motor_controller;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/**
 * Creates TalonFX objects and configures all the parameters we care about to factory defaults. Closed-loop and sensor
 * parameters are not set, as these are expected to be set by the application.
 */
public class TalonFXFactory {

    //#region Private Members
    private final static int k_timeoutMs = 100; //TODO: should use timeout?
    //#endregion

    private TalonFXFactory() {
        throw new AssertionError("utility class");
    }

    //#region Public Methods
    /**
     * Creates TalonFX with default configuration. 
     * @param id - CAN id
     */
    public static TalonFX create(TalonFXConstants motorConstants) {
        ExtendedTalonFXConfiguration defaultConfiguration = TalonFXFactory.getDefaultConfiguration();
        return TalonFXFactory.create(motorConstants, defaultConfiguration);
    }

    /**
     * Creates TalonFX with custom configuration. 
     * @param id - CAN id
     * @param configuration - Custom configuration.
     */
    
    public static TalonFX create(TalonFXConstants motorConstants, ExtendedTalonFXConfiguration configuration) {
        TalonFX talonFX = new TalonFX(motorConstants.id);
        talonFX.configFactoryDefault();
        talonFX.setInverted(motorConstants.invertType);
        talonFX.setNeutralMode(configuration.neutralMode.Brake);
        talonFX.configAllSettings(configuration);
        
        talonFX.selectProfileSlot(0, 0);
        talonFX.enableVoltageCompensation(configuration.enableVoltageCompensation);
        TalonFXFactory.setStatusFramePeriod(talonFX, StatusFrameEnhanced.Status_1_General, configuration.status1FramePeriodMs);
        TalonFXFactory.setStatusFramePeriod(talonFX, StatusFrameEnhanced.Status_2_Feedback0, configuration.status2FramePeriodMs);
        TalonFXFactory.setStatusFramePeriod(talonFX, StatusFrameEnhanced.Status_8_PulseWidth, configuration.status8FramePeriodMs);
        TalonFXFactory.setStatusFramePeriod(talonFX, StatusFrameEnhanced.Status_10_Targets, configuration.status10FramePeriodMs);
        TalonFXFactory.setStatusFramePeriod(talonFX, StatusFrameEnhanced.Status_Brushless_Current, configuration.statusCurrentFramePeriodMs);

        return talonFX;
    }

    /**
     * Creates TalonFX follower. 
     * @param id - CAN id
     * @param invertType - Clockwise/CounterClockwise
     * @param neutralMode - Brake/Coast (probably should be same as the master)
     * @param master - Motor Controller to follow.
     */
    public static TalonFX createFollower(TalonFXConstants motorConstants, NeutralMode neutralMode, IMotorController master) {
        ExtendedTalonFXConfiguration followerDefaultConfiguration = TalonFXFactory.getFollowerDefaultConfiguration(neutralMode);
        TalonFX follower = TalonFXFactory.create(motorConstants, followerDefaultConfiguration);
        follower.follow(master);

        return follower;
    }

    /**
     * Creates a default configuration.
     */
    public static ExtendedTalonFXConfiguration getDefaultConfiguration() {
        ExtendedTalonFXConfiguration configuration = new ExtendedTalonFXConfiguration();
        configuration.enableVoltageCompensation = true;
        configuration.status1FramePeriodMs = 250;

        return configuration;
    }

    /**
     * Creates a configuration for a follower.
     * @param invertType - Clockwise/CounterClockwise
     * @param neutralMode - Brake/Coast (probably should be same as the master)
     */
    public static ExtendedTalonFXConfiguration getFollowerDefaultConfiguration(NeutralMode neutralMode) {
        ExtendedTalonFXConfiguration configuration = new ExtendedTalonFXConfiguration();
        configuration.neutralMode = neutralMode;
        configuration.status1FramePeriodMs = 255;
        configuration.status2FramePeriodMs = 255;

        return configuration;
    }
    //#endregion

    //#region Private Methods
    private static void setStatusFramePeriod(TalonFX talonFX, StatusFrameEnhanced frame, int periodMs) {
        if (periodMs != -1) {
            talonFX.setStatusFramePeriod(frame, periodMs);
        }
    }
    //#endregion
    
}