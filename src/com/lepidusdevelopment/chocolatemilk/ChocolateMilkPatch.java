package com.lepidusdevelopment.chocolatemilk;

/**
 * Copyright (c) 2014, Lepidus Development LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import android.app.Application;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ChocolateMilkPatch implements IXposedHookLoadPackage {
	private static final String PACKAGE_NAME = "com.samsung.mdl.radio";

	@Override
	public void handleLoadPackage(LoadPackageParam param) throws Throwable {
		if (param.packageName.equals(PACKAGE_NAME)) {
			final ClassLoader classLoader = param.classLoader;
            final XC_MethodReplacement deviceDetection = new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam hookParam) throws Throwable {
                    return true;
                }
            };

            XC_MethodHook onCreate = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam hookParam) throws Throwable {
                    int versionCode = ((Application) hookParam.thisObject).getPackageManager().
                            getPackageInfo(PACKAGE_NAME, 0).versionCode;

                    if (versionCode == 179553) {
                        XposedHelpers.findAndHookMethod(
                                "com.samsung.mdl.radio.g",
                                classLoader,
                                "g",
                                deviceDetection
                        );
                    }
                    else if (versionCode == 176956) {
                        XposedHelpers.findAndHookMethod(
                                "com.samsung.mdl.radio.g",
                                classLoader,
                                "h",
                                deviceDetection
                        );
                    }

                    super.beforeHookedMethod(hookParam);
                }
            };

            XposedHelpers.findAndHookMethod(
                    "com.samsung.mdl.radio.RadioApp",
                    classLoader,
                    "onCreate",
                    onCreate
            );
		}
	}
}
