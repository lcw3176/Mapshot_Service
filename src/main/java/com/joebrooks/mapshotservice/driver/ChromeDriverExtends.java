package com.joebrooks.mapshotservice.driver;


import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;

public class ChromeDriverExtends extends ChromeDriver {

    public ChromeDriverExtends(ChromeOptions options) throws Exception {
        this(ChromeDriverService.createDefaultService(), options);
    }

    public ChromeDriverExtends(ChromeDriverService service, ChromeOptions options) throws Exception {
        super(service, options);
        CommandInfo cmd = new CommandInfo("/session/:sessionId/chromium/send_command_and_get_result", HttpMethod.POST);
        Method defineCommand = HttpCommandExecutor.class.getDeclaredMethod("defineCommand", String.class, CommandInfo.class);
        defineCommand.setAccessible(true);
        defineCommand.invoke(super.getCommandExecutor(), "sendCommand", cmd);
    }

    public byte[] getFullScreenshot() {
        Object metrics = sendEvaluate(
                "({" +
                        "width: Math.max(window.innerWidth,document.body.scrollWidth,document.documentElement.scrollWidth)|0," +
                        "height: Math.max(window.innerHeight,document.body.scrollHeight,document.documentElement.scrollHeight)|0," +
                        "deviceScaleFactor: window.devicePixelRatio || 1," +
                        "mobile: typeof window.orientation !== 'undefined'" +
                        "})");
        sendCommand("Emulation.setDeviceMetricsOverride", metrics);
        Object result = sendCommand("Page.captureScreenshot", ImmutableMap.of("format", "jpeg",  "quality", 80));
        sendCommand("Emulation.clearDeviceMetricsOverride", ImmutableMap.of());
        String data = ((Map<String, String>)result).get("data");

        return Base64.getDecoder().decode(data);
    }

    protected Object sendCommand(String cmd, Object params) {
        return execute("sendCommand", ImmutableMap.of("cmd", cmd, "params", params)).getValue();
    }

    protected Object sendEvaluate(String script) {
        Object response = sendCommand("Runtime.evaluate", ImmutableMap.of("returnByValue", true, "expression", script));
        Object result = ((Map<String, ?>)response).get("result");
        return ((Map<String, ?>)result).get("value");
    }

}