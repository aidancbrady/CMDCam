package com.creativemd.cmdcam.utils;

import com.creativemd.cmdcam.CMDCam;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CamPoint {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public double x;
	public double y;
	public double z;
	
	public double rotationYaw;
	public double rotationPitch;
	
	public double roll;
	public double zoom;
	
	public CamPoint(double x, double y, double z, double rotationYaw, double rotationPitch, double roll, double zoom)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.roll = roll;
		this.zoom = zoom;
	}
	
	public CamPoint()
	{
		this.x = mc.thePlayer.posX;
		this.y = mc.thePlayer.posY;
		this.z = mc.thePlayer.posZ;
		
		this.rotationYaw = MathHelper.wrapDegrees(mc.thePlayer.rotationYawHead);
		this.rotationPitch = mc.thePlayer.rotationPitch;
		
		this.roll = CMDCam.roll;
		this.zoom = CMDCam.fov;
	}
	
	public CamPoint getPointBetween(CamPoint point, double percent)
	{
		//player.rotationYaw);
		double yawDifference1 = (point.rotationYaw - this.rotationYaw);
		double yawDifference2 = (point.rotationYaw - 360 - this.rotationYaw);
		double diff = yawDifference1;
		if(Math.abs(yawDifference2) < Math.abs(yawDifference1))
			diff = yawDifference2;
		double yaw = MathHelper.wrapDegrees(this.rotationYaw + diff * percent);
		
		return new CamPoint(
				this.x + (point.x - this.x) * percent,
				this.y + (point.y - this.y) * percent,
				this.z + (point.z - this.z) * percent,
				yaw,
				this.rotationPitch + (point.rotationPitch - this.rotationPitch) * percent,
				this.roll + (point.roll - this.roll) * percent,
				this.zoom + (point.zoom - this.zoom) * percent);
	}
	
	public void faceEntity(Vec3d pos, float maxYaw, float maxPitch, double ticks)
    {
        double d0 = pos.xCoord - this.x;
        double d2 = pos.zCoord - this.z;
        double d1 = pos.yCoord - this.y;

        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        double f2 = (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0D;
        double f3 = (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        this.rotationPitch = updateRotation(this.rotationPitch, f3, Math.max(maxPitch, Math.abs(f3/ticks)));
        this.rotationYaw = updateRotation(this.rotationYaw, f2, Math.max(maxYaw, Math.abs(f2/ticks)));
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private double updateRotation(double rotation, double intended, double max)
    {
        double f3 = MathHelper.wrapDegrees(intended - rotation);

        if (f3 > max)
        {
            f3 = max;
        }

        if (f3 < -max)
        {
            f3 = -max;
        }

        return rotation + f3;
    }
	
	public CamPoint copy()
	{
		return new CamPoint(x, y, z, rotationYaw, rotationPitch, roll, zoom);
	}
	
	@Override
	public String toString()
	{
		return "x:" + x + ",y:" + y + ",z:" + z + ",yaw:" + rotationYaw + ",pitch:" + rotationPitch + ",roll:" + roll + ",zoom" + zoom;
	}
	
}
