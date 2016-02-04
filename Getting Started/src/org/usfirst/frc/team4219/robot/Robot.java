package org.usfirst.frc.team4219.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myDrive, myDriveAuto;
	Talon left, frontRight, backRight;
	DoubleSolenoid lifter1 = new DoubleSolenoid(0,1);
	DoubleSolenoid lifter2 = new DoubleSolenoid(2,3);
	Joystick stick = new Joystick(0);
	Compressor comp = new Compressor(0);
	boolean compVal = true;
	int autoLoopCounter;
	boolean speedVal;
	
	//edu.wpi.first.wpilibj.Timer timer = new edu.wpi.first.wpilibj.Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		// compVal = true;
		// comp.setClosedLoopControl(compVal);

	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {
		left = new Talon(0);
		frontRight = new Talon(1);
		backRight = new Talon(2);
		myDrive = new RobotDrive(left, left, frontRight, backRight);
		
		myDrive.tankDrive(.0, .0);
		comp.setClosedLoopControl(true);
	
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		/*pick up box*/
		//====================================================
		for(int i=0; i<num(3)*10; i++)
		{
			myDrive.tankDrive(.7, .7);
		}
		Timer.delay(2.0);
		//===^^	The Program drives forward for 4 seconds ^^=====
		//====================================================
		while(true){	comp.setClosedLoopControl(true);	}//Program Stops Running
		
		
		
		
		// We have to pickup the tote and/or container and drive forward approx.
		// 9 feet(into the auto zone)
		// 4 points moving;
		// 6 points move tote into zone;
		// 8 points move bin into zone;
		// 20 points stack all three totes
		// This would probably be driving for 1 second
		// we would also use the lifter in case there is a stack.

	}
	public double num(int n)
	{
		return Math.pow(10, n);
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	public void teleopInit() {
		left = new Talon(0);
		frontRight = new Talon(1);
		backRight = new Talon(2);
		myDrive = new RobotDrive(left, left, frontRight, backRight);
		compVal = true;
		speedVal = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		controlSolenoid();
		setCompressor();
		determineSpeed();
		setSpeed(speedVal);
		comp.setClosedLoopControl(compVal);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	/**
	 * All Methods Below
	 */
	
	public void controlSolenoid()
	{
		if (buttonPressed(6) && buttonPressed(8) && buttonPressed(1)) {	//change back to if(buttonPressed(6)) only when not t-shirt cannon
			lifter1.set(DoubleSolenoid.Value.kForward);
			//lifter2.set(DoubleSolenoid.Value.kForward);	uncomment when not t-shirt
			//compVal = true;
		} 
		//=============get rid of stuff under this when not t-shirt
		else
		{
			lifter1.set(DoubleSolenoid.Value.kOff);
			//lifter2.set(DoubleSolenoid.Value.kOff);	uncomment when not t-shirt
		}
		//===============get rid of stuff above this when not t-shirt
		
			/*else if (buttonPressed(8)) {
			lifter1.set(DoubleSolenoid.Value.kReverse);
			lifter2.set(DoubleSolenoid.Value.kReverse);
			//compVal = false;
		}else{
			lifter1.set(DoubleSolenoid.Value.kOff);
			lifter2.set(DoubleSolenoid.Value.kOff);
		}*/ //Uncomment all this junk when not t-shirt
	}
	
	public void setCompressor()
	{
		if(buttonPressed(2)){
			compVal = false;
		}
		if(buttonPressed(4)){
			compVal = true;
		}
	}
	
	public boolean buttonPressed(int n) {
		if (stick.getRawButton(n)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean determineSpeed(){
		if(buttonPressed(5)){
			speedVal = true;
		}
		if(buttonPressed(7)){
			speedVal = false;
		}
		
		return speedVal;
	}
	
	public void setSpeed(boolean b)
	{
		double fleft =  -.8 * stick.getRawAxis(1);
		double fright =  -.8 * stick.getRawAxis(3);
		
		double sleft = -.65 * stick.getRawAxis(1);
		double sright = -.6 * stick.getRawAxis(3);
		if(b){
			myDrive.tankDrive(fleft, fright);
		}else{
			myDrive.tankDrive(sleft, sright);
		}
		//when speedVal equals true, extra speed. When false, normal speed
	}
}
