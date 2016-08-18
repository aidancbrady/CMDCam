package com.creativemd.cmdcam.transform;

import java.io.File;
import java.util.Map;

import com.creativemd.cmdcam.CMDCam;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class CamPatchingLoader implements IFMLLoadingPlugin {

public static File location;

	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{CamTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return CMDCam.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		location = (File) data.get("coremodLocation");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
