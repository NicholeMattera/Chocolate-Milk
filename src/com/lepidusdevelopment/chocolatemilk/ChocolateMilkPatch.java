package com.lepidusdevelopment.chocolatemilk;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ChocolateMilkPatch implements IXposedHookLoadPackage {
	public static final String PACKAGE_NAME = ChocolateMilkPatch.class.getPackage().getName();
	public static final String TAG = "ChocolateMilkPatch";
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (lpparam.packageName.equals("com.samsung.mdl.radio")) {
			ClassLoader classLoader = lpparam.classLoader;
			
			XC_MethodReplacement deviceDetection = new XC_MethodReplacement() {
				@Override
				protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
					return true;
				}
		    };
		    
		    XposedHelpers.findAndHookMethod("com.samsung.mdl.radio.g", classLoader, "h", deviceDetection);
		}
	}
}
